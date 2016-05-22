package com.book.android.android_book;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.util.ArrayList;

public class LivresBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "bibliotheque.db";

	private static final String TABLE_LIVRES = "table_livres";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_ISBN = "ISBN";
	private static final int NUM_COL_ISBN = 1;
	private static final String COL_TITRE = "Titre";
	private static final int NUM_COL_TITRE = 2;
	private static final String COL_AUTEURS = "Auteurs";
	private static final int NUM_COL_AUTEURS = 3;


	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public LivresBDD(Context context){
		//On créer la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public MaBaseSQLite getMaBase(){
		return maBaseSQLite;
	}

	public long insertLivre(Livre livre){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		if(getFirstLivreWithIsbn(livre.getIsbn())==null){
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_ISBN, livre.getIsbn());
		values.put(COL_TITRE, livre.getTitre());
		values.put(COL_AUTEURS, livre.getAuteurs());
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_LIVRES, null, values);
	}
		return 0;
	}

	public int updateLivre(int id, Livre livre){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_ISBN, livre.getIsbn());
		values.put(COL_TITRE, livre.getTitre());
		values.put(COL_AUTEURS, livre.getAuteurs());
		return bdd.update(TABLE_LIVRES, values, COL_ID + " = " +id, null);
	}

	public int removeLivreWithID(int id){
		//Supprimer un livre de la BDD grâce à l'ID
		return bdd.delete(TABLE_LIVRES, COL_ID + " = " +id, null);
	}

	public Livre getLivreWithId(int id){
		// Récupère dans un Cursor les valeurs correspondant a un livre contenu dans la BDD (ici on selectionne le livre grace a son id)
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
		return cursorToLivre(c);
	}

	public Livre getFirstLivreWithTitre(String titre){
		//Recupere dans un Cursor les valeur correspondant a un livre contenu dans la BDD (ici on selectionne le livre grace a son titre)
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
		return cursorToLivre(c);
	}

	public Livre getFirstLivreWithIsbn(String isbn){
		//Recuperere dans un Cursor les valeur correspondant a un livre contenu dans la BDD (ici on selectionne le livre grace a son isbn)
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, COL_ISBN + " LIKE \"" + isbn +"\"", null, null, null, null);
		return cursorToLivre(c);
	}

	public Livre getFirsLivreWithAuteur(String auteur){
		//Recuperere dans un Cursor les valeur correspondant a un livre contenu dans la BDD (ici on selectionne le livre grace a son auteur)
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, COL_AUTEURS + " LIKE \"" + auteur +"\"", null, null, null, null);
		return cursorToLivre(c);
	}

	public int getLength(){
		// Récupère le nombre de livres enregistrés en base de données
		String countQuery = "SELECT  * FROM " + TABLE_LIVRES;
		SQLiteDatabase db = maBaseSQLite.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int nbLivres = cursor.getCount();
		cursor.close();
		return nbLivres;
	}



	//Cette methode permet de convertir un cursor en un livre
	private Livre cursorToLivre(Cursor c){
		//si aucun element n'a ete retourne dans la requete, on renvoie null
		if (c.getCount() == 0)
			return null;

		//Sinon on se place sur le premier element
		c.moveToFirst();
		//On cree un livre
		Livre livre = new Livre(c.getInt(NUM_COL_ID),c.getString(NUM_COL_ISBN),c.getString(NUM_COL_TITRE),c.getString(NUM_COL_AUTEURS));
		c.close();

		//On retourne le livre
		return livre;
	}

	private ArrayList<Livre> cursorToLivres(Cursor c){
		//si aucun element n'a ete retourne dans la requete, on renvoie une ArrayList vide
		if (c.getCount() == 0)
			return new ArrayList<Livre>(0);
		//Sinon on se place sur le premier element
		c.moveToFirst();
		ArrayList<Livre> biblio=new ArrayList<Livre>(c.getCount());
		do{
			//On cree un livre
			Livre livre = new Livre(c.getInt(NUM_COL_ID),c.getString(NUM_COL_ISBN),c.getString(NUM_COL_TITRE),c.getString(NUM_COL_AUTEURS));
			biblio.add(livre);
		}
		while(c.moveToNext());
		//On ferme le cursor
		c.close();

		//On retourne la biblio
		return biblio;
	}






	public	void Affiche(Context cont){
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, null, null, null, null,null);	
		ArrayList<Livre> biblio=cursorToLivres(c);
		Toast.makeText(cont, "taille de la table :"+biblio.size(), Toast.LENGTH_LONG).show();
		for(int i=0;i<biblio.size();i++){
			Toast.makeText(cont, biblio.get(i).toString(), Toast.LENGTH_LONG).show();
		}
	}

	public ArrayList<Livre> touteLaBD(){
		Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, null, null, null, null,null);
		return cursorToLivres(c);
	}

	public ArrayList<Livre> touteLaBdByTitre(boolean croissant){
		Cursor c;
		String orderby=" ASC";
		if(!croissant) orderby=" DESC";
		c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, null, null, null, null,COL_TITRE + orderby);
		return cursorToLivres(c);
	}

	public ArrayList<Livre> touteLaBdByIsbn(boolean croissant){	
		Cursor c;
		String orderby=" ASC";
		if(!croissant) orderby=" DESC";
		c= bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, null, null, null, null,COL_ISBN + orderby);
		return cursorToLivres(c);
	}

	public ArrayList<Livre> touteLaBdByAuteurs(boolean croissant){
		Cursor c;
		String orderby=" ASC";
		if(!croissant) orderby=" DESC";
		c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE,COL_AUTEURS}, null, null, null, null,COL_AUTEURS + orderby);
		return cursorToLivres(c);
	}



}