package brama.com.hearthum;

/**
 * Created by ABM on 17.07.2015..
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAdvanced extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_advanced, container, false);
    }


    // TODO Ability to upload existing record to amazon web services storage

    // TODO Ability to check if any of records that is being analyzed remotely
    // from amazon web services was marked as positive
}