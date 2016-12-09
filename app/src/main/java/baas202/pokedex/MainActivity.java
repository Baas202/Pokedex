package baas202.pokedex;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search_movies(View v) {

        btnHit = (Button) findViewById(R.id.search);
        txtJson = (TextView) findViewById(R.id.responseView);


        // get input tex

        EditText input_text = (EditText) findViewById(R.id.pokemonName);
        String api_url = input_text.getText().toString();
        getData(api_url);






        // combine the user input with the JsonTask





    }
    public void getData(String api_url) {

        // used videos from https://www.youtube.com/watch?v=frltqnSKqiA : Hisham Muneer
        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

            private static final String url1 = "http://pokeapi.co/api/v2/pokemon/";

            protected void onPreExecute() {
                super.onPreExecute();

                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Please wait");
                pd.setCancelable(false);
                pd.show();
            }

            protected String doInBackground(String... params) {

                String pokemonname = params[0];
                URL url = null;
                try {
                    url = new URL(url1 + pokemonname);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line+"\n");
                        Log.d("Response: ", "> " + line);

                    }

                    return buffer.toString();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (pd.isShowing()){
                    pd.dismiss();
                }
                txtJson.setText(result);
            }
        };
        task.execute(api_url);

    }

}