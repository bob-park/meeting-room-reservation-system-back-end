package com.m2rs.userservice.model.entity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.entity.base.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String cellPhone;

    @Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserRoles userRoles;

    @Builder
    private User(Long id, String email, String password, String name,
        String phone, String cellPhone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;

        this.createdDate = LocalDateTime.now();
    }

    public void setDepartment(Department department) {
        department.addUser(this);
        this.department = department;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public void modifyPassword(String encPassword) {
        this.password = encPassword;
    }

    public void modify(ModifyUserRequest request) {
        this.name = defaultIfNull(request.getName(), this.name);
        this.phone = defaultIfNull(request.getPhone(), this.phone);
        this.cellPhone = defaultIfNull(request.getCellPhone(), this.cellPhone);
    }

}
