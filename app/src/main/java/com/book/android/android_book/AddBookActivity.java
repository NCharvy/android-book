package com.book.android.android_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addButton;
    private LivresBDD livreBdd;
    private Livre livre;
    private EditText etTitre, etIsbn, etAuteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        Intent intent = getIntent();
        String scanContent = intent.getStringExtra("scanContent");
        String scanFormat = intent.getStringExtra("scanFormat");

        livreBdd = new LivresBDD(this);
        addButton = (Button) findViewById(R.id.add);
        etTitre = (EditText) findViewById(R.id.titre);
        etIsbn = (EditText) findViewById(R.id.isbn);
        if (scanContent != null) {
            etIsbn.setText(scanContent);
            Toast.makeText(getApplicationContext(), "Scan effectué : Format = " + scanFormat + " Content = " + scanContent, Toast.LENGTH_LONG).show();
        }
        etAuteur = (EditText) findViewById(R.id.auteur);
        addButton.setOnClickListener(this);

        setTitle("Ajout de livre");
    }

    public void goToMain() {
        Intent intent = new Intent();
        intent.setClass(AddBookActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_only_exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_mode_close_button:
                /* DO RETURN TO MAIN */
                goToMain();
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
        if (v == addButton) {
            livre = new Livre(1, etIsbn.getText().toString(), etTitre.getText().toString(), etAuteur.getText().toString());
            livreBdd.open();
            livreBdd.insertLivre(livre);
            livre = livreBdd.getFirstLivreWithTitre(etTitre.getText().toString());

            Toast.makeText(this, "Ajout du livre " + livre.getTitre().toString(), Toast.LENGTH_LONG).show();
            livreBdd.close();
            goToMain();
        }
    }

    public void reinit(){
        livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(),0,0);
    }
}
