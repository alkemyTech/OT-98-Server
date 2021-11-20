package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Member;
import org.springframework.data.domain.Page;

public interface IListMembersService {

  Page<Member> list(int page, int size);

}
