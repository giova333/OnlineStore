package com.gladunalexander.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gladunalexander.backend.service.EmailService;
import com.gladunalexander.backend.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;

/**
 * @author Alexander Gladun
 * Basic Spring configuration class
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.gladunalexander.backend.persistence.repositories")
@EntityScan(basePackages = "com.gladunalexander.backend.persistence.domain")
@EnableTransactionManagement
public class ApplicationConfig {

    @Value("${datasource.driver}")
    private String driverClassName;

    @Value("${datasource.url}")
    private String dataSourceUrl;

    @Value("${datasource.username}")
    private String dataSourceUsername;

    @Value("${datasource.password}")
    private String dataSourcePassword;

    @Value("${aws.s3.profile.access.key}")
    private String awsAccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsSecretKey;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return new CommonsMultipartResolver();
    }

    @Bean
    public AmazonS3 s3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        return s3Client;
    }
}
