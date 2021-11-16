package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.CreateMemberRequest;

public interface ICreateMemberService {

  Member create(CreateMemberRequest createMemberRequest);

}
