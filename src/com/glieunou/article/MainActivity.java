package com.glieunou.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MainActivity extends ActionBarActivity {
	
	// déclaration de notre objet bouton et notre objet listview
	
	private Button ajouter;
	private ListView liste;
	
	private static final int CODE_ACTIVITE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // affectation de nos instances au variables du formulaires
        
        ajouter = (Button)findViewById(R.id.button1);
        liste = (ListView)findViewById(R.id.listView1);
        
        
        // ajout d'un écouteur sur l'objet bouton, il sera en charge de rediriger vers la page d'ajout d'article
        
        ajouter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
                Intent intent=new Intent(MainActivity.this, Crud.class);
				
				startActivityForResult(intent,CODE_ACTIVITE);
				
			}
		});
        
        /***
         * Tout ce qui suit est lié à l'affichage d'article dans la listview 
         * 
         */
        
        
        // Création de la liste d'article qui s'affichera au niveau de la listview
          
           // la listview contiendra des articles, donc un créer un arraylist de type article
        
            ArrayList<Article> listart = new ArrayList<Article>();
            
            final Requete query = new Requete(this);
            
            query.open();
            
            listart=query.getAllArticle();
            
            query.close();
            
            
            if(listart!=null){
            	
            	Iterator tab = listart.iterator();
            	
            	// ArrayList devant servir à remplir la listview
            	
            	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
            	
            	while(tab.hasNext()){
            		
            		Article current = new Article();
            		
            		current =(Article) tab.next();
            		
            		// on déclare le HashMap qui contiendras les informations pour un item
            		
            		HashMap<String, String> map;
            		
            		map = new HashMap<String, String>();
            		
            		map.put("titre", current.getTitre());
            		map.put("key", ""+current.getId());
            		listItem.add(map);
            		
            	}
            	
            	
            	// création d'un adapter qui se chargera de mettre les items présents dans notre liste
            	
            	SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affiche_article,
                        new String[] {"titre", "key"}, new int[] {R.id.titre, R.id.key});
                 
            	// on affecte la l'adapter à la listview
            	
            	liste.setAdapter(mSchedule);
            	
            	
            	// defintions d'un listener sur la listview afin de récuperer l'item sélectionné
            	
                liste.setOnItemClickListener(new OnItemClickListener(){
              	   
              	   @SuppressWarnings("unchecked")
                     public void onItemClick(AdapterView<?> a, View v, int position, long id) {
              		   
                         //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                        
              		   HashMap<String, String> map1 = (HashMap<String, String>) liste.getItemAtPosition(position);
                        
              		   Intent intent=new Intent(MainActivity.this, Crud.class);
              		   
              		   Bundle objetbundle=new Bundle();

	                   objetbundle.putString("key",""+map1.get("key"));
	                  	 
	                   intent.putExtras(objetbundle);
	                  	 
	                   startActivityForResult(intent,CODE_ACTIVITE);
              		   
             	   }
                 }); 
            	
            }
            
            /*
             * fin d'affichage des articles dans la listview
             * 
             * */
            
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
