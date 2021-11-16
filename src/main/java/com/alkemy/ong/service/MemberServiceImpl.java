package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.request.CreateMemberRequest;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.repository.IMemberRepository;

import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IListMembersService, ICreateMemberService {

  @Autowired
  IMemberRepository memberRepository;

  @Autowired
  ConvertUtils convertUtils;

  @Override
  public ListMemberResponse list() {
    List<Member> members = memberRepository.findBySoftDeleteFalse();
    List<DetailsMemberResponse> detailsMemberResponses = convertUtils.toResponseList(members);
    return new ListMemberResponse(detailsMemberResponses);
  }

  @Override
  public Member create(CreateMemberRequest createMemberRequest) {
    Member member = new Member();
    member.setName(createMemberRequest.getName());
    member.setImage(createMemberRequest.getImage());
    memberRepository.save(member);
    return member;
  }

}
