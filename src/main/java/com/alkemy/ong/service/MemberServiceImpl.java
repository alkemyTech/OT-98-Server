package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.repository.IMemberRepository;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IListMembersService, IDeleteMembersService {

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

}
