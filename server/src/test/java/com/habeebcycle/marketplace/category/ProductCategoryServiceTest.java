package com.habeebcycle.marketplace.category;

import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:marketplace-db", "spring.jpa.properties.hibernate.dialect="})
@Transactional(propagation = NOT_SUPPORTED)
public class ProductCategoryServiceTest {

    @Autowired ProductCategoryService productCategoryService;

    private ProductCategory savedCategory;

    @BeforeEach
    public void setupDb() {
        productCategoryService.deleteAllCategory();

        ProductCategory category = new ProductCategory("cat name", "cat-slug", "cat description");
        savedCategory = productCategoryService.addCategory(category);
        assertNull(savedCategory.getImage());
        assertNull(savedCategory.getParent());
        assertEqualsCategory(category, savedCategory);
    }

    @Test
    public void testCreateCategory() {
        String sameSlug = "cat-slug";

        ProductCategory newCategory = new ProductCategory("cat name", sameSlug, "cat description");
        newCategory.setParent(savedCategory.getId());
        productCategoryService.addCategory(newCategory);

        ProductCategory foundCategory = productCategoryService.getCategoryById(newCategory.getId()).get();

        assertNotEquals(sameSlug, foundCategory.getSlug());
        assertEquals(foundCategory.getParent(), savedCategory.getId());
        assertEqualsCategory(newCategory, foundCategory);

        assertEquals(2, productCategoryService.getCategoryCount());
    }

    @Test
    public void testUpdateCategory() {
        savedCategory.setName("cat name 2");
        productCategoryService.updateCategory(savedCategory);

        ProductCategory foundCategory = productCategoryService.getCategoryById(savedCategory.getId()).get();
        assertEquals(savedCategory.getSlug(), foundCategory.getSlug());
        assertEquals("cat name 2", foundCategory.getName());
    }

    @Test
    public void testDeleteCategory() {
        productCategoryService.deleteCategory(savedCategory.getId());
        assertFalse(productCategoryService.categoryExistsById(savedCategory.getId()));
    }

    @Test
    public void testGetChildrenCategory() {
        ProductCategory newCategory = new ProductCategory("cat name", "cat-slug", "cat description");
        newCategory.setParent(savedCategory.getId());
        ProductCategory newCat = productCategoryService.addCategory(newCategory);

        List<ProductCategory> childList = productCategoryService.getChildrenCategory(savedCategory.getId());

        assertThat(childList, hasSize(1));
        assertEqualsCategory(newCat, childList.get(0));
    }

    private void assertEqualsCategory(ProductCategory expectedCategory, ProductCategory actualCategory) {
        assertEquals(expectedCategory.getName(),        actualCategory.getName());
        assertEquals(expectedCategory.getSlug(),   actualCategory.getSlug());
        assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
    }
}
