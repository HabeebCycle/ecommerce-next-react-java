package com.habeebcycle.marketplace.service;

import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.util.ChildCategory;
import com.habeebcycle.marketplace.payload.category.ProductCategoryResponse;
import com.habeebcycle.marketplace.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository ;
    private final ImageService imageService;
    private final String IMAGE_FOLDER = "product-category";

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, ImageService imageService) {
        this.productCategoryRepository = productCategoryRepository;
        this.imageService = imageService;
    }

    public Optional<ProductCategory> getCategoryById(Long id){
        return productCategoryRepository.findById(id);
    }

    public Optional<ProductCategory> getCategoryBySlug(String slug){
        return productCategoryRepository.findBySlug(slug);
    }

    public List<ProductCategory> getAllCategory(){
        return productCategoryRepository.findAll();
    }

    public List<ProductCategory> getChildrenCategory(Long parent){
        return productCategoryRepository.findByParent(parent);
    }

    public Boolean slugExists(String slug){
        return productCategoryRepository.existsBySlug(slug);
    }

    public ProductCategory addCategory(ProductCategory category){
        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(ProductCategory category){
        return productCategoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        productCategoryRepository.deleteById(id);
    }

    public void deleteCategory(ProductCategory category){
        productCategoryRepository.delete(category);
    }

    public ProductCategoryResponse getCategoryResponse(ProductCategory category){
        List<ChildCategory> children = collateChildren(category.getParent());
        Image image = imageService.getImage(category.getImage());
        return new ProductCategoryResponse(category.getId(), category.getName(), category.getSlug(),
                category.getDescription(), children, image);
    }

    public List<ChildCategory> collateChildren(Long parent){
        List<ChildCategory> childCategories = new ArrayList<>();
        getChildrenCategory(parent)
                .forEach(category -> {
                    ChildCategory childCategory = new ChildCategory(category.getId(), category.getName(),
                            category.getSlug(), "/api/v1/product-category/" + category.getId());
                    childCategories.add(childCategory);
                });
        return childCategories;
    }

    public Long getImage(MultipartFile file, String name, String url){
        Image image = imageService.storeFile(file, IMAGE_FOLDER, name, url);
        if(image != null){
            image = imageService.addImage(image);
            return image.getId();
        }
        return null;
    }

    public Long updateImage(Long id, MultipartFile file, String name, String url){
         if(id == null){
            return getImage(file, name, url);
        }else{
            Image image = imageService.updateFile(file, id, IMAGE_FOLDER, name, url);
            image.setId(id);
            return imageService.updateImage(image).getId();
        }
    }

    public String formatSlug(String slug){
        if(slugExists(slug)){
            slug = slug + "-" + ((int)(Math.random() + 1));
            formatSlug(slug);
        }
        return slug;
    }
}
