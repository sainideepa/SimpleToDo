package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.IOUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ArrayList<Item> items;
    CustomAdapter itemsAdapter;
    ListView lvitems;
    private final int REQUEST_CODE = 20;


    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHandler(this);

        setContentView(R.layout.activity_main);

        lvitems = (ListView)findViewById(R.id.lvitems);
        readItems();
        itemsAdapter = new CustomAdapter(this, items);
        lvitems.setAdapter(itemsAdapter);
        setupListViewListener();
        lvitems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Item item = (Item)parent.getItemAtPosition(position);
                        for (Item element:items) {
                            if(element.getName().equals(item.getName())) {
                                item.setId(element.getId());
                            }
                        }

                        Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                        i.putExtra("item", (Parcelable) item);
                        startActivityForResult(i, REQUEST_CODE);
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
            Item newItem = data.getExtras().getParcelable("item");

            for (int i = 0; i <= items.size(); i++) {
            if ((items.get(i).getId()== newItem.getId())) {
                items.set(i, newItem);
                db.updateItem(newItem);
                break;
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
        items = new ArrayList<Item>();

        File database=getApplicationContext().getDatabasePath("ItemDatabase.db");
        if (!database.exists()) {
            //TODO
        } else {
            items  = (ArrayList)db.getAllItems();
        }




    }

    private void writeItems(Item item) {
        DatabaseHandler db = new DatabaseHandler(this);
        long pk = db.addItem(item);
        item.setId(pk);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        if(itemText.length()>0 && CommonUtility.isAlphaNumeric(itemText)) {
            Item item = new Item(itemText);
            writeItems(item);
            itemsAdapter.add(item);
            etNewItem.setText("");
            itemsAdapter.notifyDataSetChanged();
        } else {
            new InvalidItemCustomDialog().show(getSupportFragmentManager(), null);
        }
    }

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
        db.deleteItem(items.get(pos));
        items.remove(pos);
        itemsAdapter.notifyDataSetChanged();

    }

}
