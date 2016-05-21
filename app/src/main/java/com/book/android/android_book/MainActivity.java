package com.book.android.android_book;

import android.app.ListActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LivresBDD livreBdd;
    private Button btnTitre, btnIsbn, btnAuteur, btnId;
    private ArrayList<Livre> biblio;
    private ListView listBiblio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        livreBdd = new LivresBDD(this);
        listBiblio = (ListView) findViewById(R.id.list);
        btnTitre = (Button) findViewById(R.id.btn_titre);
        btnIsbn = (Button) findViewById(R.id.btn_isbn);
        btnAuteur = (Button) findViewById(R.id.btn_auteur);
        btnId = (Button) findViewById(R.id.btn_id);
        btnTitre.setOnClickListener(this);
        btnIsbn.setOnClickListener(this);
        btnAuteur.setOnClickListener(this);
        btnId.setOnClickListener(this);

        setTitle("Bibliothèque");

        listBiblio.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int pos, long id) {
                        Object o = listBiblio.getItemAtPosition(pos);
                        Livre livre = (Livre) o;
                        String isbn = livre.getIsbn().toString();
                        showBookOnBrowser(isbn);
                        //Toast.makeText(getApplicationContext(), "Vous avez choisi le livre: " + isbn, Toast.LENGTH_LONG).show();
                    }
                }
        );
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

    public void goToEditBook() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditBookActivity.class);
        startActivity(intent);
    }

    public void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
                goToEditBook();
                return true;
            case R.id.action_add:
                /* DO ADD */
                goToAddBook();
                return true;
            case R.id.action_delete:
                /* DO DELETE */
                return true;
            case R.id.action_mode_close_button:
                /* DO EXIT */
                exitApp();
                return true;
            case R.id.action_reinit:
                /* DO EXIT */
                livreBdd.open();
                reinit();
                livreBdd.close();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnTitre) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByTitre(false);
            ArrayAdapter<Livre> adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnIsbn) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByIsbn(false);
            ArrayAdapter<Livre> adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnAuteur) {
            livreBdd.open();
            biblio = livreBdd.touteLaBdByAuteurs(false);
            ArrayAdapter<Livre> adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
        else if (v == btnId) {
            livreBdd.open();
            biblio = livreBdd.touteLaBD();
            ArrayAdapter<Livre> adapter = new ArrayAdapter<Livre>(MainActivity.this, android.R.layout.simple_list_item_1, biblio);
            listBiblio.setAdapter(adapter);
            livreBdd.close();
        }
    }

    public void reinit(){
        livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(),0,0);
    }
}
