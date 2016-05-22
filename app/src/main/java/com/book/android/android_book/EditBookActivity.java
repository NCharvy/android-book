package com.book.android.android_book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBookActivity extends AppCompatActivity implements View.OnClickListener {
    private Button editButton;
    private LivresBDD livreBdd;
    private Livre livre;
    private EditText etTitre, etIsbn, etAuteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        Intent intent = getIntent();
        int idLivreToEdit = intent.getIntExtra("idLivre", 0);
        if (idLivreToEdit == 0) {
            Toast.makeText(getApplicationContext(), "L'identifiant du livre à modifier et invalide ! ", Toast.LENGTH_LONG).show();
            goToMain();
        }

        livreBdd = new LivresBDD(this);
        livreBdd.open();
        livre = livreBdd.getLivreWithId(idLivreToEdit);
        livreBdd.close();
        editButton = (Button) findViewById(R.id.edit);
        etTitre = (EditText) findViewById(R.id.titre);
        etTitre.setText(livre.getTitre());
        etIsbn = (EditText) findViewById(R.id.isbn);
        etIsbn.setText(livre.getIsbn());
        etAuteur = (EditText) findViewById(R.id.auteur);
        etAuteur.setText(livre.getAuteurs());
        editButton.setOnClickListener(this);

        setTitle("Modifier un livre");
    }

    public void goToMain() {
        Intent intent = new Intent();
        intent.setClass(EditBookActivity.this, MainActivity.class);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == editButton) {
            Livre editLivre = new Livre(1, etIsbn.getText().toString(), etTitre.getText().toString(), etAuteur.getText().toString());
            livreBdd.open();
            livreBdd.updateLivre(livre.getId(), editLivre);

            Toast.makeText(this, "Le livre " + livre.getTitre().toString() + " ayant pour identifiant " + livre.getId() + " a bien été modifié", Toast.LENGTH_LONG).show();
            livreBdd.close();
            goToMain();
        }
    }
}
