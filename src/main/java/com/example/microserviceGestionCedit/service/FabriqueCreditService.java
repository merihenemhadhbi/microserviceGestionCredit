package com.example.microserviceGestionCedit.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.exception.InvalidSwitchCaseException;

public interface FabriqueCreditService {
	CreditAbstractionService generateCredit(Credits.TypeCredit credit) throws InvalidSwitchCaseException;

	Credits updateCredit(Credits credits);

	List<Credits> allCredits();

	Credits reglerUneMensualite(Long idCredit, BigDecimal mensualite);

	Credits findUnCredit(Long id);
}
