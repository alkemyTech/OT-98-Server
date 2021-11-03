package com.alkemy.ong.common.s3;

import com.alkemy.ong.common.FileUtils;
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
import org.springframework.stereotype.Service;

@Service
public class UploadS3ObjectHelper {

  @Autowired
  private AmazonConfig amazonConfig;

  @Autowired
  private AmazonS3 amazonS3;

  public String upload(InputStream inputStream, String fileName, ContentType contentType)
      throws ExternalServiceException {
    String bucketName = amazonConfig.getBucketName();
    String fileNameOrDefault = FileUtils.getFileNameOrDefault(fileName);
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(contentType.toString());
      uploadImageTos3Bucket(fileNameOrDefault, inputStream, bucketName, metadata);
      return amazonS3.getUrl(bucketName, fileNameOrDefault).toString();
    } catch (AmazonClientException e) {
      throw new ExternalServiceException("The file could not be uploaded.");
    }
  }

  private void uploadImageTos3Bucket(String fileName, InputStream inputStream,
      String bucketName, ObjectMetadata metadata) {
    amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }
}
