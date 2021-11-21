package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.repository.IMemberRepository;
import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import com.alkemy.ong.service.abstraction.IUpdateMembersService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl
    implements IListMembersService, ICreateMemberService, IDeleteMembersService,
    IUpdateMembersService {


  @Autowired
  IMemberRepository memberRepository;

  @Autowired
  ConvertUtils convertUtils;

  @Override
  public Page<Member> list(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return memberRepository.findBySoftDeleteFalse(pageable);
  }

  @Override
  public Member create(DetailsMemberRequest detailsMemberRequest) {
    Member member = new Member();
    member.setName(detailsMemberRequest.getName());
    member.setImage(detailsMemberRequest.getImage());
    member.setDescription(detailsMemberRequest.getDescription());
    member.setSoftDelete(false);
    member.setFacebookUrl(detailsMemberRequest.getFacebookUrl());
    member.setLinkedinUrl(detailsMemberRequest.getLinkedinUrl());
    member.setInstagramUrl(detailsMemberRequest.getInstagramUrl());

    return memberRepository.save(member);
  }

  @Override
  public void deleteBy(long id) throws EntityNotFoundException {
    Member member = memberRepository.getById(id);
    validateMember(member);
    member.setSoftDelete(true);
    memberRepository.save(member);
  }

  private void validateMember(Member member) {
    if (member == null) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
  }

  @Override
  public Member update(DetailsMemberRequest detailsMemberRequest, Long id) {

    Optional<Member> memberOptional = memberRepository.findById(id);

    if (memberOptional.isEmpty() || memberOptional.get().isSoftDelete()) {
      throw new EntityNotFoundException("Member not found");
    }

    Member member = memberOptional.get();
    member.setName(detailsMemberRequest.getName());
    member.setImage(detailsMemberRequest.getImage());
    member.setDescription(detailsMemberRequest.getDescription());
    member.setSoftDelete(true);
    member.setFacebookUrl(detailsMemberRequest.getFacebookUrl());
    member.setLinkedinUrl(detailsMemberRequest.getLinkedinUrl());
    member.setInstagramUrl(detailsMemberRequest.getInstagramUrl());
    memberRepository.save(member);
    return member;
  }
}
