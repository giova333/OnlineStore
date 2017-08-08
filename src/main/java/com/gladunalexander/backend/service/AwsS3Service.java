package com.gladunalexander.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Alexander Gladun
 * Service class for images storage
 * at Amazon S3 service.
 */

@Service
public class AwsS3Service {

    private static final Logger LOG = LoggerFactory.getLogger(AwsS3Service.class);

    @Value("${aws.s3.bucket.name}")
    private String awsBucketName;

    @Value("${aws.s3.folder}")
    private String awsBucketFolder;

    @Autowired
    private AmazonS3 s3Client;

    /**
     * Saves an image
     * @param file The file to store
     */
    public void storeProductImageToS3(File file){
        ensureBucketExist(awsBucketName);
        s3Client.putObject(awsBucketName, awsBucketFolder + file.getName(), file);

        LOG.info("Image: " + file.getName() + " successfully saved");

    }

    /**
     * Deletes an image
     * @param imageName Image name of file
     */
    public void removeProductImagefromS3(String imageName){
        LOG.info("Removing current image: " + imageName);
        s3Client.deleteObject(awsBucketName, awsBucketFolder + imageName);
        LOG.info("Image: " + imageName + " succesfully removed");
    }

    /**
     * Ensure that bucket already exists, if doesn't
     * create a new bucket.
     * @param bucketName Amazon S3 bucket name
     */
    private void ensureBucketExist(String bucketName){
        if (!s3Client.doesBucketExist(awsBucketName)){
            LOG.info("Creating bucket with name: " + awsBucketName);
            s3Client.createBucket(bucketName);
        }
    }

}
