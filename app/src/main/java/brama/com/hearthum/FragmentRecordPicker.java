package brama.com.hearthum;

/**
 * Created by ABM on 18.07.2015..
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRecordPicker extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_record_picker, container, false);
    }
}