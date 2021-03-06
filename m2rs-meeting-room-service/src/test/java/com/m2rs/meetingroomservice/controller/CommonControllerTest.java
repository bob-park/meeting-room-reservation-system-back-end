package com.m2rs.meetingroomservice.controller;

import static com.m2rs.core.document.utils.SnippetUtils.commonResponseBodyFields;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.core.document.utils.SnippetUtils;
import com.m2rs.core.security.model.JwtClaim;
import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.core.security.utils.JwtUtils;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class CommonControllerTest {

    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    JwtClaimInfo jwtClaimInfo;

    @BeforeEach
    void setup(WebApplicationContext context,
        RestDocumentationContextProvider restDocumentationExtension) throws Exception {
        this.document =
            document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));

        this.mockMvc =
            MockMvcBuilders.webAppContextSetup(context)
                .apply(
                    documentationConfiguration(restDocumentationExtension)
                        .uris())
                .alwaysDo(document)
                .build();

        createCommonsResponseFields();
    }

    private void createCommonsResponseFields() throws Exception {
        this.mockMvc
            .perform(get("/docs").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(
                MockMvcRestDocumentation.document(
                    "commons-response",
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    commonResponseBodyFields(
                        null,
                        subsectionWithPath("result")
                            .description("??????")
                            .type(JsonFieldType.VARIES),
                        fieldWithPath("success")
                            .description("?????? ??????")
                            .type(JsonFieldType.BOOLEAN),
                        // pagination
                        subsectionWithPath("page.size")
                            .description("????????? ??? ?????? ?????? ???")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.page")
                            .description("?????? ?????????")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.totalCount")
                            .description("?????? ??????")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.firstPage")
                            .description("??? ????????? ??????")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        subsectionWithPath("page.lastPage")
                            .description("????????? ????????? ??????")
                            .type(JsonFieldType.NUMBER)
                            .optional(),
                        // error
                        subsectionWithPath("error.message")
                            .description("?????? ?????????")
                            .type(JsonFieldType.STRING)
                            .optional()
                    )));
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    protected HttpHeaders getDefaultHeaders() {

        HttpHeaders defaultHeaders = SnippetUtils.getDefaultHeaders();

        Map<String, String> defaultHeadersMap = defaultHeaders.toSingleValueMap();

        HttpHeaders headers = new HttpHeaders();

        for (Entry<String, String> entry : defaultHeadersMap.entrySet()) {
            String headerName = entry.getKey();
            String headerValue = entry.getValue();

            headers.add(headerName, headerValue);
        }

        headers.setBearerAuth(generateJwt());

        return headers;

    }

    private String generateJwt() {
        return JwtUtils.newToken(JwtClaim.builder()
                .id(1L)
                .comId(1L)
                .departmentId(1L)
                .name("test-user")
                .roleType(RoleType.ROLE_USER)
                .build().toClaims(),
            jwtClaimInfo);
    }

}
