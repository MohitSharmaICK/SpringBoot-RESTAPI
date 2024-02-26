package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.dto.GroupResponse;
import org.springframework.stereotype.Service;

public interface GroupService {

    GroupResponse createGroup(GroupInfo groupInfo);

    public double calculateGroupTotalWorkload(long groupId);

}
