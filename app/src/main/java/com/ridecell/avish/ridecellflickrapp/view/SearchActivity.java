package com.ridecell.avish.ridecellflickrapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ridecell.avish.ridecellflickrapp.R;

public class SearchActivity extends AppCompatActivity {

    private static final String SEARCH_VALUE = "SEARCH_STRING";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.editText);
    }

    public void onSearch(View view) {
        String searchStr = editText.getText().toString();

        Intent imageListIntent = new Intent(this, ImageListActivity.class);
        imageListIntent.putExtra(SEARCH_VALUE, searchStr);
        startActivity(imageListIntent);
    }
}
