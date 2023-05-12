package com.example.microserviceGestionCedit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.microserviceGestionCedit.entity.Compte;
import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.exception.InvalidAccountException;
import com.example.microserviceGestionCedit.exception.InvalidAmountException;
import com.example.microserviceGestionCedit.exception.InvalidMensualiteException;
import com.example.microserviceGestionCedit.repository.CreditRepository;


@Service
public class CreditImmobilierService extends CreditAbstractionService {
	private static final Logger LOG = LoggerFactory.getLogger(CreditImmobilierService.class);

public BigDecimal getTMM() {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.bct.gov.tn/bct/siteprod/tableau_statistique_a.jsp?params=PL203105&la=AR")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements tableRows = doc.select(".bct-table-fixed table tr");
		BigDecimal lastValue = new BigDecimal(0);
		for (Element tableRow : tableRows) {
			String rowData = tableRow.text();
			if (rowData.split(" ").length == 7) {
				lastValue = new BigDecimal(rowData.split(" ")[6]);
			}
		}
		return lastValue;

	}

	@Autowired
	private CreditRepository creditRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public void setCreditRepository(CreditRepository creditRepository) {
		this.creditRepository = creditRepository;
	}



	public CreditImmobilierService() {
		credit = Credits.TypeCredit.IMMOBILIER;
	}

	@Transactional
	@Override
	public Credits createCredit(Credits credits, Long compteId)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException {
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		Compte compte = restTemplate.getForObject("http://localhost:8080/comptes/" + compteId, Compte.class);

		if (!compte.isEtatCompte()) {
			throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
		} else if (credits.getMontantCredit().longValue() <= 10000) {
			throw new InvalidAmountException("Montant spécifié est supérieur à 10000");
		} else if (credits.getNombreMensualitesCredit() > 240) {
			throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas 20 ans");
		} else {

			credits.setCompteCredit(compte);
			credits.setDateCredit(new Date());
			credits.setTypeCredit(credit);
			BigDecimal TMM = getTMM();
			if (credits.getNombreMensualitesCredit() <= 84) {
				BigDecimal interet = TMM.add(new BigDecimal(2)).divide(new BigDecimal(100));
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);
				System.out.print(interet);

			} else if (credits.getNombreMensualitesCredit() > 84 && credits.getNombreMensualitesCredit() <= 180) {

				BigDecimal interet = TMM.add(new BigDecimal(2.25)).divide(new BigDecimal(100));
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);

			} else if (credits.getNombreMensualitesCredit() > 180 && credits.getNombreMensualitesCredit() <= 240) {

				BigDecimal interet = TMM.add(new BigDecimal(2.5)).divide(new BigDecimal(100));
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);

			}

		}
		return creditRepository.save(credits);
	}



	
}
