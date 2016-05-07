package fr.rizor.android_book;

/**
 * Creation d'un livre tout simple pour un exemple d'utilisation de SQLite sous Android
 * 
 */
public class Livre {

	private int id;
	private String isbn;
	private String titre;
	private String auteurs;

	public Livre(){}

	public Livre(int id,String isbn, String titre,String auteurs){
		this.id=id;
		this.isbn = isbn;
		this.titre = titre;
		this.auteurs=auteurs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteurs() {
		return auteurs;
	}


	public void setAuteurs(String auteurs) {
		this.auteurs = auteurs;
	}

	public String toString(){
		return "ID : "+id+"\nISBN : "+isbn+"\nTitre : "+titre+"\nAuteurs : "+auteurs;
	}
}