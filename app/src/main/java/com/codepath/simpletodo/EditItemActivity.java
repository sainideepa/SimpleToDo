package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

        //resultI.putExtra("product", 200);

        //setResult(Activity.RESULT_OK, resultI);
        //finish();
        //String product = getIntent().getStringExtra("product");
        //int code = getIntent().getIntExtra("code",0);
        //TextView txtProduct = (TextView) findViewById(R.id.editText);
        //Intent i = getIntent();
        //txtProduct = i.getExtras().getString("product");
      //  String product = i.getStringExtra("product");
        //txtProduct.setText(txtProduct);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }
    public void onSubmit(View v)
    {     //closes this acitivity and return to main if user clicks on back
     EditText etName = (EditText)findViewById(R.id.editText);
      Intent data = new Intent();
        data.putExtra("name", etName.getText().toString());
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        save(etName.getText().toString(), getIntent().getStringExtra("product"));
        finish();
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

//        Path path = Paths.get("test.txt");
//        Charset charset = StandardCharsets.UTF_8;
//
//        String content = new String(Files.readAllBytes(path), charset);
//        content = content.replaceAll("foo", "bar");
//        Files.write(path, content.getBytes(charset));

//        try {
//            String content = IOUtils.toString(new FileInputStream("todo.txt"), "UTF8");
//            content = content.replaceAll(oldString, newString);
//            IOUtils.write(content, new FileOutputStream("todo.txt"), "UTF8");
//        } catch (IOException e) {
//
//        }


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
