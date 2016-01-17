package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {


    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_edit_item);

        EditText txtproduct = (EditText)findViewById(R.id.editText);
        Intent resultI = getIntent();

        item = resultI.getParcelableExtra("item");


        txtproduct.setText(item.getName().toString());
        txtproduct.setSelection(txtproduct.getText().length());

    }

    public void onSubmit(View v)
    {
     EditText etName = (EditText)findViewById(R.id.editText);
        String editedText = etName.getText().toString();

        if(editedText.length()>0 && CommonUtility.isAlphaNumeric(editedText)) {
            Intent data = new Intent();

            item.setName(etName.getText().toString());
            data.putExtra("code", 200);
            data.putExtra("item", item);
            setResult(RESULT_OK, data);
            finish();
        } else {
            new InvalidItemCustomDialog().show(getSupportFragmentManager(), null);
        }
    }

}
