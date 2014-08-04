package com.glieunou.article;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Requete {

	/*
	 * ici on défini la version de notre BDD, il faudra modifier cela si plus tard nous ajoutons une table ou modifions les attributs d'une table
	 * 
	 * on définit aussi le nom de notre bd, nous allons l'appeler journal
	 * 
	 * on définit enfin table article semblable à celle définit dans le fichier Database.java
	 * 
	 * on reprend aussi les attributs de notre table définit dans le fichier Database
	 * 
	 * on ajoute à cela des variables qui indiquerons la position de chaque attributs dans l'article
	 * 
	 */
	
	private static final int VERSION_BDD=1;

    private static final String NOM_BDD="journal.db";

    private static final String TABLE_ARTICLE="article";
    
    
    private static final String ARTICLE_ID="id";

    private static final int NUM_ARTICLE_ID=0;

    private static final String ARTICLE_TITRE="titre";

    private static final int NUM_ARTICLE_TITRE=1;

    private static final String ARTICLE_CONTENU="contenu";
    
    private static final int NUM_ARTICLE_CONTENU=2;
    
    
    // on définit une instance de SQLiteDatabse ainsi que de notre classe Database précédente
    
    private SQLiteDatabase bdd;

    private Database ma_bdd;
    
    
    // on définit le constructeur ainsi que les méthodes devant servir à ouvrir / fermer la BD
	
    public Requete(Context context){

        // on crée la bdd et ses tables
        ma_bdd=new Database(context, NOM_BDD, null, VERSION_BDD);
    }


    public void open(){

        // ouverture de la BDD en écriture
        bdd=ma_bdd.getWritableDatabase();
    }


    public void close(){

        // on ferme l'accès à la BDD
        bdd.close();
    }


    public SQLiteDatabase getBdd(){

        return bdd;
    }
    
    // une fois tout cela définit, on peut à présent définir les méthodes pour la table article
    
    public long insertArticle(Article art){
    	
    	// création d'un content values qui servira a encapsuler les valeurs à insérer

        ContentValues values=new ContentValues();
        
        values.put(ARTICLE_TITRE,art.getTitre());

        values.put(ARTICLE_CONTENU,art.getContenu());
        
        // RMQ : on ne prend pas l'id car il est auto increment donc se gère par le SGBD directement

        // Avec la syntaxe ci-dessous, l'élément est inséré dans la bd
        
        return bdd.insert(TABLE_ARTICLE,null,values);
        
    }
    
    
    public long updateArticle(Article art, int id){
    	
    	// création d'un content values qui servira a encapsuler les valeurs à insérer

        ContentValues values=new ContentValues(); 
        
        values.put(ARTICLE_TITRE,art.getTitre());

        values.put(ARTICLE_CONTENU,art.getContenu());
        
        return bdd.update(TABLE_ARTICLE,values, ARTICLE_ID+"="+id,null);
        
    }
    
    
    public int removeArticle(int id){
    	
    	return bdd.delete(TABLE_ARTICLE, ARTICLE_ID+"="+id,null);
    }
    
    public int removeAllArticle(){
    	
    	return bdd.delete(TABLE_ARTICLE, null,null);
    }
   
    public Article getArticle(int id){
    	
    	// pour parcourrir une table, on utilise des curseurs, voyez les comme des pointeurs 
    	
    	Cursor c=bdd.query(TABLE_ARTICLE, new String[]{ARTICLE_ID,ARTICLE_TITRE,ARTICLE_CONTENU},ARTICLE_ID+"="+id, null,null,null,null);

        return cursorArticle(c);
    }
    
    public Article cursorArticle(Cursor c){

        if(c.getCount()==0){

            return null;

        }

        else {
        	
        	/* on déplace le curseur a sa position de départ et on recupère les attributs en fonction de leurs type et de leurs positions */
        	
        	c.moveToFirst();

            Article art=new Article();

            art.setId(c.getInt(NUM_ARTICLE_ID));
            
            art.setTitre(c.getString(NUM_ARTICLE_TITRE));
            
            art.setContenu(c.getString(NUM_ARTICLE_CONTENU));

            c.close();

            return art;

        }

    }

    
    // ici, on sélectionne tout les articles de notre bd, le résultat est retourné sous forme de liste
    
    public ArrayList<Article> getAllArticle(){

        Cursor c=bdd.query(TABLE_ARTICLE, new String[]{ARTICLE_ID,ARTICLE_TITRE,ARTICLE_CONTENU}, null, null,null,null,null);

        return cursorArticleAll(c);
    }

    
    public ArrayList<Article> cursorArticleAll(Cursor c){

        ArrayList<Article> list=new ArrayList<Article>();

        if(c.getCount()==0){

            return null;

        }

        else {

            // déplaçons le curseur sur le premier enregistrement

            c.moveToFirst();

            // tant que le curseur pourra se déplacer sur les autres éléments, remplir la liste

            while(c.isAfterLast()==false){

            Article art=new Article();

            art.setId(c.getInt(NUM_ARTICLE_ID));
                 
            art.setTitre(c.getString(NUM_ARTICLE_TITRE));
                 
            art.setContenu(c.getString(NUM_ARTICLE_CONTENU));

            list.add(art);
            
            c.moveToNext();

            }

            c.close();

            return list;

        }

    }

    
    
}
