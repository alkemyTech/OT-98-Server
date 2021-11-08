package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.CreateContactResponse;
import java.util.List;

public interface IListContactsService {

  List<CreateContactResponse> contacts();

}
