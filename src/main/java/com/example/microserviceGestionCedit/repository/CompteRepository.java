package com.example.microserviceGestionCedit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microserviceGestionCedit.entity.Compte;
import com.example.microserviceGestionCedit.entity.Credits;
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

}
