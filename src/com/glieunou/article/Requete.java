package com.glieunou.article;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Requete {

	/*
	 * ici on d�fini la version de notre BDD, il faudra modifier cela si plus tard nous ajoutons une table ou modifions les attributs d'une table
	 * 
	 * on d�finit aussi le nom de notre bd, nous allons l'appeler journal
	 * 
	 * on d�finit enfin table article semblable � celle d�finit dans le fichier Database.java
	 * 
	 * on reprend aussi les attributs de notre table d�finit dans le fichier Database
	 * 
	 * on ajoute � cela des variables qui indiquerons la position de chaque attributs dans l'article
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
    
    
    // on d�finit une instance de SQLiteDatabse ainsi que de notre classe Database pr�c�dente
    
    private SQLiteDatabase bdd;

    private Database ma_bdd;
    
    
    // on d�finit le constructeur ainsi que les m�thodes devant servir � ouvrir / fermer la BD
	
    public Requete(Context context){

        // on cr�e la bdd et ses tables
        ma_bdd=new Database(context, NOM_BDD, null, VERSION_BDD);
    }


    public void open(){

        // ouverture de la BDD en �criture
        bdd=ma_bdd.getWritableDatabase();
    }


    public void close(){

        // on ferme l'acc�s � la BDD
        bdd.close();
    }


    public SQLiteDatabase getBdd(){

        return bdd;
    }
    
    // une fois tout cela d�finit, on peut � pr�sent d�finir les m�thodes pour la table article
    
    public long insertArticle(Article art){
    	
    	// cr�ation d'un content values qui servira a encapsuler les valeurs � ins�rer

        ContentValues values=new ContentValues();
        
        values.put(ARTICLE_TITRE,art.getTitre());

        values.put(ARTICLE_CONTENU,art.getContenu());
        
        // RMQ : on ne prend pas l'id car il est auto increment donc se g�re par le SGBD directement

        // Avec la syntaxe ci-dessous, l'�l�ment est ins�r� dans la bd
        
        return bdd.insert(TABLE_ARTICLE,null,values);
        
    }
    
    
    public long updateArticle(Article art, int id){
    	
    	// cr�ation d'un content values qui servira a encapsuler les valeurs � ins�rer

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
        	
        	/* on d�place le curseur a sa position de d�part et on recup�re les attributs en fonction de leurs type et de leurs positions */
        	
        	c.moveToFirst();

            Article art=new Article();

            art.setId(c.getInt(NUM_ARTICLE_ID));
            
            art.setTitre(c.getString(NUM_ARTICLE_TITRE));
            
            art.setContenu(c.getString(NUM_ARTICLE_CONTENU));

            c.close();

            return art;

        }

    }

    
    // ici, on s�lectionne tout les articles de notre bd, le r�sultat est retourn� sous forme de liste
    
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

            // d�pla�ons le curseur sur le premier enregistrement

            c.moveToFirst();

            // tant que le curseur pourra se d�placer sur les autres �l�ments, remplir la liste

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
