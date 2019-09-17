package com.example.wordskills;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/getLangs";
    String TEST_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    HttpsURLConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.translate_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new TranslateTask(findViewById(R.id.result_text_view)).execute(
                                ((TextView)findViewById(R.id.to_translate_text_view)).getText().toString()
                        );
                    }
                }
        );
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                connection = (HttpsURLConnection) new URL(TEST_URL).openConnection();
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("key", "trnsl.1.1.20190917T105042Z.e8158215fa365724.d61807cb1da4ded8d51c218d599b4fba49698073");
                                connection.setRequestProperty("text", "данные");
                                connection.setRequestProperty("lang", "ru-en");
                                connection.setRequestProperty("format", "plain");
                                connection.setRequestProperty("options", "1");
                                connection.connect();
                                Log.i("READ DATA", String.valueOf(connection.getResponseCode()));
                                InputStream inputStream = connection.getInputStream();
            byte[] result = new byte[1024];
            inputStream.read(
                    result
            );
            Log.i("READ DATA", String.valueOf(result.length));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();



    }

    class TranslateTask extends AsyncTask<String, Void, String> {
        private View view;

        TranslateTask(View view){
            this.view = view;
        }

        @Override
        protected String doInBackground(String... strings) {
            String msg = "";
                            try {
                                connection = (HttpsURLConnection) new URL(TEST_URL).openConnection();
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("key", "trnsl.1.1.20190917T105042Z.e8158215fa365724.d61807cb1da4ded8d51c218d599b4fba49698073");
                                connection.setRequestProperty("text", "данные");

                                connection.setRequestProperty("Content-Type","application/json");
                                connection.setRequestProperty("lang", "ru-en");
                                connection.setRequestProperty("format", "plain");
                                connection.setRequestProperty("options", "1");
                                Log.i("READ DATA", String.valueOf(connection.getResponseCode()));

                                connection.connect();
                                Log.i("READ DATA", String.valueOf(connection.getResponseCode()));
                                InputStream inputStream = connection.getInputStream();
                                byte[] result = new byte[1024];
                                inputStream.read(
                                        result
                                );
                                for (int i = 0; i < result.length; i++){
                                    msg += String.valueOf((char)result[i]);
                                }
                                Log.i("READ DATA", String.valueOf(result.length));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

            /*try {
                connection = (HttpsURLConnection) new URL(TEST_URL).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("key","trnsl.1.1.20190917T105042Z.e8158215fa365724.d61807cb1da4ded8d51c218d599b4fba49698073");
                connection.setRequestProperty("text",strings[0]);
                connection.setRequestProperty("lang","ru-en");
                connection.setRequestProperty("format","plain");
                connection.setRequestProperty("options","1");
//            connection.setRequestProperty("callback",);
                connection.setDoInput(true);
                connection.connect();
*/

                return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            ((TextView)view).setText(s);
        }
    }
}
