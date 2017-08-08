package com.gladunalexander.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Alexander Gladun
 * Service for image operations
 */

@Service
public class ImageWorkerService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageWorkerService.class);

    @Value("${resources.img.path}")
    private String rootDir;

    @Autowired
    private AwsS3Service awsS3Service;

    /**
     * Saves an image to file system and AWS S3
     * @param file Multipart file
     */
    public void saveImage(MultipartFile file){
        File imgFile = new File(rootDir + File.separator + file.getOriginalFilename());
        try {
            file.transferTo(imgFile);
            awsS3Service.storeProductImageToS3(imgFile);
        } catch (IOException e) {
            LOG.error("Image saving failed...");
            throw new RuntimeException("Image saving failed...");
        }
    }

    /**
     * Remove an image from File System and Amazon S3 Service
     * @param imageName Image name of file
     */
    public void removeImage(String imageName) {
        if (!StringUtils.isEmpty(imageName)) {
            Path fileToDeletePath = Paths.get(rootDir + File.separator + imageName);
            try {
                Files.delete(fileToDeletePath);
                LOG.info("File " + imageName + " is deleted.");
                awsS3Service.removeProductImagefromS3(imageName);
            } catch (IOException e) {
                LOG.warn("Deleting file " + imageName + " failed.");
                throw new RuntimeException("Removing file failed failed...");
            }
        }
    }
}
