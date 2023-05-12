package com.example.microserviceGestionCedit.entity;

import java.math.BigDecimal;
import java.util.Date;


public class Compte {
	public enum TypeCompte {
		EPARGNE, COURANT;
	}

	public enum CategorieCompte {
		SILVER, GOLD, PLATINUM;
	}

	

	private Long numeroCompte;

	private boolean etatCompte;

	private Date dateCreation;

	private BigDecimal soldeCompte;

	private String motDePasse;

	private TypeCompte typeCompte;


	private CategorieCompte categorieCompte;
	
	/**
	 * Constructeur de l'entité Compte
	 * 
	 * @param numeroCompte
	 * @param etatCompte
	 * @param dateCreation
	 * @param soldeCompte
	 * @param typeCompte
	 * @param motDePasse
	 */

	public Compte(Long numeroCompte, boolean etatCompte, Date dateCreation, BigDecimal soldeCompte,
			TypeCompte typeCompte, String motDePasse) {
		this.numeroCompte = numeroCompte;
		this.etatCompte = etatCompte;
		this.dateCreation = dateCreation;
		this.soldeCompte = soldeCompte;
		this.typeCompte = typeCompte;
		this.motDePasse = motDePasse;
	}

	public Compte() {
	}

	public Long getNumeroCompte() {
		return numeroCompte;
	}

	public CategorieCompte getCategorieCompte() {
		return categorieCompte;
	}

	public void setCategorieCompte(CategorieCompte categorieCompte) {
		this.categorieCompte = categorieCompte;
	}

	public void setNumeroCompte(Long numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public boolean isEtatCompte() {
		return etatCompte;
	}

	public void setEtatCompte(boolean etatCompte) {
		this.etatCompte = etatCompte;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public BigDecimal getSoldeCompte() {
		return soldeCompte;
	}

	public void setSoldeCompte( BigDecimal d) {
		this.soldeCompte = d;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public TypeCompte getTypeCompte() {
		return typeCompte;
	}

	public void setTypeCompte(TypeCompte typeCompte) {
		this.typeCompte = typeCompte;
	}

	/**
	 * @return Chaines de caracteres des infos sur Compte
	 */

	@Override
	public String toString() {
		return "Compte{" + "numeroCompte=" + numeroCompte + ", etatCompte=" + etatCompte + ", dateCreation="
				+ dateCreation + ", soldeCompte=" + soldeCompte + ", motDePasse='" + motDePasse + '\'' + ", typeCompte="
				+ typeCompte + '}';
	}

	public void setUtilisateur(String string) {
		// TODO Auto-generated method stub
		
	}
}
