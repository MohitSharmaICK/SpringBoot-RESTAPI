package com.example.EducationalREST.service;

import com.example.EducationalREST.Exceptions.GroupNotValidException;
import com.example.EducationalREST.entity.Group;
import com.example.EducationalREST.entity.Routine;
import com.example.EducationalREST.repository.GroupRepository;
import com.example.EducationalREST.repository.RoutineRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class GroupServiceImplementation implements GroupService<Group, ResponseEntity>{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoutineRepository routineRepository;

    private List<Routine> routines;
    @Override
    public ResponseEntity addGroup(Group group) {
        validateGroup(group);

        groupRepository.save(group);
        return ResponseEntity.ok().build();
    }

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Double calculateTotalWorkload(Long groupId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("calculate_total_workload");
        query.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);  // Register the input parameter at index 1
        query.setParameter(1, groupId); // Set the value for the input parameter

        // Execute the query
        query.execute();


        // Retrieve the result
        BigDecimal result = (BigDecimal) query.getSingleResult();
        return result.doubleValue(); // Convert BigDecimal to Double
    }

    @Override
    public boolean groupExists(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.isPresent();
    }



    //Validating the information of group entity to ensure none of them are left blank/empty.
   public void validateGroup(Group group)
   {
       if(StringUtils.isBlank(group.getGradeLevel()) || StringUtils.isBlank(group.getSpecialization()))
       {
           throw new GroupNotValidException("Group information is incomplete");
       }

   }

}
