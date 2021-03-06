package com.m2rs.meetingroomservice.service.meetingroom;

import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.ModifyMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.SearchMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import java.util.List;

public interface MeetingRoomService {

    List<MeetingRoomResponse> search(SearchMeetingRoomRequest searchRequest);

    MeetingRoomResponse find(Id<MeetingRoom, Long> id);

    MeetingRoomResponse create(CreateMeetingRoomRequest createRequest);

    MeetingRoomResponse modify(Id<MeetingRoom, Long> id, ModifyMeetingRoomRequest modifyRequest);

    MeetingRoomResponse changeActive(Id<MeetingRoom, Long> id, boolean isActive);

}
