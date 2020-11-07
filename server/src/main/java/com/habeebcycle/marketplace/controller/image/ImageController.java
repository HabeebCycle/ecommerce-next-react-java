package com.habeebcycle.marketplace.controller.image;

import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.service.ImageService;
import com.habeebcycle.marketplace.util.ApplicationConstants;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping(ApplicationConstants.IMAGE_ENDPOINT)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //"http://localhost/media/image/product-category/product-category-d4aa8eac-4ced-4cda-892d-0e2507727a6e.png"
    //"image":{"createdAt":"2020-11-05T10:11:41.509Z","updatedAt":"2020-11-05T10:11:41.509Z","createdBy":null,
    // "updatedBy":null,"id":1,"src":"http://localhost/media/image/product-category/product-category-d4aa8eac-4ced-4cda-892d-0e2507727a6e.png",
    // "name":"product-category-d4aa8eac-4ced-4cda-892d-0e2507727a6e.png","alt":"Clothing and Apparels","ext":"png","mime":"image/png","size":356837,"external":false}
    @GetMapping("product-category/{imageName}")
    public ResponseEntity<?> getProductCategoryImage(@PathVariable String imageName, HttpServletRequest request){
        byte[] imgRes = new byte[0];
        Optional<Image> image = imageService.getImageByName(imageName);
        if(image.isPresent()){
            imgRes = imageService.getFile(image.get(), "product-category");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageService.getMimeType(imageName, request))).body(imgRes);
    }
}
