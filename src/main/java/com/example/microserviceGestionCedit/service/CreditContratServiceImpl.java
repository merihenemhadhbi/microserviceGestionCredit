package com.example.microserviceGestionCedit.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviceGestionCedit.repository.CreditRepository;


@Service
public class CreditContratServiceImpl implements CreditContratService
{

	private CreditRepository creditRepository;


    @Autowired
    public void setCreditsRepository(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

	
	}


