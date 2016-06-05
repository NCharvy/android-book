package com.book.android.android_book;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Handler;

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String scanContent = intent.getStringExtra("scanContent");
        String scanFormat = intent.getStringExtra("scanFormat");

        livreBdd = new LivresBDD(this);
        addButton = (Button) findViewById(R.id.add);
        testButton = (Button) findViewById(R.id.test_isbn);
        etTitre = (EditText) findViewById(R.id.titre);
        etIsbn = (EditText) findViewById(R.id.isbn);
        if (scanContent != null) {
            etIsbn.setText(scanContent);
            Toast.makeText(getApplicationContext(), "Scan effectué : Format = " + scanFormat + " Content = " + scanContent, Toast.LENGTH_LONG).show();
        }
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
            if(etIsbn.getText().toString().equals("") || etTitre.getText().toString().equals("") || etAuteur.getText().toString().equals("")){
                Toast.makeText(this, "Il manque des informations pour l'ajout du livre !", Toast.LENGTH_LONG).show();
            }
            else {
                livre = new Livre(1, etIsbn.getText().toString(), etTitre.getText().toString(), etAuteur.getText().toString());
                livreBdd.open();
                livreBdd.insertLivre(livre);
                livre = livreBdd.getFirstLivreWithTitre(etTitre.getText().toString());

                Toast.makeText(this, "Ajout du livre " + livre.getTitre().toString(), Toast.LENGTH_LONG).show();
                livreBdd.close();
                goToMain();
            }
        }
        else if(v == testButton){
            if(etIsbn.getText().toString().equals("")){
                Toast.makeText(this, "Veuillez entrer un ISBN !", Toast.LENGTH_LONG).show();
            }
            else{
                parseXML(etIsbn.getText().toString());
            }
        }
    }

    public void reinit(){
        livreBdd.getMaBase().onUpgrade(livreBdd.getBDD(), 0, 0);
    }

    public void parseXML(String isbn) {
        final String num = isbn;
        AddBookActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
                    String url = "http://classify.oclc.org/classify2/Classify?isbn=" + num + "&summary=true";
                    APIUrl = new URL(url);
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    SAXParser sp = spf.newSAXParser();
                    XMLReader xr = sp.getXMLReader();

                    xr.setContentHandler(new XMLHandler(AddBookActivity.this));
                    InputSource inp = new InputSource(APIUrl.openStream());
                    xr.parse(inp);
                    if(etTitre.getText().toString().equals("") && etAuteur.getText().toString().equals("")){
                        Toast.makeText(AddBookActivity.this, "Le livre n'a pas été trouvé !", Toast.LENGTH_LONG).show();
                    }
                    else if(etTitre.getText().toString().equals("") || etAuteur.getText().toString().equals("")){
                        Toast.makeText(AddBookActivity.this, "Il manque des informations au livre !", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException ioe) {
                    Log.e("AddBookActivity", "Input error " + ioe.getMessage());
                } catch (SAXException se) {
                    Log.e("AddBookActivity", "SAX error " + se.getMessage());
                } catch (ParserConfigurationException pe) {
                    Log.e("AddBookActivity", "Parser error " + pe.getMessage());
                }
            }
        });
    }
}
