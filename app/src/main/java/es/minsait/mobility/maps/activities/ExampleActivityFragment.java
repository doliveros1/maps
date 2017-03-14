package es.minsait.mobility.maps.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import es.minsait.mobility.maps.R;
import es.minsait.mobility.maps.fragments.ExampleFragmentMap;

/**
 * Created by davidoliverosescribano on 10/3/17.
 */

public class ExampleActivityFragment extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setFragment((Fragment)ExampleFragmentMap.newInstance());
    }

    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
