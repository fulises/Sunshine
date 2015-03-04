package app.com.example.ulixes.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class ForecastFragment extends Fragment {
    ArrayAdapter<String> mForecastAdapter;
    public ForecastFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        //fake data
        String[] fake_forecast={
                "Wed-Cloudy 77/66",
                "Tue-Meat 797/866",
                "Thr-Vegetable 77/66",
                "Fri-Cloudy 775/66",
                "Sat-Sunny 757/66",
                "Sun-Always 5/66",
                "Mon-Philly 77/66",
                "Tue-Danny 77/66",
                "Wed-De 77/66",
                "Thr-Vito 77/66"};

        List<String> weekForecast;
        weekForecast = new ArrayList<String>(Arrays.asList(fake_forecast));

        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),// the courrent context of activity
                        R.layout.list_item_forecast,//The name of the layoutId
                        R.id.list_item_forecast_textview,//The id of the text view to populate
                        fake_forecast);



        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ////after the darn thing has been inflated se supone que se le
        // agregan los demas elementos

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);





        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.forecastfragment, menu);

    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh:
                FetchweatherTask weatherTask = new FetchweatherTask();
                weatherTask.execute();
                return true;
            default:
                return  super.onOptionsItemSelected(item);

        }

    }

        public class FetchweatherTask extends AsyncTask<URL, Integer, Long>{

        private final String LOG_TAG= FetchweatherTask.class.getSimpleName();//She wants somthing to be in sync???
        //  ("http://api.openweathermap.org/data/2.5/forecast/daily?q=08861&mode=json&units=imperial&cnt=7");
        // using URI builder implementation for the query sting
            final String FORECAST_BASE_URI =
                "http://api.openweathermap.org/data/2.5/forecast/daily?";


            final String Q_PARAM ="q";
            final String F_PARAM = "mode";
            final String U_PARAM = "units";
            final String Days_PARAM = "cnt";



            Uri platano = Uri.parse(FORECAST_BASE_URI).buildUpon()
                    .appendQueryParameter(Q_PARAM, "08861")
                    .appendQueryParameter(F_PARAM,"json")
                    .appendQueryParameter(U_PARAM,"imperial")
                    .appendQueryParameter(Days_PARAM,Integer.toString(7))
                    .build();
            String platanoString = platano.toString();











            @Override
        protected Long doInBackground(URL... params) {
            // These two need to be declared outside the try/catch  x
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(platano.toString());//Se estaba explotando porque hay que manejar execptiones sabra dios despues lo investigamos

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
            Log.v(LOG_TAG,"Foreca JSON String"+forecastJsonStr);


        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

            return null;
        }

    }

}