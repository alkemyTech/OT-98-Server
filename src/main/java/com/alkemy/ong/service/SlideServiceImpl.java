package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.response.ListSlidesResponse;
import com.alkemy.ong.repository.ISlideRepository;
import com.alkemy.ong.service.abstraction.ICreateSlideService;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.service.abstraction.IListSlidesService;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import com.alkemy.ong.common.s3.S3ObjectHelper;
import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.CreateSlideRequest;
import javax.persistence.EntityNotFoundException;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideServiceImpl implements IDeleteSlideService, IListSlidesService, ICreateSlideService {

  @Autowired
  private ISlideRepository slideRepository;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  S3ObjectHelper s3ObjectHelper;

  @Transactional
  @Override
  public void delete(long id) throws EntityNotFoundException {
    if (!slideRepository.existsById(id)) {
      throw new EntityNotFoundException("Slide not found!");
    }
    slideRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  @Override
  public ListSlidesResponse list() {
    List<Slide> slides = slideRepository.findAll();

    return convertUtils.listSlidesToResponse(slides);
  }

  @Transactional
  @Override
  public Slide create(CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException {
    Slide slide = new Slide();
    String imageUrl = uploadImage(createSlideRequest.getImage(), createSlideRequest.getFileName(), createSlideRequest.getImageContentType());
    slide.setImageUrl(imageUrl);
    slide.setText(createSlideRequest.getText());
    if(createSlideRequest.getOrder() == 0) {
      slide.setOrder(slideRepository.getMaxOrder() + 1);
    } else slide.setOrder(createSlideRequest.getOrder());
    return slideRepository.save(slide);
  }

  private String uploadImage(String image, String fileName, String contentType)
      throws ExternalServiceException {
    byte[] decodedBytes = Base64.getDecoder().decode(image);
    InputStream inputStream = new ByteArrayInputStream(decodedBytes);
    ContentType imageContentType = ContentType.create(contentType);
    String imageUrl = s3ObjectHelper.upload(inputStream,fileName, imageContentType);
    return imageUrl;
  }

}
