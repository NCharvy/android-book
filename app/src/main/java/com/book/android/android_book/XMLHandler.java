package com.book.android.android_book;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class XMLHandler extends DefaultHandler{
    private String local = null;
    private String auteur;
    private String titre;
    private EditText tvAuteur;
    private EditText tvTitre;
    private Context context;
    private View addBookView;

    public XMLHandler(Context cont){
        super();
        context = cont;
        addBookView = ((AddBookActivity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        tvAuteur = (EditText) addBookView.findViewById(R.id.auteur);
        tvTitre = (EditText) addBookView.findViewById(R.id.titre);
    }

    public void startDocument() throws SAXException{
    }

    public void startElement(String namespaceURI, String localName, String rawNames, Attributes attrs) throws SAXException{
        local = localName;
        if(local.equals("work")){
            auteur = attrs.getValue("author");
            tvAuteur.setText(auteur);
            titre = attrs.getValue("title");
            tvTitre.setText(titre);
        }
    }

    public void characters(char[] ch, int start, int length){
        String text = new String(ch, start, length);
    }

    public void endElement(String namespaceURI, String localName, String rawNames, Attributes attrs) throws SAXException{
        local = "";
    }

    public void endDocument() throws SAXException{
    }
}
