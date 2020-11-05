package com.habeebcycle.marketplace.service;

import com.habeebcycle.marketplace.exception.ApplicationException;
import com.habeebcycle.marketplace.model.entity.Image;
import com.habeebcycle.marketplace.repository.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.apache.http.entity.ContentType.*;


@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final String fileStorageLocation;
    private final Long fileMaxSize;
    private final Path fileStoragePath;

    public ImageService(ImageRepository imageRepository,
                        @Value("${app.file.storage.location:media-storage}") String fileStorageLocation,
                        @Value("${app.file.storage.max-size:2048000}") Long fileMaxSize) {
        this.imageRepository = imageRepository;
        this.fileStorageLocation = fileStorageLocation;
        this.fileMaxSize = fileMaxSize;

        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new ApplicationException("Issue in creating file directory", e);
        }
    }

    public Image getImage(Long id){
        return imageRepository.findById(id).orElse(null);
    }

    public List<Image> getAllImages(){return imageRepository.findAll();}

    public List<Image> getImages(List<Long> ids){
        return imageRepository.findAllById(ids);
    }

    public Image addImage(Image image){
        return imageRepository.save(image);
    }

    public List<Image> addImages(List<Image> images){
        return imageRepository.saveAll(images);
    }

    public Image updateImage(Image image){
        return imageRepository.save(image);
    }

    public List<Image> updateImages(List<Image> images){
        return imageRepository.saveAll(images);
    }

    public void deleteImage(Long id){
        imageRepository.deleteById(id);
    }

    public void deleteImages(List<Long> ids){
        imageRepository.deleteAll(imageRepository.findAllById(ids));
    }

    public void deleteAllImages(){deleteAllFiles(); imageRepository.deleteAll();}

    public Image storeFile(MultipartFile file, String folder, String name, String url) {
        Image image = null;
        if(file == null || file.isEmpty() || !isMimeTypeSupported(file.getContentType()) || file.getSize() > fileMaxSize){
            if(url != null && !url.equals("")){
                image = new Image();
                image.setAlt(name);
                image.setExternal(true);
                image.setSrc(url);
                image.setName(name);

                return image;
            }
            return image;
        }

        try {
            String ext = getFileExtension(file.getOriginalFilename());
            String fileName = folder + "-" + UUID.randomUUID() + "." + ext;
            Path filePath = Paths.get(fileStoragePath + "\\" + folder + "\\" + fileName);

            image = new Image();
            image.setSrc(getImageUrl(fileName, folder));
            image.setName(fileName);
            image.setAlt(name);
            image.setExt(ext);
            image.setMime(file.getContentType());
            image.setSize(file.getSize());
            image.setExternal(false);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {}
        return image;
    }

    public Boolean deleteFile(Long id, String folder){
        if(id != null) {
            try {
                Image image = getImage(id);
                if (image != null) {
                    Path filePath = Paths.get(fileStoragePath + "\\" + folder + "\\" + image.getName());
                    return Files.deleteIfExists(filePath);
                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public void deleteAllFiles(){
        try {
            Path path = Paths.get(fileStoragePath + "\\");
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    public Image updateFile(MultipartFile file, Long id, String folder, String name, String url){
        deleteFile(id, folder);
        return storeFile(file, folder, name, url);
    }

    /* {@link TODO}
     *
     * @description Write a batch that runs every night
     * to remove unlinked images and files.
     */

    private Boolean isMimeTypeSupported(String contentType){
        return Arrays.asList(IMAGE_PNG.getMimeType(), IMAGE_JPEG.getMimeType(),
                IMAGE_SVG.getMimeType(), IMAGE_WEBP.getMimeType())
                .contains(contentType);
    }

    private String getFileExtension(String fileName){
        return FilenameUtils.getExtension(fileName);
    }

    private String getImageUrl(String fileName, String folder){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/media/image/" + folder + "/")
                .path(fileName)
                .toUriString();
    }
}
