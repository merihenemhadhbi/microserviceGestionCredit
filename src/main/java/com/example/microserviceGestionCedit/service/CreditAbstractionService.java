package com.example.microserviceGestionCedit.service;

import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.exception.InvalidAccountException;
import com.example.microserviceGestionCedit.exception.InvalidAmountException;
import com.example.microserviceGestionCedit.exception.InvalidMensualiteException;

public abstract class CreditAbstractionService {

	Credits.TypeCredit credit;

	abstract public Credits createCredit(Credits credits, Long compteId)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException;



}
