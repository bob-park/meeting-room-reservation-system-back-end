package com.m2rs.userservice.controller.controller.company;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.fields.company.CompanyResponseField;
import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.controller.CommonControllerTest;
import com.m2rs.userservice.model.api.company.UpdateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.payload.JsonFieldType;

class CompanyControllerTest extends CommonControllerTest {

    private static final String COMPANY_API = "/company";

    @MockBean
    CompanyRepository companyRepository;

    @BeforeEach
    void setup() {

        Company mockCompany = Company.builder()
            .id(1L)
            .name("test-company")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        Page<Company> mockCompanies = new PageImpl<>(Collections.singletonList(mockCompany));

        when(companyRepository.search(any(), any())).thenReturn(mockCompanies);
        when(companyRepository.findById(any())).thenReturn(Optional.of(mockCompany));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("get company")
    void getCompanyTest(long comId) throws Exception {
        mockMvc.perform(get(COMPANY_API + "/{comId}", comId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseBodyFields(CompanyResponseField.COMPANY_RESPONSE)));
    }


    @WithMockCustomUser(comId = 1, departmentId = 1, email = "manager@manager.com", roleType = RoleType.ROLE_MANAGER)
    @ParameterizedTest
    @ValueSource(longs = 1L)
    @DisplayName("modify company")
    void modifyCompanyTest(long comId) throws Exception {

        UpdateCompanyRequest request = new UpdateCompanyRequest();

        request.setName("mock-company");

        mockMvc.perform(put(COMPANY_API + "/{comId}", comId)
                .headers(getDefaultHeaders())
                .content(toJson(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("comId")
                    .description("회사 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(fieldWithPath("name")
                    .type(JsonFieldType.STRING)
                    .description("회사 이름")
                    .optional()),
                customResponseBodyFields(CompanyResponseField.COMPANY_RESPONSE)));
    }


}
