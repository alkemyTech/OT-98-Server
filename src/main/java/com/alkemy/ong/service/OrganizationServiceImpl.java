package com.alkemy.ong.service;

import com.alkemy.ong.exception.ErrorHandler;
import com.alkemy.ong.exception.OrganizationNotAcceptableException;
import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

  @Autowired
  IOrganizationRepository iOrganizationRepository;

  @Override
  @Transactional
  public ResponseEntity<?> getOrganizationDetails() {
    if(iOrganizationRepository.findAll().size()==0){
      return new ErrorHandler()
          .HandleOrganizationNotAcceptableException(new OrganizationNotAcceptableException("El servidor no es capaz de devolver los datos en ninguno de los formatos aceptados"));
    }else{
      Organization organization = iOrganizationRepository.findAll().get(0);
      return new ResponseEntity<>(OrganizationResponse.builder()
          .name(organization.getName())
          .image(organization.getImage())
          .address(organization.getAddress())
          .phone(organization.getPhone())
          .build(),HttpStatus.OK);
    }

  }
}
