package com.glieunou.article;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	/* 
	 * déclaration des variables définissant la table article ainsi que ses attributs
	 * 
	 * les variables sont définis comme des constantes
	 * 
	 * si vous avez plusieurs tables, alors vous devez créer des variables pour chacune d'elles ainsi que pour chaque attributs de chacune des tables
	
	*/
	
	private static final String TABLE_ARTICLE="article";
	
	private static final String ARTICLE_ID="id";
	
	private static final String ARTICLE_TITRE="titre";
	
	private static final String ARTICLE_CONTENU="contenu";
	
	
	/*
	 * requetes de création de tables
	 * 
	 * s'il y a plusieurs tables, alors il faudrait en faire une par tables
	 * 
	 * */
	
	private static final String CREATE_A="CREATE TABLE "+TABLE_ARTICLE+" ("+ARTICLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ARTICLE_TITRE+" TEXT NOT NULL, "+ARTICLE_CONTENU+" TEXT NOT NULL);";
	
	
	/* Définition du constructeur en se servant du constructeur de la classe mère, ne rien y changer */
	
	public Database(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/* méthode appelée lors de la création de notre projet, elle crée notre base de données */
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// exécution de la requete
		
		db.execSQL(CREATE_A);
		
	}

	/* méthode appelée lors de la mise à jour de la BD, elle supprimer l'ancienne version et met à jour la nouvelle */
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE "+TABLE_ARTICLE);
		
		 onCreate(db);
	}

}
