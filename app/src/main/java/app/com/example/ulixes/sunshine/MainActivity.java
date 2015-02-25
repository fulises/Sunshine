package app.com.example.ulixes.sunshine;

import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Un comentario para que diga algo
        // Inflate the menu; this akkkdds items to the action bar if it is present.jhkjhjhkjhlkj
        //Ja vamos a ver si puede usar el commit
        
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        ArrayAdapter<String> mForecastAdapter;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


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

            List<String> weekForecast = new ArrayList<String>(Arrays.asList(fake_forecast));

            mForecastAdapter =
                   new ArrayAdapter<String>(
                           getActivity(),// the courrent context of activity
                           R.layout.list_item_forecast,//The name of the layoutId
                           R.id.list_item_forecast_textview,//The id of the text view to populate
                           fake_forecast);



            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ////after the darn thing has been inflated se supone que se le
            // agregan los demas elementos

            ListView    listView = (ListView)rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);



            return rootView;
        }
    }
}
