package com.book.android.android_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        setTitle("Ajout d'un livre");
    }

    public void goToAddBook() {
        Intent intent = new Intent();
        intent.setClass(AddBookActivity.this, AddBookActivity.class);
        startActivity(intent);
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
                /* DO ADD */
                goToAddBook();
                return true;
            case R.id.action_delete:
                /* DO DELETE */
                return true;
            case R.id.action_mode_close_button:
                /* DO RETURN TO MAIN */
                goToMain();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
