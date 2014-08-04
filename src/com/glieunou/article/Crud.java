package com.glieunou.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Crud extends ActionBarActivity {
	
	  private EditText titre,contenu;
	  
	  private Button bt1,bt2,bt3; private static final int CODE_ACTIVITE=2;
	  
	  private Requete req=new Requete(this);
	  
	  private int cle =0;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.crud_article);
	        
	        titre = (EditText)findViewById(R.id.editText1);
	        contenu = (EditText)findViewById(R.id.editText2);
	        
	        bt1 = (Button)findViewById(R.id.button1);
	        bt2 = (Button)findViewById(R.id.button2);
	        bt3 = (Button)findViewById(R.id.button3);
	        
	        // récupération de l'intent clé envoyé !!
	        
	        Bundle objetbunble  = this.getIntent().getExtras();
	        
	        if (objetbunble != null && objetbunble.containsKey("key")) {
	        	
                bt2.setVisibility(View.VISIBLE); bt3.setVisibility(View.VISIBLE);
	        	
	        	bt1.setVisibility(View.GONE);
	        	
	        	cle = Integer.parseInt(this.getIntent().getStringExtra("key"));
	        	
	        	Article art = new Article(); 
	        	
	        	req.open(); art=req.getArticle(cle); req.close();
	        	
	        	titre.setText(art.getTitre().toString());
	        	
	        	contenu.setText(art.getContenu().toString());
	        	
	               
	        }else {
	        	
	        	bt2.setVisibility(View.GONE); bt3.setVisibility(View.GONE);
	        	
	        	bt1.setVisibility(View.VISIBLE);
	        	
	        	titre.setText(""); contenu.setText("");
	        
	        }
	        
	        
	        bt1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Article art = new Article();
					
					art.setTitre(titre.getText().toString());
					
					art.setContenu(titre.getText().toString());
					
					req.open(); req.insertArticle(art); req.close();
					
					Intent intent=new Intent(Crud.this, MainActivity.class);
					
					startActivityForResult(intent,CODE_ACTIVITE);
					
				}
			});
	        
	        
	        bt2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
                    Article art = new Article();
					
					art.setTitre(titre.getText().toString());
					
					art.setContenu(titre.getText().toString());
					
					req.open(); req.updateArticle(art, cle); req.close();
				
					Intent intent=new Intent(Crud.this, MainActivity.class);
					
					startActivityForResult(intent,CODE_ACTIVITE);
					
					
				}
			});
	        
	        
	        bt3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					req.open();
					
					req.removeArticle(cle);
					
					req.close();
					
                    Intent intent=new Intent(Crud.this, MainActivity.class);
					
					startActivityForResult(intent,CODE_ACTIVITE);
					
				}
			});
	        
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