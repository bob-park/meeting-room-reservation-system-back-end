package com.m2rs.meetingroomservice.model.entity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.meetingroomservice.model.entity.base.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "meeting_rooms")
public class MeetingRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long comId;
    private String name;
    private Boolean isActive;

    @Builder
    private MeetingRoom(Long id, Long comId, String name, Boolean isActive) {
        this.id = id;
        this.comId = comId;
        this.name = name;
        this.isActive = defaultIfNull(isActive, true);
    }
}
