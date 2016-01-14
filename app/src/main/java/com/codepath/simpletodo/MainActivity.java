package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvitems;
    private final int REQUEST_CODE = 20;

    private String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lvitems = (ListView)findViewById(R.id.lvitems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvitems.setAdapter(itemsAdapter);
        setupListViewListener();
        lvitems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        String product = ((TextView) view).getText().toString();
                        Intent i = new Intent(getApplicationContext(),EditItemActivity.class);
                        edit = product;
                        i.putExtra("product",product);
                        startActivityForResult(i,REQUEST_CODE);
                    }
                }
        );


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //super.onActivityResult(requestCode, resultCode, data);
            String item = data.getExtras().getString("name");
        int REQUEST_CODE = data.getExtras().getInt("code", 0);

        for (int i = 0; i < items.size(); i++) {
            if ((items.get(i).toString()).equals(edit)) {
                items.set(i, item);
            }
        }

        itemsAdapter.notifyDataSetChanged();
    }



    }


    private void setupListViewListener() {
        lvitems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        DeleteCustomDialog deleteCustomDialog = new DeleteCustomDialog();
                        Bundle args = new Bundle();
                        args.putInt("pos", pos);
                        deleteCustomDialog.setArguments(args);
                        deleteCustomDialog.show(getSupportFragmentManager(), null);
                        return true;
                    }
                }
        );

    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        }catch (IOException e){
               items = new ArrayList<>();
        }
    }

    private void writeItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        if(itemText.length()>0 && CommonUtility.isAlphaNumeric(itemText)) {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
            itemsAdapter.notifyDataSetChanged();
        } else {
            new InvalidItemCustomDialog().show(getSupportFragmentManager(), null);
        }
    }

    public void onEditItem(View v) {

        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        etNewItem.setText("");
        writeItems();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//     return true;
//    }

    private void editItems(String newString, String oldString)
    {
        try {
            String content = IOUtils.toString(new FileInputStream("todo.txt"), "UTF8");
            content = content.replaceAll(oldString, newString);
            IOUtils.write(content, new FileOutputStream("todo.txt"), "UTF8");
        } catch (IOException e) {

        }
    }

    public void deleteItem(int pos) {
        items.remove(pos);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }


}
