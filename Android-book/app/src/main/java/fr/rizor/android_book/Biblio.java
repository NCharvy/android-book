package fr.rizor.android_book;
import java.util.ArrayList;


import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Toast;

public class Biblio extends ListActivity {
	/** Called when the activity is first created. */

	private LivresBDD livreBdd;
	private ArrayList<Livre> lesLivres;
	
	
	private final static int MENU_QUITTER=1;
	private final static int MENU_AJOUTER=2;
	private final static int MENU_MODIFIER=3;
	private final static int MENU_SUPPRESSION=4;
	private final static int MENU_REINIT=5;
	private final static int MENU_SCAN=6;
	private final static int MENU_SAVE_BD=7;
	private final static int MENU_LOAD_BD=8;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView entete=(TextView) findViewById(R.id.entete);
		//Creation d'une instance de ma classe LivresBDD
		livreBdd = new LivresBDD(this);
		
		//On ouvre la base de donnees pour y ecrire 
		
		livreBdd.open();
		Livre livre1 = new Livre(0,"123456789", "Programmez sous Android","roszavolgyi garnier");
		Livre livre2 =new Livre(0,"978-2-212-12587-0","Programmation Android","Guignard Chables Robles");
		Livre livre3 =new Livre(0,"978-2-212-12569-6","Programmation GWT2","Jaber");
		Livre livre4 =new Livre(0,"978-1-4302-24119-8","L'art du developpement Android","Murphy");
		Livre livre5 =new Livre(0,"978-2-10-053182-0","GWT","Gerardin");
		livreBdd.insertLivre(livre1);
		livreBdd.insertLivre(livre2);
		livreBdd.insertLivre(livre3);
		livreBdd.insertLivre(livre4);
		livreBdd.insertLivre(livre5);
		lesLivres=livreBdd.touteLaBD();
		String mess =entete.getText().toString();
		entete.setText(entete.getText()+" nombre de livres :"+lesLivres.size());
		setListAdapter(new ArrayAdapter<Livre>(this, android.R.layout.simple_list_item_1, lesLivres));
	
		livreBdd.close();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,MENU_QUITTER,Menu.NONE,"QUITTER");
		menu.add(0,MENU_AJOUTER,Menu.NONE,"AJOUTER");
		menu.add(0,MENU_MODIFIER,Menu.NONE,"MODIFIER");
		menu.add(0,MENU_SUPPRESSION,Menu.NONE,"SUPPRIMER");
		menu.add(0,MENU_SCAN,Menu.NONE,"SCAN");
		menu.add(0,MENU_REINIT,Menu.NONE,"REINIT");
		menu.add(0,MENU_SAVE_BD,Menu.NONE,"Sauver BD");
		menu.add(0,MENU_LOAD_BD,Menu.NONE,"Charger BD");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
	  
		switch(item.getItemId()){
		case MENU_QUITTER:
			finish();	
			break;
		case MENU_REINIT:{
			livreBdd.open();
			reinit();	
			livreBdd.close();
			break;
		}
		case MENU_AJOUTER: {
			
			break;	
		}
		case MENU_MODIFIER:{
			
			break;	
		}
		case MENU_SUPPRESSION:{
			
		}
		case MENU_SCAN:{
			
			break;
		}
		case MENU_LOAD_BD:
			
            break;
		case MENU_SAVE_BD:
			
      	     break;
		}
		return true;	
	}

	
	void reinit(){
		
		livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(),0,0);
			Livre livre1 = new Livre(0,"123456789", "Programmez sous Android","roszavolgyi garnier");
			Livre livre2 =new Livre(0,"978-2-212-12587-0","Programmation Android","Guignard Chables Robles");
			Livre livre3 =new Livre(0,"978-2-212-12569-6","Programmation GWT2","Jaber");
			Livre livre4 =new Livre(0,"978-1-4302-24119-8","L'art du developpement Android","Murphy");
			Livre livre5 =new Livre(0,"978-2-10-053182-0","GWT","Gerardin");
			livreBdd.insertLivre(livre1);
			livreBdd.insertLivre(livre2);
			livreBdd.insertLivre(livre3);
			livreBdd.insertLivre(livre4);
			livreBdd.insertLivre(livre5);
			lesLivres=livreBdd.touteLaBD();	
		}
}
