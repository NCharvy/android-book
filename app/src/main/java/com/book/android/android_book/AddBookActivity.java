package com.book.android.android_book;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addButton, testButton;
    private LivresBDD livreBdd;
    private Livre livre;
    private EditText etTitre, etIsbn, etAuteur;
    private String WCKey;
    private URL APIUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        livreBdd = new LivresBDD(this);
        addButton = (Button) findViewById(R.id.add);
        testButton = (Button) findViewById(R.id.test_isbn);
        etTitre = (EditText) findViewById(R.id.titre);
        etIsbn = (EditText) findViewById(R.id.isbn);
        etAuteur = (EditText) findViewById(R.id.auteur);
        testButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        //WCKey = "8kXg8K92DSGLZ9J9uZzvttp3pRUiT3w0MeaOqZJkzq5vNiGf8mBvfc0bL9jSZ4dd0A7EZfkJW5cE6x1m";

        setTitle("Ajout de livre");
    }

    public void goToMain() {
        Intent intent = new Intent();
        intent.setClass(AddBookActivity.this, MainActivity.class);
        startActivity(intent);
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
                return true;
            case R.id.action_add:
                /* DO NOTHING */
                return true;
            case R.id.action_delete:
                /* DO DELETE */
                return true;
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
        }
        else if(v == testButton){
            if(etIsbn.getText() != null){
                parseXML(etIsbn.getText().toString());
            }
        }
    }

    public void reinit(){
        livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(),0,0);
    }

    public void parseXML(String isbn){
        Toast.makeText(this,"" + isbn + "", Toast.LENGTH_LONG).show();
        try {
            System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
            String url = "http://classify.oclc.org/classify2/Classify?isbn=" + isbn + "&summary=true";
            APIUrl = new URL(url);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            xr.setContentHandler(new XMLHandler(AddBookActivity.this));
            InputSource inp = new InputSource(APIUrl.openStream());
            xr.parse(inp);
        }
        catch(IOException ioe){ Log.e("AddBookActivity", "Input error " + ioe.getMessage()); }
        catch(SAXException se){ Log.e("AddBookActivity", "SAX error " + se.getMessage()); }
        catch(ParserConfigurationException pe){ Log.e("AddBookActivity", "Parser error " + pe.getMessage()); }
    }
}
