package com.example.EducationalREST.service;

import com.example.EducationalREST.entity.Routine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService <E, T> {



    ResponseEntity<T> addGroup(E entity);


    public Double calculateTotalWorkload(Long groupId);

    boolean groupExists(Long groupId);
}
