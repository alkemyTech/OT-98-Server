package com.alkemy.ong.common;

import com.alkemy.ong.config.AmazonConfig;
import com.alkemy.ong.exception.ExternalServiceException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadImage {

  @Autowired
  private AmazonConfig amazonConfig;
  @Autowired
  private AmazonS3 amazonS3 = amazonConfig.initialize();

  public String upload(InputStream inputStream, String fileName, ContentType contentType)
      throws ExternalServiceException {
    String bucketName = amazonConfig.getBucketName();
    String validatedFileName = FileUtils.getFileNameOrDefault(fileName);
    String fileUrl = "";
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentType.toString());
      uploadImageTos3Bucket(validatedFileName, inputStream, bucketName, metadata);
      fileUrl = amazonS3.getUrl(bucketName, validatedFileName).toString();
    } catch (AmazonClientException e) {
      throw new ExternalServiceException("The file could not be uploaded");
    }
    return fileUrl;
  }

  private void uploadImageTos3Bucket(String validatedFileName, InputStream inputStream, String bucketName, ObjectMetadata metadata) {
    amazonS3.putObject(new PutObjectRequest(bucketName, validatedFileName, inputStream, metadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }
}
