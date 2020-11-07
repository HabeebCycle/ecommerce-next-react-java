package com.habeebcycle.marketplace.component.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.payload.category.ProductCategoryResponse;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCategoryQueryResolver implements GraphQLQueryResolver {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryQueryResolver(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    //@PreAuthorize("isAnonymous()")
    public ProductCategoryResponse getProductCategory(Long id){
        ProductCategory category = productCategoryService.getCategoryById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        return productCategoryService.getCategoryResponse(category);
    }

    //@PreAuthorize("isAnonymous()")
    public List<ProductCategoryResponse> getProductCategories(){
        List<ProductCategoryResponse> categoryResponses = new ArrayList<>();
        productCategoryService.getAllCategory()
                .forEach(category -> {
                    ProductCategoryResponse response = productCategoryService.getCategoryResponse(category);
                    categoryResponses.add(response);
                });
        return categoryResponses;
    }

}
