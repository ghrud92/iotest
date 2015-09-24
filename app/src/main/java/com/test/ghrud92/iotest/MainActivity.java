package com.test.ghrud92.iotest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    MyAsync task;
    int value;
    String name;
    EditText text;
    Button insertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.name);
        insertBtn = (Button) findViewById(R.id.insertbtn);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = text.getText().toString();
                if(name=="")
                    Toast.makeText(MainActivity.this, "No input",Toast.LENGTH_SHORT).show();
                else
                {
                    task = new MyAsync();
                    task.execute(100);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    class MyAsync extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {

        }

        protected Integer doInBackground(Integer... values) {

            Log.v("iotest","1");
            String url = "192.168.0.32/iotest.php?name="+name;
            try {
                Log.v("iotest","2");
                URL ioTest = new URL(url);
                Log.v("iotest","3");
                HttpURLConnection conn = (HttpURLConnection) ioTest.openConnection();
                Log.v("iotest","4");
                conn.connect();
                Log.v("iotest","5");
            }catch(MalformedURLException e){
                Toast.makeText(MainActivity.this,e+" error",Toast.LENGTH_SHORT).show();
            //}catch(UnsupportedEncodingException e){
            //    Toast.makeText(MainActivity.this,e+" error",Toast.LENGTH_SHORT).show();
            }catch(IOException e){
                Toast.makeText(MainActivity.this,e+" error",Toast.LENGTH_SHORT).show();
            }

            return value;
        }

        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(Integer result) {

        }

        protected void onCancelled() {

        }
    }

}