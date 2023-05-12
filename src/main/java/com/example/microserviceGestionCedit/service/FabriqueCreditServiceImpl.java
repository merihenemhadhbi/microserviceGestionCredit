package com.example.microserviceGestionCedit.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.entity.Credits.TypeCredit;
import com.example.microserviceGestionCedit.exception.InvalidSwitchCaseException;
import com.example.microserviceGestionCedit.repository.CreditRepository;


@Service
public class FabriqueCreditServiceImpl implements FabriqueCreditService {
	private ApplicationContext applicationContext;
	@Autowired
	private CreditRepository creditRepository;

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public CreditAbstractionService generateCredit(TypeCredit credit) throws InvalidSwitchCaseException {
		CreditAbstractionService creditAbstractionService;
		switch (credit) {
		case IMMOBILIER:
			creditAbstractionService = applicationContext.getBean(CreditImmobilierService.class);
			break;
		case CONSOMMATION:
			creditAbstractionService = applicationContext.getBean(CreditConsommationService.class);
			break;
		default:
			throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
		}
		return creditAbstractionService;
	}

	@Override
	public Credits updateCredit(Credits credits) {
		// TODO Auto-generated method stub
		return creditRepository.save(credits);
	}

	@Override
	public List<Credits> allCredits() {
		// TODO Auto-generated method stub
		return (List<Credits>) creditRepository.findAll();
	}

	public boolean lessThan(Long one, Long two) {
		return one <= two;
	}

	@Override
	public Credits reglerUneMensualite(Long idCredit, BigDecimal mensualite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Credits findUnCredit(Long id) {
		// TODO Auto-generated method stub
		return creditRepository.findById(id).get();
	}

}
