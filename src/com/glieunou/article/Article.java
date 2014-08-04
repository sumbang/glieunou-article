package com.glieunou.article;

public class Article {
	
	/* Définition des attributs de la class Article */
	
	private int id;
	
	private String titre;
	
	private String contenu;
	
	/* Définition des constructeurs de la class Article */
	
	public Article(){
		
	 this.id=0; this.titre=""; this.contenu="";
		
	}
	
	public Article(int id,String titre,String contenu){
		
	this.id=id; this.titre=titre; this.contenu=contenu;
		
	}
	
	/* Définition de méthodes qui permettront l'accès aux attributs, ceci dans le but de respecter l'encapsulation si cher à Java  */
	
	public int getId(){ return this.id;  }
	
	public void setId(int id) { this.id=id; }
	
	public String getTitre(){ return this.titre; }
	
	public void setTitre(String titre) { this.titre=titre; }
	
	public String getContenu() { return this.contenu; }
	
	public void setContenu(String contenu) { this.contenu=contenu; }
	
}
