package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.CommentsResponse;
import java.util.List;
import javax.persistence.EntityNotFoundException;

public interface IListCommentsService {
  List<CommentsResponse> list() throws EntityNotFoundException;

}
