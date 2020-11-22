package com.habeebcycle.marketplace.component.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.habeebcycle.marketplace.exception.NotFoundException;
import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.payload.product.ProductResponse;
import com.habeebcycle.marketplace.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryResolver implements GraphQLQueryResolver {

    private ProductService productService;

    public ProductQueryResolver(ProductService productService) {
        this.productService = productService;
    }

    //@PreAuthorize("isAnonymous()")
    public ProductResponse getProduct(Long id){
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Product Category", "Id", "" + id));

        return productService.getProductResponse(product);
    }

    //@PreAuthorize("isAnonymous()")
    public List<ProductResponse> getProducts(){
        List<ProductResponse> productResponses = new ArrayList<>();
        productService.getAllProduct()
                .forEach(product -> {
                    ProductResponse response = productService.getProductResponse(product);
                    productResponses.add(response);
                });
        return productResponses;
    }
}
