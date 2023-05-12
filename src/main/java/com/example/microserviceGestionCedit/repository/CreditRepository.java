package com.example.microserviceGestionCedit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.microserviceGestionCedit.entity.Credits;

@Repository
public interface CreditRepository extends JpaRepository<Credits, Long>{

}
