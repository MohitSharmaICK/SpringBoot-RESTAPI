package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long>{

    long calculateGroupWorkload(GroupInfo groupInfo);
}
