package com.SoftwareTech.PrcScheduleWeb.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class EntityDataFormatterRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(rollbackOn = {Exception.class})
    public int runInsertionQuery(String query) throws DataIntegrityViolationException {
        return entityManager.createNativeQuery(query).executeUpdate();
    }
}
