package com.example.microserviceGestionCedit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@AllArgsConstructor
public class Credits  implements Serializable {
	public enum TypeCredit {
		IMMOBILIER, CONSOMMATION;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCredit;
	private Double montantCredit;
	@Temporal(TemporalType.DATE)
	private Date dateCredit;
	private Double mensualite;
	@Enumerated(EnumType.STRING)
	private TypeCredit typeCredit;
	private Long nombreMensualitesCredit;
	private Long montantReste;
	private Long montantReglee;
	private BigDecimal interet;	
	private Boolean approuver = false;

	@ManyToOne
	@JoinColumn(name = "numeroCompte")
	@JsonIgnore
	private Compte compteCredit;

	public Credits(Double montantCredit, Date dateCredit, Double mensualite, TypeCredit typeCredit,
            Long nombreMensualitesCredit, Long montantReste, Long montantReglee, BigDecimal interet, Boolean approuver) {
        this.montantCredit = montantCredit;
        this.dateCredit = dateCredit;
        this.mensualite = mensualite;
        this.typeCredit = typeCredit;
        this.nombreMensualitesCredit = nombreMensualitesCredit;
        this.montantReste = montantReste;
        this.montantReglee = montantReglee;
        this.interet = interet;
        this.approuver=approuver;
}

}
