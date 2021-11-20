package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.s3.S3ObjectHelper;
import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.CreateSlideRequest;
import com.alkemy.ong.model.response.ListSlidesResponse;
import com.alkemy.ong.repository.ISlideRepository;
import com.alkemy.ong.service.abstraction.ICreateSlideService;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.service.abstraction.IGetSlideService;
import com.alkemy.ong.service.abstraction.IListSlidesService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideServiceImpl
    implements IDeleteSlideService, IListSlidesService, ICreateSlideService, IGetSlideService {

  @Autowired
  private ISlideRepository slideRepository;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private S3ObjectHelper s3ObjectHelper;

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

  @Transactional(readOnly = true)
  @Override
  public Slide getBy(Long id) throws EntityNotFoundException {
    Optional<Slide> slide = slideRepository.findById(id);
    if (slide.isEmpty()) {
      throw new EntityNotFoundException("Slide not found!");
    }

    return slide.get();
  }

  @Transactional
  @Override
  public Slide create(CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException {
    String imageUrl = uploadImage(createSlideRequest.getImage(), createSlideRequest.getFileName(),
        createSlideRequest.getImageContentType());
    int order = getSlideOrder(createSlideRequest.getOrder());
    Slide slide = buildSlide(createSlideRequest, imageUrl, order);
    return slideRepository.save(slide);
  }

  private String uploadImage(String image, String fileName, String contentType)
      throws ExternalServiceException {
    byte[] decodedBytes = Base64.getDecoder().decode(image);
    InputStream inputStream = new ByteArrayInputStream(decodedBytes);
    ContentType imageContentType = ContentType.create(contentType);
    return s3ObjectHelper.upload(inputStream, fileName, imageContentType);
  }

  private int getSlideOrder(int order) {
    if (order == 0) {
      return slideRepository.getMaxOrder() + 1;
    }
    return order;
  }

  private Slide buildSlide(CreateSlideRequest createSlideRequest, String imageUrl, int order) {
    Slide slide = new Slide();
    slide.setImageUrl(imageUrl);
    slide.setText(createSlideRequest.getText());
    slide.setOrder(order);
    return slide;
  }

}

