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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    phpInsert task_insert;
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
                    task_insert = new phpInsert();
                    task_insert.execute("http://127.0.0.1/iotest.php?name=" + name);
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

    private class phpInsert extends AsyncTask<String, Integer, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls) {
            StringBuilder resultText = new StringBuilder();
            try{
                //연결 url 설정
                URL url = new URL(urls[0]);
                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //연결 되었으면
                if(conn != null) {
                    Log.v("test","1");
                    conn.setConnectTimeout(10000);
                    Log.v("test", "2");
                    conn.setUseCaches(false);
                    Log.v("test", "3");
                    //연결되었음 코드가 리턴되면
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.v("test","4");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            //웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            //저당된 텍스트 라인을 jsonHtml에 붙여넣음
                            resultText.append(line);
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return resultText.toString();
        }

        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(String str) {
            Log.v("test",str);
            if(str.equals("1")){
                Toast.makeText(getApplicationContext(),"DB Insert Complete.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"DB Insert Failed.",Toast.LENGTH_SHORT).show();
            }
        }

        protected void onCancelled() {

        }
    }

}