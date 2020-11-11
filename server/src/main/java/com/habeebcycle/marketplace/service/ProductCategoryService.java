package com.habeebcycle.marketplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.util.ChildCategory;
import com.habeebcycle.marketplace.payload.category.ProductCategoryRequest;
import com.habeebcycle.marketplace.payload.category.ProductCategoryResponse;
import com.habeebcycle.marketplace.payload.user.UserSummary;
import com.habeebcycle.marketplace.repository.ProductCategoryRepository;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository ;
    private final ImageService imageService;
    private final UserService userService;
    private final String IMAGE_FOLDER = "product-category";

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, ImageService imageService,
                                  UserService userService) {
        this.productCategoryRepository = productCategoryRepository;
        this.imageService = imageService;
        this.userService = userService;
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

    public Boolean categoryExistsById(Long id){
        return productCategoryRepository.existsById(id);
    }

    public Long getCategoryCount(){
        return productCategoryRepository.count();
    }

    public ProductCategory addCategory(ProductCategory category){
        category.setSlug(formatSlug(category.getSlug()));
        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(ProductCategory category){
        getCategoryById(category.getId())
                .ifPresent(oldCat -> category.setSlug(
                oldCat.getSlug().equalsIgnoreCase(category.getSlug())
                        ? category.getSlug() : formatSlug(category.getSlug()) ));
        return productCategoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        productCategoryRepository.deleteById(id);
    }

    public void deleteAllCategory(){
        productCategoryRepository.deleteAll();
    }

    public ProductCategoryResponse getCategoryResponse(ProductCategory category){
        List<ChildCategory> children = collateChildren(category.getId());
        Image image = category.getImage() != null ? imageService.getImage(category.getImage()) : null;
        ProductCategoryResponse response = new ProductCategoryResponse(category.getId(), category.getName(), category.getSlug(),
                category.getDescription(), children, image);
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        userService.getUser(category.getCreatedBy()).ifPresent(user -> {
            response.setCreatedBy(new UserSummary(user.getId(), user.getUsername(), user.getName()));
        });
        userService.getUser(category.getUpdatedBy()).ifPresent(user -> {
            response.setUpdatedBy(new UserSummary(user.getId(), user.getUsername(), user.getName()));
        });
        return response;
    }

    public List<ChildCategory> collateChildren(Long parent){
        List<ChildCategory> childCategories = new ArrayList<>();
        getChildrenCategory(parent)
                .forEach(category -> {
                    ChildCategory childCategory = new ChildCategory(category.getId(), category.getName(),
                            category.getSlug(), ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + "/" + category.getId());
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
        imageService.deleteFile(imgId, IMAGE_FOLDER);
        imageService.deleteImage(imgId);
    }

    public ProductCategoryRequest convertCategoryString(String categoryJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(categoryJson, ProductCategoryRequest.class);
    }

    public String formatSlug(String slug){
        if(slugExists(slug)){
            slug = slug + "-" + ((int)(Math.random() + 1));
            formatSlug(slug);
        }
        return slug.toLowerCase();
    }
}
