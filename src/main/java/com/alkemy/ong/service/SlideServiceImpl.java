package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.response.ListSlidesResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.repository.ISlideRepository;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.service.abstraction.IListSlidesOrderedService;
import com.alkemy.ong.service.abstraction.IListSlidesService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideServiceImpl implements IDeleteSlideService, IListSlidesService,
    IListSlidesOrderedService {

  @Autowired
  private ISlideRepository slideRepository;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private IOrganizationRepository organizationRepository;

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
  public ListSlidesResponse findSlidesOrdered() {
    Organization organization = getOrganization();
    List<Slide> slides = slideRepository.findByOrganizationIdOrderBySlideOrder(organization.getId());
    return convertUtils.listSlidesToResponse(slides);
  }

  private Organization getOrganization() {
    List<Organization> organizations = organizationRepository.findAll();
    if (organizations.isEmpty()) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
    return organizations.get(0);
  }

}
