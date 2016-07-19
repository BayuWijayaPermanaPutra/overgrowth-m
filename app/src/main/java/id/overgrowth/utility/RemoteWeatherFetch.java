package id.overgrowth.utility;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import id.overgrowth.R;

/**
 * Created by bayu_ on 7/16/2016.
 */
public class RemoteWeatherFetch {
    public static JSONObject getJSON(Context context){
        try {
            URL url = new URL(String.format(UrlApi.urlOpenWeatherMapApi));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("=",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());


            if(data.getInt("cod") != 200){
                Toast.makeText(context, "Data Cuaca berada di luar jangkauan!", Toast.LENGTH_SHORT).show();
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}
