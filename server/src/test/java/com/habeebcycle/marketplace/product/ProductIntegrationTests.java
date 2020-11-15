package com.habeebcycle.marketplace.product;

import com.habeebcycle.marketplace.config.TestSecurityConfig;
import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.model.entity.product.Product;
import com.habeebcycle.marketplace.model.entity.product.ProductDetails;
import com.habeebcycle.marketplace.payload.product.ProductRequest;
import com.habeebcycle.marketplace.service.ImageService;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import com.habeebcycle.marketplace.service.ProductService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import com.habeebcycle.marketplace.util.HelperClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TestSecurityConfig.class},
        properties = {"spring.main.allow-bean-definition-overriding=true", "spring.codec.max-in-memory-size=20MB",
                "spring.datasource.url=jdbc:h2:mem:marketplace-db", "spring.jpa.properties.hibernate.dialect=",
                "app.file.storage.location=media-test"})
public class ProductIntegrationTests {

    @Autowired private WebTestClient client;
    @Autowired ProductService productService;
    @Autowired ImageService imageService;
    @Autowired private ProductCategoryService categoryService;

    @BeforeEach
    void setUpDB(){
        productService.deleteAllProduct();
        imageService.deleteAllImages();
    }

    @Test
    void testGetEmptyProduct(){
        long badId = 1L;

        getAndVerifyProduct("")
                .jsonPath("$.length()").isEqualTo(0);
        getAndVerifyProduct("/category/" + badId)
                .jsonPath("$.length()").isEqualTo(0);
        getAndVerifyProduct("/price/" + badId)
                .jsonPath("$.length()").isEqualTo(0);
        getAndVerifyProduct("/price-range/" + badId + "/" + badId)
                .jsonPath("$.length()").isEqualTo(0);

        String slug = "clothing-and-apparels";
        getAndVerifyProduct("/slug-available/" + slug)
                .jsonPath("$.available").isEqualTo(true);

        getAndVerifyNotFoundProduct("" + badId);
        getAndVerifyNotFoundProduct("/slug/" + slug);
    }

    @Test
    void testPostProduct() throws Exception {
        postAndVerifyProduct("My Simple Product", true, true, true);
        assertEquals(1, productService.getProductCount());
        assertEquals(1, categoryService.getCategoryCount());
        assertEquals(3, imageService.getAllImages().size());
        assertEquals(1, productService.getOnSalesProducts().size());
        assertEquals(productService.getNumberOfProducts(), productService.getProductCount());

        Product foundProduct = productService.getAllProduct().get(0);
        assertNotNull(foundProduct.getThumbnail());
        assertNotNull(foundProduct.getCategory());
        assertNull(foundProduct.getOwner());
        assertEquals(foundProduct.getCategory().getId(), categoryService.getAllCategory().get(0).getId());
        assertNotNull(foundProduct.getProductDetails().getImages());
        assertFalse(foundProduct.getProductDetails().getFeatured());

        getAndVerifyProduct("")
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].slug").isEqualTo(foundProduct.getSlug());

        getAndVerifyProduct("/slug-available/" + foundProduct.getSlug())
                .jsonPath("$.available").isEqualTo(false);

        assertNotNull(getAndVerifyProduct("/" + foundProduct.getId()).returnResult().getResponseBody());
        //System.out.println(new String(getAndVerifyCategory("/" + pCat.getId()).returnResult().getResponseBody()));

        List<Image> pImages = imageService.getAllImages();
        List<Long> imgs = new ArrayList<>();
        pImages.forEach(image -> imgs.add(image.getId()));
        imgs.remove(foundProduct.getThumbnail());

        assertEquals(HelperClass.getStringFromLongList(imgs), foundProduct.getProductDetails().getImages());
        assertEquals(getAndVerifyImage("/media/image/product/" + pImages.get(0).getName())
                .returnResult().getResponseBody().length, getImage().getFile().length());


        postAndVerifyProduct("My Simple Product", false, true, false);
        assertEquals(2, productService.getProductCount());
        assertEquals(1, categoryService.getCategoryCount());
        assertEquals(4, imageService.getAllImages().size());
        assertEquals(1, productService.getOnSalesProducts().size());
        assertEquals(productService.getNumberOfProducts(), productService.getProductCount());

        foundProduct = productService.getAllProduct().get(1);
        assertNotNull(foundProduct.getThumbnail());
        assertNotNull(foundProduct.getCategory());
        assertNull(foundProduct.getOwner());
        assertEquals(foundProduct.getCategory().getId(), categoryService.getAllCategory().get(0).getId());
        assertSame("", foundProduct.getProductDetails().getImages());
        assertFalse(foundProduct.getProductDetails().getFeatured());

        getAndVerifyProduct("")
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[1].slug").isEqualTo(foundProduct.getSlug());

        Image pImg = imageService.getImage(foundProduct.getThumbnail());

        assertEquals(getAndVerifyImage("/media/image/product/" + pImg.getName())
                .returnResult().getResponseBody().length, getImage().getFile().length());
    }

    WebTestClient.BodyContentSpec getAndVerifyProduct(String path){
        return client.get()
                .uri(ApplicationConstants.PRODUCT_ENDPOINT + path)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    void getAndVerifyNotFoundProduct(String path){
        client.get()
                .uri(ApplicationConstants.PRODUCT_ENDPOINT + path)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    void postAndVerifyProduct(String name, Boolean sales, Boolean withThumbnail, Boolean withImages) throws Exception {

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        if(withThumbnail) multipartBodyBuilder.part("file", getImage());//.filename("user-image-.png");
        if(withImages) multipartBodyBuilder.part("files", getImage());
        if(withImages) multipartBodyBuilder.part("files", getImage());

        multipartBodyBuilder.part("product", createProductEntity(name, sales).getBytes());//.contentType(MediaType.MULTIPART_FORM_DATA);

        client.post()
                .uri(ApplicationConstants.PRODUCT_ENDPOINT)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    Resource getImage() throws Exception{
        Path path = Paths.get("C:\\Users\\habee\\Desktop\\Photo\\user-image-.png");
        return new UrlResource(path.toUri());
    }

    String createProductEntity(String name, Boolean sales){
        ProductRequest request = new ProductRequest();
        request.setShortDescription("Description for " + name);
        if(sales) {
            request.setRegularPrice(BigDecimal.valueOf(20.50));
            request.setSalesPrice(BigDecimal.valueOf(20.50));
            request.setSalesStart(LocalDateTime.now());
            request.setSalesEnd(LocalDateTime.now().plusMinutes(2));
        }
        request.setSku("SKU-" + (int)(Math.random()*9) + 1);
        request.setType("Simple Product");
        request.setStatus("In Stock");
        request.setWeight("600g");
        request.setDimension("20mmx40mmx80mm");
        request.setStock(20);
        request.setNotes("Product note");

        request.setTitle(name);
        request.setCategory(createCategory());
        request.setSlug(name.toLowerCase().replace(" ", "-"));
        request.setPrice(BigDecimal.valueOf((Math.random() * 20) + 10).setScale(2, RoundingMode.UP));
        request.setDescription("Long Description for " + name);
        request.setOwner(null);
        request.setUrl("");

        return request.toString();
    }


    Long createCategory(){
        if(categoryService.getCategoryCount() > 0){
            return categoryService.getAllCategory().get(0).getId();
        }
        ProductCategory category = new ProductCategory();
        category.setName("Uncategorized");
        category.setSlug("uncategorized");

        return categoryService.addCategory(category).getId();
    }

    WebTestClient.BodyContentSpec getAndVerifyImage(String path){
        return client.get()
                .uri(path)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }
}
