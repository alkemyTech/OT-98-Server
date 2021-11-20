package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.DetailsNewsCommentsResponse;
import com.alkemy.ong.model.response.ListNewsCommentsResponse;
import java.util.List;
import javax.persistence.EntityNotFoundException;


public interface IListNewsCommentService {

  ListNewsCommentsResponse findByNews(Long id) throws EntityNotFoundException;

}
