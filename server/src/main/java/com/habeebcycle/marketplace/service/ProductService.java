package com.habeebcycle.marketplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.model.entity.product.ProductDetails;
import com.habeebcycle.marketplace.model.entity.user.User;
import com.habeebcycle.marketplace.model.entity.util.ResponseCategory;
import com.habeebcycle.marketplace.payload.product.ProductDetailResponse;
import com.habeebcycle.marketplace.payload.product.ProductRequest;
import com.habeebcycle.marketplace.payload.product.ProductResponse;
import com.habeebcycle.marketplace.payload.user.UserSummary;
import com.habeebcycle.marketplace.repository.ProductRepository;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import com.habeebcycle.marketplace.util.HelperClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService categoryService;
    private final ImageService imageService;
    private final UserService userService;
    private final String IMAGE_FOLDER = "product";

    public ProductService(ProductRepository productRepository, ImageService imageService, UserService userService,
                          ProductCategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.userService = userService;
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Optional<Product> getProductBySlug(String slug){
        return productRepository.findBySlug(slug);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Boolean slugExists(String slug){
        return productRepository.existsBySlug(slug);
    }

    public Boolean productExistsById(Long id){
        return productRepository.existsById(id);
    }

    public Long getProductCount(){
        return productRepository.count();
    }

    public List<Product> getProductByPrice(BigDecimal price){
        return productRepository.findByPrice(price);
    }

    public List<Product> getProductInId(List<Long> ids){
        return productRepository.findByIdIn(ids);
    }

    public List<Product> getProductByCategory(Long catId){
        return productRepository.findByCategoryId(catId);
    }

    public List<Product> getProductByOwner(Long owner){
        return productRepository.findByOwner(owner);
    }

    public List<Product> getPriceRangeProduct(BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findByMinPriceAndMaxPrice(minPrice, maxPrice);
    }

    public BigDecimal getMaxProductPrice(){
        return productRepository.findMaxPrice();
    }

    public BigDecimal getMinProductPrice(){
        return productRepository.findMinPrice();
    }

    public Long getNumberOfProducts(){
        return productRepository.findNumberOfProducts();
    }

    public Long getNumberOfProductsInCategory(ProductCategory category){
        return productRepository.findNumberOfProductInCategory(category);
    }

    public Product addProduct(Product product){
        product.setSlug(formatSlug(product.getSlug()));
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        getProductById(product.getId())
                .ifPresent(oldProduct -> product.setSlug(
                        oldProduct.getSlug().equalsIgnoreCase(product.getSlug())
                                ? product.getSlug() : formatSlug(product.getSlug()) ));
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void deleteProducts(List<Long> ids){
        productRepository.deleteAll(productRepository.findAllById(ids));
    }

    public void deleteAllProduct(){
        productRepository.deleteAll();
    }

    public List<Product> getOnSalesProducts(){
        List<Product> allProducts = getAllProduct();
        LocalDateTime time = LocalDateTime.now();
        return allProducts
                .stream()
                .filter(product -> product.getProductDetails() != null
                        && product.getProductDetails().getSalesStart() != null
                        && product.getProductDetails().getSalesStart().isBefore(time)
                        && product.getProductDetails().getSalesEnd() != null
                        && product.getProductDetails().getSalesEnd().isAfter(time))
                .collect(Collectors.toList());
    }

    public List<Product> getFeaturedProducts(){
        List<Product> allProducts = getAllProduct();
        return allProducts.isEmpty() ? allProducts
                : allProducts.stream()
                .filter(product -> product.getProductDetails() != null
                        && product.getProductDetails().getFeatured() != null
                        && product.getProductDetails().getFeatured())
                .collect(Collectors.toList());
    }

    public ProductCategory getCategory(Long catId){
        return categoryService.getCategoryById(catId)
                .orElse(null);
    }

    public ProductDetails getProductDetails(ProductRequest request){
        ProductDetails details = new ProductDetails();

        details.setShortDescription(request.getShortDescription());
        details.setRegularPrice(request.getRegularPrice());
        details.setSalesPrice(request.getSalesPrice());
        details.setSalesStart(request.getSalesStart());
        details.setSalesEnd(request.getSalesEnd());
        details.setSku(request.getSku());
        details.setType(request.getType());
        details.setStatus(request.getStatus());
        details.setWeight(request.getWeight());
        details.setDimension(request.getDimension());
        details.setStock(request.getStock());
        details.setNotes(request.getNotes());

        return details;
    }

    public ProductResponse getProductResponse(@NotNull Product product){
        /*TODO: Add Product variations here*/
        ProductDetails pDetails = product.getProductDetails();

        Image thumbnail = product.getThumbnail() != null ? imageService.getImage(product.getThumbnail()) : null;

        List<Long> imgSplit = pDetails.getImages() != null ? HelperClass.getLongListFromString(pDetails.getImages()) : null;
        List<Image> images = imgSplit != null ? imageService.getImages(imgSplit) : null;

        User ownerUser = product.getOwner() != null ?
                userService.getUser(product.getOwner()).orElse(null) : null;
        UserSummary owner = ownerUser != null ? new UserSummary(ownerUser.getId(), ownerUser.getUsername(), ownerUser.getName()) : null;

        LocalDateTime time = LocalDateTime.now();
        //Boolean onSale = pDetails.getSalesStart().isBefore(time) && pDetails.getSalesEnd().isAfter(time);
        Boolean onSale = pDetails.getSalesStart() != null && pDetails.getSalesStart().isBefore(time)
                && pDetails.getSalesEnd() != null && pDetails.getSalesEnd().isAfter(time);

        ProductDetailResponse detailResponse = new ProductDetailResponse(pDetails.getShortDescription(),
                pDetails.getRegularPrice(), pDetails.getSalesPrice(), pDetails.getSalesStart(), pDetails.getSalesEnd(),
                pDetails.getSku(), pDetails.getType(), pDetails.getStatus(), pDetails.getWeight(), pDetails.getDimension(),
                pDetails.getNotes(), pDetails.getStock(), pDetails.getFeatured(), onSale, images);

        String catLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + "/" + product.getCategory().getId()).toUriString();
        ResponseCategory category = new ResponseCategory(product.getCategory().getId(), product.getCategory().getName(),
                product.getCategory().getSlug(), catLink);

        ProductResponse response = new ProductResponse(product.getId(), product.getTitle(), product.getSlug(), product.getPrice(),
                product.getDescription(), category, thumbnail, owner, detailResponse);

        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        Long catId = product.getCreatedBy();
        if(catId != null) {
            userService.getUser(product.getCreatedBy()).ifPresent(user -> {
                response.setCreatedBy(new UserSummary(user.getId(), user.getUsername(), user.getName()));
            });
            userService.getUser(product.getUpdatedBy()).ifPresent(user -> {
                response.setUpdatedBy(new UserSummary(user.getId(), user.getUsername(), user.getName()));
            });
        }else{
            response.setCreatedBy(null);
            response.setUpdatedBy(null);
        }
        return response;
    }

    /*TODO: Add Product variations get method here*/

    public Long getImage(MultipartFile file, String name, String url){
        Image image = imageService.storeFile(file, IMAGE_FOLDER, name, url);
        if(image != null){
            image = imageService.addImage(image);
            return image.getId();
        }
        return null;
    }

    public List<Long> getImages(String name, String url, MultipartFile... files){
        List<Long> idList = new ArrayList<>();
        for(MultipartFile file : files){
            idList.add(getImage(file, name, url));
        }
        return idList;
    }

    public Long updateImage(Long imgId, MultipartFile file, String name, String url){
        if(imgId == null){
            return getImage(file, name, url);
        }else{
            Image image = imageService.updateFile(file, imgId, IMAGE_FOLDER, name, url);
            image.setId(imgId);
            return imageService.updateImage(image).getId();
        }
    }

    public void deleteImage(Long imgId){
        if(imgId != null) {
            imageService.deleteFile(imgId, IMAGE_FOLDER);
            imageService.deleteImage(imgId);
        }
    }

    public void deleteImages(List<Long> ids) {
        if (ids != null && !ids.isEmpty()){
            for (Long id : ids) {
                deleteImage(id);
            }
        }
    }

    public ProductRequest convertProductString(String productJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(productJson, ProductRequest.class);
    }

    public String formatSlug(String slug){
        if(slugExists(slug)){
            slug = slug + "-" + ((int)(Math.random() + 1));
            formatSlug(slug);
        }
        return slug.toLowerCase();
    }

}
