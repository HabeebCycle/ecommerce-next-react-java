package com.habeebcycle.marketplace.controller.category;

import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.payload.ResourceAvailability;
import com.habeebcycle.marketplace.payload.category.ProductCategoryRequest;
import com.habeebcycle.marketplace.payload.category.ProductCategoryResponse;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-category")
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

    @GetMapping("/slug-available")
    public ResourceAvailability checkResourceAvailability(@RequestParam(value = "slug") String slug){
        Boolean isAvailable = !productCategoryService.slugExists(slug);
        return new ResourceAvailability(isAvailable);
    }

    @PostMapping
    public ProductCategoryResponse addNewCategory(@NotNull @Valid @ModelAttribute ProductCategoryRequest request) {
        Long image = productCategoryService.getImage(request.getImage(), request.getName(), request.getUrl());
        String slug = productCategoryService.formatSlug(request.getSlug());
        ProductCategory category = new ProductCategory(request.getName(), slug, request.getDescription());

        if(request.getParent() > 0) category.setParent(request.getParent());
        if(image != null) category.setImage(image);

        category = productCategoryService.addCategory(category);

        return productCategoryService.getCategoryResponse(category);
    }

    @PutMapping("/{catId}")
    public ProductCategoryResponse updateCategory(@NotNull @Valid @ModelAttribute ProductCategoryRequest request,
                                                  @PathVariable Long catId){
        ProductCategory category = productCategoryService.getCategoryById(catId)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + catId));

        Long image = productCategoryService.updateImage(category.getImage(), request.getImage(),
                request.getName(), request.getUrl());
        String slug = !category.getSlug().equalsIgnoreCase(request.getSlug())
                ? productCategoryService.formatSlug(request.getSlug()) : request.getSlug();

        category.setName(request.getName());
        category.setSlug(slug);
        category.setDescription(request.getDescription());
        if(request.getParent() > 0) category.setParent(request.getParent());
        if(image != null) category.setImage(image);

        category = productCategoryService.updateCategory(category);

        return productCategoryService.getCategoryResponse(category);
    }

}
