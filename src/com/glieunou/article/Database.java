package com.glieunou.article;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	/* 
	 * d�claration des variables d�finissant la table article ainsi que ses attributs
	 * 
	 * les variables sont d�finis comme des constantes
	 * 
	 * si vous avez plusieurs tables, alors vous devez cr�er des variables pour chacune d'elles ainsi que pour chaque attributs de chacune des tables
	
	*/
	
	private static final String TABLE_ARTICLE="article";
	
	private static final String ARTICLE_ID="id";
	
	private static final String ARTICLE_TITRE="titre";
	
	private static final String ARTICLE_CONTENU="contenu";
	
	
	/*
	 * requetes de cr�ation de tables
	 * 
	 * s'il y a plusieurs tables, alors il faudrait en faire une par tables
	 * 
	 * */
	
	private static final String CREATE_A="CREATE TABLE "+TABLE_ARTICLE+" ("+ARTICLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ARTICLE_TITRE+" TEXT NOT NULL, "+ARTICLE_CONTENU+" TEXT NOT NULL);";
	
	
	/* D�finition du constructeur en se servant du constructeur de la classe m�re, ne rien y changer */
	
	public Database(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/* m�thode appel�e lors de la cr�ation de notre projet, elle cr�e notre base de donn�es */
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// ex�cution de la requete
		
		db.execSQL(CREATE_A);
		
	}

	/* m�thode appel�e lors de la mise � jour de la BD, elle supprimer l'ancienne version et met � jour la nouvelle */
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE "+TABLE_ARTICLE);
		
		 onCreate(db);
	}

}
