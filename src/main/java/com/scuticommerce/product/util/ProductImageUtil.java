package com.scuticommerce.product.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;

@Component
public class ProductImageUtil {

    private static final String BUCKET_NAME = "product-images-scuti";

    public static AmazonS3 s3client;

    @Autowired
    public void ProductImageUtil() {

        String awskey =  "badkey";//System.getenv("awskey");
        String secret = "/LzA6sS/MaTOXIJKKPLmLA9Dg+0NLGLNPEYCe7oT";//System.getenv("secret");

        System.out.println(awskey);

        AWSCredentials credentials = new BasicAWSCredentials(awskey , secret);

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();

    }

    public  void deleteFiles(AmazonS3 s3client, String bucketNameToUpload) {
        s3client.deleteObject(bucketNameToUpload,"new-video.mp4");

        String objkeyArr[] = {
                "new-video.mp4",
                "file.txt"
        };

        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketNameToUpload)
                .withKeys(objkeyArr);
        s3client.deleteObjects(delObjReq);
    }

    public String uploadToS3(String fileName) {

        createBucket(s3client);

        PutObjectResult result = s3client.putObject(
                BUCKET_NAME,
                fileName,
                new File(fileName)
        );

        URL url = s3client.getUrl(BUCKET_NAME,fileName);

        return url.getHost()+url.getFile();
    }

    public void readFilesFromS3(AmazonS3 s3client) {
        //list buckets
        List<Bucket> buckets = s3client.listBuckets();
        String bucketName = "";
        for(Bucket bucket : buckets) {

            bucketName = bucket.getName();
            System.out.println(bucketName);
        }

        //list objects in bucket
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println("Item Name "+os.getKey());
        }
    }

    private static void createBucket(AmazonS3 s3client){


        if(s3client.doesBucketExistV2(BUCKET_NAME)) {
            System.out.println("Bucket name is not available."
                    + " Try again with a different Bucket name.");
            return;
        }

        s3client.createBucket(BUCKET_NAME);

    }
}
