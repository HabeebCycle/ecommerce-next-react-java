package com.habeebcycle.marketplace.category;

import com.habeebcycle.marketplace.config.TestSecurityConfig;
import com.habeebcycle.marketplace.exception.ApplicationException;
import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.model.entity.category.ProductCategory;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import com.habeebcycle.marketplace.service.ImageService;
import com.habeebcycle.marketplace.service.ProductCategoryService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TestSecurityConfig.class},
        properties = {"spring.main.allow-bean-definition-overriding=true", "spring.codec.max-in-memory-size=20MB",
                "spring.datasource.url=jdbc:h2:mem:marketplace-db", "spring.jpa.properties.hibernate.dialect="})
public class ProductCategoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WebTestClient client;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ImageService imageService;

    private

    @BeforeEach
    void setUpDB(){
        productCategoryService.deleteAllCategory();
        imageService.deleteAllImages();
    }

    @Test
    void testGetEmptyCategory(){
        getAndVerifyCategory("")
                .jsonPath("$.length()").isEqualTo(0);

        String slug = "clothing-and-apparels";
        getAndVerifyCategory("/slug-available/" + slug)
                .jsonPath("$.available").isEqualTo(true);
    }

    @Test
    void testInsertCategory() throws Exception {
        postAndVerifyCategory();

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(1, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);
        Image catImg = imageService.getAllImages().get(0);

        assertEquals(pCat.getImage(), catImg.getId());

        getAndVerifyCategory("")
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].slug").isEqualTo(pCat.getSlug());

        getAndVerifyCategory("/slug-available/" + pCat.getSlug())
                .jsonPath("$.available").isEqualTo(false);

        assertNotNull(getAndVerifyCategory("/" + pCat.getId()).returnResult().getResponseBody());
        //System.out.println(new String(getAndVerifyCategory("/" + pCat.getId()).returnResult().getResponseBody()));
        assertEquals(getAndVerifyImage("/media/image/product-category/" + catImg.getName()).returnResult().getResponseBody().length, getTestFile().length);
    }

    @Test
    void testInsertCategoryWithoutImage() throws Exception {
        postAndVerifyCategoryWithoutImage();

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(0, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);

        assertNull(pCat.getImage());

        getAndVerifyCategory("/" + pCat.getId())
                .jsonPath("$.name").isEqualTo(pCat.getName())
                .jsonPath("$.slug").isEqualTo(pCat.getSlug());

        getAndVerifyCategory("/slug-available/" + pCat.getSlug())
                .jsonPath("$.available").isEqualTo(false);

        assertNotNull(getAndVerifyCategory("/slug/" + pCat.getSlug()).returnResult().getResponseBody());
    }

    @Test
    void testUpdateCategory() throws Exception {
        postAndVerifyCategory();

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(1, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);
        Image catImg = imageService.getAllImages().get(0);

        assertEquals(pCat.getImage(), catImg.getId());

        updateAndVerifyCategory(pCat.getId());

        getAndVerifyCategory("")
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].slug").isEqualTo(pCat.getSlug());

        getAndVerifyCategory("/" + pCat.getId())
                .jsonPath("$.name").isEqualTo("Clothing and Apparels 2");

        catImg = imageService.getAllImages().get(0);

        assertNotNull(getAndVerifyCategory("/" + pCat.getId()).returnResult().getResponseBody());
        assertEquals(getAndVerifyImage("/media/image/product-category/" + catImg.getName()).returnResult().getResponseBody().length, getTestFile2().length);
    }

    @Test
    void testUpdateCategoryWithImage() throws Exception {
        postAndVerifyCategoryWithoutImage(); //No Image upload

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(0, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);

        assertNull(pCat.getImage());

        //Upload Image
        updateAndVerifyCategory(pCat.getId());

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(1, imageService.getAllImages().size());

        pCat = productCategoryService.getAllCategory().get(0);
        Image catImg = imageService.getAllImages().get(0);

        assertEquals(pCat.getImage(), catImg.getId());

    }

    @Test
    void testUpdateCategoryWithoutImage() throws Exception {
        postAndVerifyCategoryWithoutImage(); //No Image upload

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(0, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);

        assertNull(pCat.getImage());

        //Upload Image
        updateAndVerifyCategoryWithoutImage(pCat.getId());

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(0, imageService.getAllImages().size());

        pCat = productCategoryService.getAllCategory().get(0);

        assertNull(pCat.getImage());

        getAndVerifyCategory("/" + pCat.getId())
                .jsonPath("$.name").isEqualTo("Clothing and Apparels 2");

    }

    @Test
    void testDeleteCategory() throws Exception {
        postAndVerifyCategory(); //No Image upload

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(1, imageService.getAllImages().size());

        ProductCategory pCat = productCategoryService.getAllCategory().get(0);
        Image catImg = imageService.getAllImages().get(0);

        assertEquals(pCat.getImage(), catImg.getId());

        //Update Category
        updateAndVerifyCategory(pCat.getId());

        assertEquals(1, productCategoryService.getAllCategory().size());
        assertEquals(1, imageService.getAllImages().size());

        pCat = productCategoryService.getAllCategory().get(0);
        catImg = imageService.getAllImages().get(0);

        assertEquals(pCat.getImage(), catImg.getId());

        //Delete Category
        deleteAndVerifyCategory(pCat.getId());

        assertEquals(0, productCategoryService.getAllCategory().size());
        assertEquals(0, imageService.getAllImages().size());
        assertNull(getAndVerifyImage("/media/image/product-category/" + catImg.getName()).returnResult().getResponseBody());
    }

    String getTestRequest(){
        return  "{" +
                "\"name\":\"Clothing and Apparels\","+
                "\"slug\":\"clothing-and-apparels\","+
                "\"description\":\"Women and men clothing, wear and footwear\","+
                "\"url\":\"\","+
                "\"parent\":null"+
                "}";
    }

    String getTestRequest2(){
        return "{" +
                "\"name\":\"Clothing and Apparels 2\","+
                "\"slug\":\"clothing-and-apparels\","+
                "\"description\":\"Women and men clothing, wear and footwear\","+
                "\"url\":\"\","+
                "\"parent\":null"+
                "}";
    }

    byte[] getTestFile() {
        try {
            File file = new File("C:\\Users\\habee\\Desktop\\Photo\\5777417.png");
            return IOUtils.toByteArray(new FileInputStream(file));
        }catch (FileNotFoundException fnfe){
            throw new ApplicationException("File is not available", fnfe);
        }catch (IOException ioe){
            throw new ApplicationException("IO Exception occurred", ioe);
        }
    }

    byte[] getTestFile2() {
        try {
            File file = new File("C:\\Users\\habee\\Desktop\\Photo\\user-image-.png");
            return IOUtils.toByteArray(new FileInputStream(file));
        }catch (FileNotFoundException fnfe){
            throw new ApplicationException("File is not available", fnfe);
        }catch (IOException ioe){
            throw new ApplicationException("IO Exception occurred", ioe);
        }
    }

    /*WebTestClient.BodyContentSpec postAndVerifyCategory(CategoryRequest request){
        return client.post()
                .uri("/api/v1/product-category")
                .body(just(request), CategoryRequest.class)

                *//*.uri(uriBuilder -> uriBuilder
                    .path(SecurityConfigConstants.PRODUCT_CATEGORY_ENDPOINT)
                    .queryParam("category", request.getCategory())
                    .queryParam("file", request.getFile())
                    .build())*//*
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }*/
    void postAndVerifyCategory() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "5777417.png", "image/png", getTestFile());
        MockMultipartFile category = new MockMultipartFile("category", "", "application/json", getTestRequest().getBytes());


        //CAN ONLY MAKE A POST REQUEST
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT)
                .file(file)
                .file(category))
                .andExpect(status().is(200));
    }

    void postAndVerifyCategoryWithoutImage() throws Exception {
        MockMultipartFile category = new MockMultipartFile("category", "", "application/json", getTestRequest().getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT)
                //.file(file)
                .file(category))
                .andExpect(status().is(200));
    }

    void updateAndVerifyCategory(Long id) throws Exception {

        Path path = Paths.get("C:\\Users\\habee\\Desktop\\Photo\\user-image-.png");
        Resource resource = new UrlResource(path.toUri());

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", resource);//.filename("user-image-.png");
        multipartBodyBuilder.part("category", getTestRequest2().getBytes());//.contentType(MediaType.MULTIPART_FORM_DATA);

        client.put()
                .uri(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + "/" + id)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    void updateAndVerifyCategoryWithoutImage(Long id) throws Exception {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("category", getTestRequest2().getBytes());

        client.put()
                .uri(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + "/" + id)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    WebTestClient.BodyContentSpec getAndVerifyCategory(String path){
        return client.get()
                .uri(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + path)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    WebTestClient.BodyContentSpec getAndVerifyImage(String path){
        return client.get()
                .uri(path)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    void deleteAndVerifyCategory(Long id){
        client.delete()
                .uri(ApplicationConstants.PRODUCT_CATEGORY_ENDPOINT + "/" + id)
                .exchange()
                .expectStatus().isOk() ;
    }


}
