package com.m2rs.meetingroomservice.repository.meetingroom;

import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>,
    MeetingRoomQueryRepository {

}
