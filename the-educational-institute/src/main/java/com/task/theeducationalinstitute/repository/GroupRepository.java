package com.task.theeducationalinstitute.repository;

import com.task.theeducationalinstitute.dto.GroupInfo;
import com.task.theeducationalinstitute.entity.Group;
import com.task.theeducationalinstitute.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group,Long>{

    Optional<Group> findByGroupId(long groupId);
}
