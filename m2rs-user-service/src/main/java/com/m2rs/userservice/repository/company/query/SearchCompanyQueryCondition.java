package com.m2rs.userservice.repository.company.query;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.ToString;

@Getter
@ToString
public class SearchCompanyQueryCondition {

    private final String name;
    private final LocalDateTime createdDateFrom;
    private final LocalDateTime createdDateTo;

    @Builder
    private SearchCompanyQueryCondition(String name, LocalDateTime createdDateFrom,
        LocalDateTime createdDateTo) {
        this.name = name;
        this.createdDateFrom = createdDateFrom;
        this.createdDateTo = createdDateTo;
    }
}
