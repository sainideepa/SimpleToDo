package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_edit_item);

        EditText txtproduct = (EditText)findViewById(R.id.editText);
        Intent resultI = getIntent();

        String product = resultI.getStringExtra("product");
        txtproduct.setText(product);
        txtproduct.setSelection(txtproduct.getText().length());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }
    public void onSubmit(View v)
    {
    //closes this acitivity and return to main if user clicks on back
     EditText etName = (EditText)findViewById(R.id.editText);
        String editedText = etName.getText().toString();

        if(editedText.length()>0 && CommonUtility.isAlphaNumeric(editedText)) {
            Intent data = new Intent();
            data.putExtra("name", etName.getText().toString());
            data.putExtra("code", 200);
            setResult(RESULT_OK, data);
            save(editedText, getIntent().getStringExtra("product"));
            finish();
        } else {
            new InvalidItemCustomDialog().show(getSupportFragmentManager(), null);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(String newString, String oldString)
    {

        try {
            File filesDir = getFilesDir();
            File todoFile = new File(filesDir, "todo.txt");
            String content = FileUtils.readFileToString(todoFile, "UTF-8");
            content = content.replaceAll(oldString, newString);
            //File tempFile = new File("todo.txt");
            FileUtils.writeStringToFile(todoFile, content, "UTF-8");
        } catch (IOException e) {
            //Simple exception handling, replace with what's necessary for your use case!
            throw new RuntimeException("Generating file failed", e);
        }

    }
}
