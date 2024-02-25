package com.task.theeducationalinstitute.service.impl;

import com.task.theeducationalinstitute.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImplementation implements GroupService {

    @Autowired
    GroupRepository groupRepository;
}
