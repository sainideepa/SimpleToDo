package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
                        // Launching new Activity on selecting single List Item
                        //Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        //startActivity(i);

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
                //editItems(item, edit);
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
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
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
       //items = new ArrayList<>();
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
        //Toast.makeText(MainActivity.this,"Test",Toast.LENGTH_LONG).show();
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        itemsAdapter.notifyDataSetChanged();
    }

    public void onEditItem(View v) {

        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        //itemsAdapter.update(itemText);
        etNewItem.setText("");
        writeItems();
        //item
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

     return true;
    }

    private void editItems(String newString, String oldString)
    {

//        try {
//            String content = FileUtils.readFileToString(new File("todo.txt"), "UTF-8");
//            content = content.replaceAll(newString+"/n", oldString +"/n");
//            File tempFile = new File("todo.txt");
//            FileUtils.writeStringToFile(tempFile, content, "UTF-8");
//        } catch (IOException e) {
//            //Simple exception handling, replace with what's necessary for your use case!
//            throw new RuntimeException("Generating file failed", e);
//        }

//        Path path = Paths.get("test.txt");
//        Charset charset = StandardCharsets.UTF_8;
//
//        String content = new String(Files.readAllBytes(path), charset);
//        content = content.replaceAll("foo", "bar");
//        Files.write(path, content.getBytes(charset));

        try {
            String content = IOUtils.toString(new FileInputStream("todo.txt"), "UTF8");
            content = content.replaceAll(oldString, newString);
            IOUtils.write(content, new FileOutputStream("todo.txt"), "UTF8");
        } catch (IOException e) {

        }


//        try{
//            String verify, putData;
//            File file = new File("file.txt");
//            file.createNewFile();
//            FileWriter fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write("Some text here for a reason");
//            bw.flush();
//            bw.close();
//            FileReader fr = new FileReader(file);
//            BufferedReader br = new BufferedReader(fr);
//
//            while( (verify=br.readLine()) != null ){ //***editted
//                //**deleted**verify = br.readLine();**
//                if(verify != null){ //***edited
//                    putData = verify.replaceAll(oldString, newString);
//                    bw.write(putData);
//                }
//            }
//            br.close();
//
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }

    }
}
