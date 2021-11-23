package com.alkemy.ong.integration.member;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.repository.IMemberRepository;

public abstract class AbstractBaseMemberIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected IMemberRepository memberRepository;

  protected Member stubMember() {
    return new Member(1l, "nameExample", "imageExample", "descExample", null, false);
  }

  protected DetailsMemberRequest exampleMemberRequest() {
    DetailsMemberRequest detailsMemberRequest = new DetailsMemberRequest();
    detailsMemberRequest.setName("nameExample");
    detailsMemberRequest.setImage("imageExample");
    detailsMemberRequest.setDescription("descExample");
    detailsMemberRequest.setFacebookUrl("fbExample");
    detailsMemberRequest.setLinkedinUrl("lnExample");
    detailsMemberRequest.setInstagramUrl("igExample");
    return detailsMemberRequest;
  }

  protected List<Member> stubMember(int count){
    List<Member> members = new ArrayList<>();
    
    for (int i = 0; i < count; i++) {
      members.add(new Member(i, "nameExample", "imageExample", "descExample", null, false));
    }
    
    return members;
  }
}
