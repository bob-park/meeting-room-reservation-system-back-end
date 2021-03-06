package com.m2rs.userservice.model.entity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.userservice.model.api.department.ModifyDepartmentRequest;
import com.m2rs.userservice.model.entity.base.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "departments")
public class Department extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_id")
    private Company company;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<User> users = new ArrayList<>();

    @Builder
    private Department(Long id, String name, LocalDateTime cratedDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.createdDate = cratedDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setCompany(Company company) {
        this.company = company;

        company.addDepartment(this);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void modify(ModifyDepartmentRequest modifyRequest) {
        this.name = defaultIfNull(modifyRequest.getName(), this.name);
    }


}
