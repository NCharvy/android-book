package com.book.android.android_book;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LivresBDD livreBdd;
    private Button btnTitre, btnIsbn, btnAuteur, btnId;
    private ArrayList<Livre> biblio;
    private ListView listBiblio;
    private ArrayAdapter<Livre> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        livreBdd = new LivresBDD(this);
        listBiblio = (ListView) findViewById(R.id.list);
        listBiblio.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        btnTitre = (Button) findViewById(R.id.btn_titre);
        btnIsbn = (Button) findViewById(R.id.btn_isbn);
        btnAuteur = (Button) findViewById(R.id.btn_auteur);
        btnId = (Button) findViewById(R.id.btn_id);
        btnTitre.setOnClickListener(this);
        btnIsbn.setOnClickListener(this);
        btnAuteur.setOnClickListener(this);
        btnId.setOnClickListener(this);

        setTitle("Bibliothèque");

        // Affiche le nombre de livres enregistrés
        int nbLivres = livreBdd.getLength();
        TextView t = (TextView)findViewById(R.id.txt_nb_livres);
        t.append(String.valueOf(nbLivres));

        //listBiblio.setOnItemClickListener(
        //        new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> adapter, View v, int pos, long id) {
        //            }
        //        }
        //);
    }

    public void showBookOnBrowser(String isbn) {
        String url = "http://books.google.fr/books?isbn=" + isbn;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
    }

    public void goToAddBook() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AddBookActivity.class);
        startActivity(intent);
    }

    public void goToEditBook(int id) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditBookActivity.class);
        intent.putExtra("idLivre", id);
        startActivity(intent);
    }

    public void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void reset(){
        livreBdd.open();
        livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(),0,0);
        livreBdd.close();
    }

    public void deleteLivre(int id) {
        livreBdd.open();
        Toast.makeText(getApplicationContext(), "Le livre " + livreBdd.getLivreWithId(id).getTitre() + " a bien été supprimé", Toast.LENGTH_LONG).show();
        livreBdd.removeLivreWithID(id);
        adapter.notifyDataSetChanged();
        livreBdd.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                /* DO EDIT */
                if(listBiblio.getCheckedItemCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Pour modifier un livre, veuillez le sélectionner", Toast.LENGTH_LONG).show();
                } else {
                    Object o = listBiblio.getItemAtPosition(listBiblio.getCheckedItemPosition());
                    Livre l_to_edit = (Livre) o;
                    int id = l_to_edit.getId();
                    goToEditBook(id);
                }
                return true;
            case R.id.action_add:
                /* DO ADD */
                goToAddBook();
                return true;
            case R.id.action_delete:
                /* DO DELETE */
                if(listBiblio.getCheckedItemCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Vous devez sélectionner un livre si vous souhaitez le supprimer", Toast.LENGTH_LONG).show();
                } else {
                    Object o = listBiblio.getItemAtPosition(listBiblio.getCheckedItemPosition());
                    Livre l_to_delete = (Livre) o;
                    int id = l_to_delete.getId();
                    deleteLivre(id);
                }
                return true;
            case R.id.action_mode_close_button:
                /* DO EXIT */
                exitApp();
                return true;
            case R.id.action_reinit:
                /* DO RESET */
                reset();
                return true;
            case R.id.action_show_browser:
                /* DO SHOW BOOK ON BROWSER */
                if(listBiblio.getCheckedItemCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Vous devez d'abord sélectionner un livre", Toast.LENGTH_LONG).show();
                } else {
                    Object o = listBiblio.getItemAtPosition(listBiblio.getCheckedItemPosition());
                    Livre livre = (Livre) o;
                    String isbn = livre.getIsbn().toString();
                    showBookOnBrowser(isbn);
                    Toast.makeText(getApplicationContext(), "Vous avez choisi le livre: " + isbn, Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnTitre) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByTitre(false);
            adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_activated_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnIsbn) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByIsbn(false);
            adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_activated_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnAuteur) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByAuteurs(false);
            adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_activated_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnId) {
            livreBdd.open();
            biblio = livreBdd.touteLaBD();
            adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_activated_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
    }
}
