package com.habeebcycle.marketplace.controller.product;

import com.habeebcycle.marketplace.exception.BadRequestException;
import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.model.entity.product.ProductDetails;
import com.habeebcycle.marketplace.payload.APIResponse;
import com.habeebcycle.marketplace.payload.ResourceAvailability;
import com.habeebcycle.marketplace.payload.product.ProductRequest;
import com.habeebcycle.marketplace.payload.product.ProductResponse;
import com.habeebcycle.marketplace.service.ProductService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import com.habeebcycle.marketplace.util.HelperClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.habeebcycle.marketplace.util.HelperClass.getStringFromLongList;

@RestController
@RequestMapping(ApplicationConstants.PRODUCT_ENDPOINT)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductResponse getCategoryById(@PathVariable Long id){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product", "Product Id", "" + id));

        return productService.getProductResponse(product);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> productResponses = new ArrayList<>();
        productService.getAllProduct()
                .forEach(product -> {
                    ProductResponse response = productService.getProductResponse(product);
                    productResponses.add(response);
                });
        return productResponses;
    }

    @GetMapping("/slug/{slug}")
    public ProductResponse getProductBySlug(@PathVariable String slug){
        Product product = productService.getProductBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Product", "Product Slug", "" + slug));

        return productService.getProductResponse(product);
    }

    @GetMapping("/slug-available/{slug}")
    public ResourceAvailability checkResourceAvailability(@PathVariable String slug){
        Boolean isAvailable = !productService.slugExists(slug);
        return new ResourceAvailability(isAvailable);
    }

    @GetMapping("/category/{catId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Long catId){
        List<ProductResponse> productResponses = new ArrayList<>();
        productService.getProductByCategory(catId)
                .forEach(product -> {
                    ProductResponse response = productService.getProductResponse(product);
                    productResponses.add(response);
                });
        return productResponses;
    }

    @GetMapping("/price/{price}")
    public List<ProductResponse> getProductsByPrice(@PathVariable BigDecimal price){
        List<ProductResponse> productResponses = new ArrayList<>();
        productService.getProductByPrice(price)
                .forEach(product -> {
                    ProductResponse response = productService.getProductResponse(product);
                    productResponses.add(response);
                });
        return productResponses;
    }

    @GetMapping("/price-range/{min}/{max}")
    public List<ProductResponse> getProductByPriceRange(@PathVariable BigDecimal min, @PathVariable BigDecimal max){
        List<ProductResponse> productResponses = new ArrayList<>();
        productService.getPriceRangeProduct(min, max)
                .forEach(product -> {
                    ProductResponse response = productService.getProductResponse(product);
                    productResponses.add(response);
                });
        return productResponses;
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse addNewProduct(@RequestPart(value = "product") String productJson,
                                          @RequestPart(name = "file", required = false) MultipartFile file,
                                          @RequestPart(name = "files", required = false) MultipartFile... files) {

        try {
            ProductRequest request = productService.convertProductString(productJson);

            Long thumbnail = productService.getImage(file, request.getTitle(), request.getUrl());
            String images = getStringFromLongList(productService.getImages(request.getTitle(), "", files));
            String slug = productService.formatSlug(request.getSlug());

            ProductDetails details = productService.getProductDetails(request);
            details.setFeatured(false);
            details.setImages(images);

            Product product = new Product();
            product.setTitle(request.getTitle());
            product.setSlug(slug);
            product.setPrice(request.getPrice());
            product.setDescription(request.getDescription());
            product.setCategory(productService.getCategory(request.getCategory()));
            product.setThumbnail(thumbnail);
            product.setProductDetails(details);
            product.setOwner(request.getOwner());

            product = productService.addProduct(product);

            return productService.getProductResponse(product);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BadRequestException("Request is bad. Check the form data!", ex);
        }
    }

    @PutMapping("/feature/{id}")
    public APIResponse featureProduct(@PathVariable Long id){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        Boolean featured = product.getProductDetails().getFeatured();
        product.getProductDetails().setFeatured(!featured);
        productService.updateProduct(product);
        return new APIResponse(true, "Changes Done", HttpStatus.OK);
    }

    @PutMapping(path = "/images/{id}/{img}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse updateImage(@PathVariable Long id, @PathVariable Long img,
                             @RequestPart(name = "file", required = false) MultipartFile file){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        List<Long> imgList = HelperClass.getLongListFromString(product.getProductDetails().getImages());

        if(imgList != null && imgList.contains(img)){
            productService.updateImage(img, file, product.getTitle(), "");
        }else if(product.getThumbnail().equals(img)){
            productService.updateImage(product.getThumbnail(), file,
                    product.getTitle(), "");
        }else{
            return new APIResponse(false, "Changes Done", HttpStatus.NOT_FOUND);
        }

        return new APIResponse(true, "Changes Done", HttpStatus.OK);
    }

    @PutMapping(path = "/images/{id}/{main}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse addImage(@PathVariable Long id, @PathVariable Boolean main,
                                    @RequestPart(name = "file", required = false) MultipartFile file){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        Long image = productService.getImage(file, product.getTitle(), "");

        if(image != null) {
            if (main) {
                product.setThumbnail(image);
            } else {
                List<Long> imgList = HelperClass.getLongListFromString(product.getProductDetails().getImages());
                if (imgList == null) {
                    imgList = new ArrayList<>();
                }
                imgList.add(image);
                product.getProductDetails().setImages(getStringFromLongList(imgList));
            }
            productService.updateProduct(product);
            return new APIResponse(true, "Changes Done", HttpStatus.OK);
        }else{
            return new APIResponse(false, "Changes Done", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {

        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        try {

            String slug = !product.getSlug().equalsIgnoreCase(request.getSlug())
                    ? productService.formatSlug(request.getSlug()) : request.getSlug();
            ProductDetails details = productService.getProductDetails(request);

            product.setTitle(request.getTitle());
            product.setSlug(slug);
            product.setPrice(request.getPrice());
            product.setDescription(request.getDescription());
            product.setCategory(productService.getCategory(request.getCategory()));
            product.setProductDetails(details);
            product.setOwner(request.getOwner());

            product = productService.updateProduct(product);

            return productService.getProductResponse(product);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BadRequestException("Request is bad. Check the form data!", ex);
        }
    }

    @DeleteMapping("/{catId}")
    public void deleteProduct(@PathVariable Long id){
        Product product = productService.getProductById(id).orElse(null);
        if(product != null){
            productService.deleteImage(product.getThumbnail());
            productService.deleteImages(HelperClass.getLongListFromString(product.getProductDetails().getImages()));
            productService.deleteProduct(product.getId());
        }
    }

    @DeleteMapping("/images/{id}/{img}")
    public APIResponse deleteImage(@PathVariable Long id, @PathVariable Long img) {
        Product product = productService.getProductById(id).orElse(null);

        if(product != null){
            List<Long> imgList = HelperClass.getLongListFromString(product.getProductDetails().getImages());

            if(imgList != null && imgList.contains(img)){
                imgList.remove(img);
                String s = HelperClass.getStringFromLongList(imgList);
                product.getProductDetails().setImages(s);
            }else if(product.getThumbnail().equals(img)){
                product.setThumbnail(null);
            }else{
                return new APIResponse(false, "Not Deleted", HttpStatus.NOT_FOUND);
            }
            productService.deleteImage(img);
            productService.updateProduct(product);

            return new APIResponse(true, "Deleted Successfully", HttpStatus.OK);
        }else{
            return new APIResponse(false, "Not Deleted", HttpStatus.NOT_FOUND);
        }
    }

}
