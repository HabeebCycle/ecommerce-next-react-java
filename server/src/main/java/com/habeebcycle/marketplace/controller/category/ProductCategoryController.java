package com.habeebcycle.marketplace.controller.category;

import com.habeebcycle.marketplace.exception.BadRequestException;
import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.payload.ResourceAvailability;
import com.habeebcycle.marketplace.payload.category.ProductCategoryRequest;
import com.habeebcycle.marketplace.payload.category.ProductCategoryResponse;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT)
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{catId}")
    public ProductCategoryResponse getCategoryById(@PathVariable Long catId){
        ProductCategory category = productCategoryService.getCategoryById(catId)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + catId));

        return productCategoryService.getCategoryResponse(category);
    }

    @GetMapping
    public List<ProductCategoryResponse> getAllCategories(){
        List<ProductCategoryResponse> categoryResponses = new ArrayList<>();
        productCategoryService.getAllCategory()
                .forEach(category -> {
                    ProductCategoryResponse response = productCategoryService.getCategoryResponse(category);
                    categoryResponses.add(response);
                });
        return categoryResponses;
    }

    @GetMapping("/slug/{slug}")
    public ProductCategoryResponse getCategoryBySlug(@PathVariable String slug){
        ProductCategory category = productCategoryService.getCategoryBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Product Category", "Slug", "" + slug));

        return productCategoryService.getCategoryResponse(category);
    }

    @GetMapping("/slug-available/{slug}")
    public ResourceAvailability checkResourceAvailability(@PathVariable String slug){
        Boolean isAvailable = !productCategoryService.slugExists(slug);
        return new ResourceAvailability(isAvailable);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductCategoryResponse addNewCategory(@RequestPart(value = "category") String categoryJson,
                                                  @RequestPart(name = "file", required = false) MultipartFile file) {

        try {
            ProductCategoryRequest request = productCategoryService.convertCategoryString(categoryJson);

            Long image = productCategoryService.getImage(file, request.getName(), request.getUrl());
            String slug = productCategoryService.formatSlug(request.getSlug());
            ProductCategory category = new ProductCategory(request.getName(), slug, request.getDescription());

            if (request.getParent() != null) category.setParent(request.getParent());
            if (image != null) category.setImage(image);

            category = productCategoryService.addCategory(category);

            return productCategoryService.getCategoryResponse(category);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BadRequestException("Request is bad. Check the form data!", ex);
        }
    }

    @PutMapping(path = "/{catId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductCategoryResponse updateCategory(@RequestPart("category") String categoryJson,
                                                  @RequestPart(name = "file", required = false) MultipartFile file,
                                                  @PathVariable Long catId){
        ProductCategory category = productCategoryService.getCategoryById(catId)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + catId));

        try {
            ProductCategoryRequest request = productCategoryService.convertCategoryString(categoryJson);

            Long image = productCategoryService.updateImage(category.getImage(), file,
                    request.getName(), request.getUrl());
            String slug = !category.getSlug().equalsIgnoreCase(request.getSlug())
                    ? productCategoryService.formatSlug(request.getSlug()) : request.getSlug();

            category.setName(request.getName());
            category.setSlug(slug);
            category.setDescription(request.getDescription());
            if (request.getParent() != null) category.setParent(request.getParent());
            if (image != null) category.setImage(image);

            category = productCategoryService.updateCategory(category);

            return productCategoryService.getCategoryResponse(category);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BadRequestException("Request is bad. Check the form data!", ex);
        }
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId){
        ProductCategory category = productCategoryService.getCategoryById(catId).orElse(null);
        if(category != null){
            productCategoryService.deleteImage(category.getImage());
            productCategoryService.deleteCategory(category.getId());
        }
    }

}
