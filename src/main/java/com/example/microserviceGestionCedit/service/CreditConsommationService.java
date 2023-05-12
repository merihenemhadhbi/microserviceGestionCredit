package com.example.microserviceGestionCedit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.microserviceGestionCedit.entity.Compte;
import com.example.microserviceGestionCedit.entity.Credits;
import com.example.microserviceGestionCedit.exception.InvalidAccountException;
import com.example.microserviceGestionCedit.exception.InvalidAmountException;
import com.example.microserviceGestionCedit.exception.InvalidMensualiteException;
import com.example.microserviceGestionCedit.repository.CreditRepository;

@Service
public class CreditConsommationService extends CreditAbstractionService {
	
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

	private CreditRepository creditRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public void setCreditRepository(CreditRepository creditRepository) {
		this.creditRepository = creditRepository;
	}

	
	public CreditConsommationService() {
		credit = Credits.TypeCredit.CONSOMMATION;
	}

	@Transactional
	@Override
	public Credits createCredit(Credits credits, Long compteId)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException  {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		Compte compte = restTemplate.getForObject("http://localhost:8080/comptes/" + compteId, Compte.class);

		credits.setCompteCredit(compte);
		credits.setDateCredit(new Date());
		credits.setTypeCredit(credit);

		if (!compte.isEtatCompte()) {
			throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
		} else if (credits.getMontantCredit().longValue() <= 1000 || credits.getMontantCredit().longValue() > 5000) {
			throw new InvalidAmountException("Montant specifié est supérieur à 1000 et ne dépasse pas 5000");
		} else if (credits.getNombreMensualitesCredit() > 36) {
			throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas  3 ans ");
		} else {

			BigDecimal TMM = getTMM();

			if (credits.getNombreMensualitesCredit() >= 12 && credits.getNombreMensualitesCredit() <= 36) {
				BigDecimal interet = TMM.add(new BigDecimal(2)).divide(new BigDecimal(100));
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				Long MontantMax = compte.getSoldeCompte().longValue() * 40 / 100;
				if (MontantMax >= MontantApayerMensuelle) {
					credits.setApprouver(true);
				} else {
					credits.setApprouver(false);
				}
			}
			return creditRepository.save(credits);

		}

	}
	

}