package com.gladunalexander.backend.persistence.repositories;

import com.gladunalexander.backend.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Gladun
 * Repository for role persisting
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
