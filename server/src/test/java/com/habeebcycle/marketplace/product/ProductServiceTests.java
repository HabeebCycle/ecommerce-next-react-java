package com.habeebcycle.marketplace.product;

import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.model.entity.product.ProductDetails;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import com.habeebcycle.marketplace.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:marketplace-db", "spring.jpa.properties.hibernate.dialect=",
        "spring.jpa.show-sql=false", "spring.jpa.properties.hibernate.format_sql=false"})
@Transactional(propagation = NOT_SUPPORTED)
public class ProductServiceTests {

    @Autowired private ProductService productService;
    @Autowired private ProductCategoryService categoryService;

    private Product savedProduct;

    @BeforeEach
    void setupDb() {
        productService.deleteAllProduct();
        savedProduct = productService.addProduct(createProductEntity("My Product 1", false, false));
        assertNull(savedProduct.getThumbnail());
        assertNotNull(savedProduct.getCategory());
        assertEquals("Uncategorized", savedProduct.getCategory().getName());
        assertEquals(1, productService.getNumberOfProductsInCategory(savedProduct.getCategory()));
        assertEquals(0, productService.getOnSalesProducts().size());

        /*System.out.println("Time NOW: " + LocalDateTime.now());
        System.out.println("Time END: " + savedProduct.getProductDetails().getSalesEnd());
        System.out.println("Time START: " + savedProduct.getProductDetails().getSalesStart());*/
    }

    @Test
    void testCreateProduct(){
        Product newProduct = createProductEntity("New Created Product", true, true);
        productService.addProduct(newProduct);

        assertNull(newProduct.getThumbnail());
        assertNotNull(newProduct.getCategory());

        Product foundProduct = productService.getProductById(newProduct.getId()).get();
        assertEqualsProduct(foundProduct, newProduct);
        assertEquals("Uncategorized", foundProduct.getCategory().getName());

        assertEquals(productService.getNumberOfProducts(), productService.getProductCount());
        assertEquals(2, productService.getNumberOfProductsInCategory(foundProduct.getCategory()));
        assertEquals(1, productService.getOnSalesProducts().size());
        assertEquals(1, productService.getFeaturedProducts().size());
    }

    @Test
    void testUpdateProduct(){
        Product newProduct = createProductEntity("New Created Product", false, false);
        productService.addProduct(newProduct);

        assertNull(newProduct.getThumbnail());
        assertNotNull(newProduct.getCategory());
        assertEquals("Uncategorized", newProduct.getCategory().getName());

        Product foundProduct = productService.getProductById(newProduct.getId()).get();
        assertFalse(foundProduct.getProductDetails().getFeatured());
        assertEquals(0, productService.getOnSalesProducts().size());
        assertEquals(0, productService.getFeaturedProducts().size());
        assertEquals(2, productService.getProductCount());

        String slugTest = "my-product-1";
        foundProduct.getProductDetails().setFeatured(true);
        foundProduct.getProductDetails().setSalesStart(LocalDateTime.now());
        foundProduct.getProductDetails().setSalesEnd(LocalDateTime.now().plusMinutes(2));
        foundProduct.getProductDetails().setRegularPrice(BigDecimal.valueOf(20.50));
        foundProduct.getProductDetails().setSalesPrice(BigDecimal.valueOf(20.50));
        foundProduct.setSlug(slugTest);

        productService.updateProduct(foundProduct);

        assertNotEquals(slugTest, foundProduct.getSlug());
        assertTrue(foundProduct.getProductDetails().getFeatured());
        assertEquals(productService.getNumberOfProducts(), productService.getProductCount());
        assertEquals(2, productService.getNumberOfProductsInCategory(foundProduct.getCategory()));
        assertEquals(1, productService.getOnSalesProducts().size());
        assertEquals(1, productService.getFeaturedProducts().size());
        assertEquals(2, productService.getProductCount());
    }

    @Test
    void testDeleteProduct(){
        Product newProduct = createProductEntity("New Created Product", true, true);
        productService.addProduct(newProduct);

        assertNull(newProduct.getThumbnail());
        assertNotNull(newProduct.getCategory());
        assertEquals("Uncategorized", newProduct.getCategory().getName());
        assertEquals(2, productService.getProductCount());

        productService.deleteProduct(savedProduct.getId());

        assertEquals(1, productService.getProductCount());

        productService.deleteAllProduct();

        assertEquals(0, productService.getProductCount());

    }

    @Test
    void testMinMaxProduct(){
        Product newProduct = createProductEntity("New Created Product", true, true);
        productService.addProduct(newProduct);

        assertEquals(2, productService.getProductCount());
        assertNull(newProduct.getOwner());

        assertTrue(productService.getMaxProductPrice().compareTo(productService.getMinProductPrice()) > 0);

    }

    Product createProductEntity(String name, Boolean sales, Boolean featured){
        ProductDetails details = new ProductDetails();
        details.setShortDescription("Description for " + name);
        if(sales) {
            details.setRegularPrice(BigDecimal.valueOf(20.50));
            details.setSalesPrice(BigDecimal.valueOf(20.50));
            details.setSalesStart(LocalDateTime.now());
            details.setSalesEnd(LocalDateTime.now().plusMinutes(2));
        }
        details.setSku("SKU-" + (int)(Math.random()*9) + 1);
        details.setType("Simple Product");
        details.setStatus("In Stock");
        details.setWeight("600g");
        details.setDimension("20mmx40mmx80mm");
        details.setStock(20);
        details.setFeatured(featured);
        details.setNotes("Product note");

        Product product = new Product();
        product.setTitle(name);
        product.setCategory(createCategory());
        product.setSlug(name.toLowerCase().replace(" ", "-"));
        product.setPrice(BigDecimal.valueOf((Math.random() * 20) + 10).setScale(2, RoundingMode.UP));
        product.setDescription("Long Description for " + name);
        product.setProductDetails(details);

        return product;
    }


    private ProductCategory createCategory(){
        if(categoryService.getCategoryCount() > 0){
            return categoryService.getAllCategory().get(0);
        }
        ProductCategory category = new ProductCategory();
        category.setName("Uncategorized");
        category.setSlug(createSlug("Uncategorized"));

        return categoryService.addCategory(category);
    }

    private String createSlug(String slug){
        return slug.replace(" ", "-").toLowerCase();
    }

    private void assertEqualsProduct(Product expectedProduct, Product actualProduct) {
        assertEquals(expectedProduct.getTitle(),        actualProduct.getTitle());
        assertEquals(expectedProduct.getSlug(),   actualProduct.getSlug());
        assertEquals(expectedProduct.getPrice(),   actualProduct.getPrice());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getCategory().getId(), actualProduct.getCategory().getId());
    }
}
