package org.example.ch01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //빈들을 찾아 어플리케이션 컨텍스트를 사용하여 테스트 진행 가능
@AutoConfigureMockMvc // mockMVC를 자동으로 구성해주는 클래스 / 보통 컨트롤러 테스트 할 때 많이 사용함
class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context; // todo 찾아보기

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll(); // 테스트가 끝나면 지울 수 있도록 함
    }

    @DisplayName("getAllMembers : 멤버 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {

        // given
        final String url = "/test";
        Member saveMember = memberRepository.save(new Member(1L, "홍길동"));

        // when
        // perform -> 메서드 요청을 보내는 역할
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk()) // 조건을 충족해야지 성공하도록 하는 메서드
                // jsonPath members 호출값 -> 응답값
                .andExpect(jsonPath("$[0].id").value(saveMember.getId()))
                .andExpect(jsonPath("$[0].name").value(saveMember.getName()));
    }
}