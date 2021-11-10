package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.repository.IMemberRepository;
import com.alkemy.ong.service.abstraction.IListMembersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IListMembersService {

  @Autowired
  IMemberRepository iMemberRepository;

  @Autowired
  ConvertUtils convertUtils;

  @Override
  public ListMemberResponse list() {
    List<Member> members = iMemberRepository.findBySoftDeleteFalse();
    List<DetailsMemberResponse> detailsMemberResponses = convertUtils.toResponseList(members);
    return new ListMemberResponse(detailsMemberResponses);
  }

}
