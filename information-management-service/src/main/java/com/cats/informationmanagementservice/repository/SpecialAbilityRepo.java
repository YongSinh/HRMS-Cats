package com.cats.informationmanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialAbility extends JpaRepository<SpecialAbility,Long> {
}
