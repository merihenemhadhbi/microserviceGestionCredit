package com.example.microserviceGestionCedit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviceGestionCedit.entity.Compte;
import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.exception.InvalidSwitchCaseException;
import com.example.microserviceGestionCedit.repository.CompteRepository;
import com.example.microserviceGestionCedit.service.CreditAbstractionService;
import com.example.microserviceGestionCedit.service.CreditConsommationService;
import com.example.microserviceGestionCedit.service.CreditImmobilierService;
import com.example.microserviceGestionCedit.service.FabriqueCreditService;


@RestController
@RequestMapping("/credits")
public class CreditController {

	private FabriqueCreditService fabriqueCreditService;
	@Autowired
	private CompteRepository compteRepository;

	@GetMapping("/retrieve-all-credits")
	public ResponseEntity retrieveAllCredits() {
		List<Credits> listCredit = new ArrayList<>();
		try {
			listCredit = fabriqueCreditService.allCredits();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(listCredit);

	}

	@PostMapping("/add-credit")
	public ResponseEntity createCredit(@RequestBody Credits c, @RequestParam Long idCompte) {
		Credits postcredit = null;
		CreditAbstractionService creditAbstractionService = null;
		try {
			creditAbstractionService = fabriqueCreditService.generateCredit(c.getTypeCredit());
			Compte compte = compteRepository.findById(idCompte).get();
			switch (c.getTypeCredit()) {
			case IMMOBILIER:
				CreditImmobilierService creditImmobilierService = (CreditImmobilierService) creditAbstractionService;
				postcredit = creditImmobilierService.createCredit(c, idCompte);
				break;
			case CONSOMMATION:
				CreditConsommationService creditConsommationService = (CreditConsommationService) creditAbstractionService;
				postcredit = creditConsommationService.createCredit(c, idCompte);
				break;
			default:
				throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(postcredit);
	}

	@GetMapping("/get-credit/{credit-id}")
	public ResponseEntity retrieveCredit(@PathVariable("credit-id") Long id) {
		Credits credit = null;
		try {
			credit = fabriqueCreditService.findUnCredit(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(credit);
	}




}
