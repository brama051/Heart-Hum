package brama.com.hearthum;

/**
 * Created by ABM on 18.07.2015..
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class FragmentRecordPicker extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        /*TextView text = new TextView(getActivity());
        text.setText("RADI LI OVO");
        text.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.listContainer);

        linearLayout.addView(text);*/
        return inflater.inflate(R.layout.fragment_record_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = new TextView(getActivity());
        text.setText("RADI LI OVO");
        text.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        text.setBackgroundColor(542);
        text.setClickable(true);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "tekst kliknut", Toast.LENGTH_LONG);
            }
        });
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.listContainer);
        linearLayout.addView(text);

        //ovako se uèitava custom layout u postojeæi
        View headerView = View.inflate(getActivity(), R.layout.element_record_picker, null);
        //View headerView = View.inflate(getActivity(), R.layout.element_record_picker, linearLayout);
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "layout kliknut", Toast.LENGTH_LONG);

            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getActivity(), "layout OnTouch", Toast.LENGTH_LONG);


                return false;
            }
        });
        linearLayout.addView(headerView);
    }

    /*ListView listView;

    List<Map<String, String>> planetsList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_record_picker, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getActivity().findViewById(R.id.listView);
        initList();

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(R.color.button_material_light);
        // React to user clicks on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {


                // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;

                Toast.makeText(getActivity(), "Item with id [" + id + "] - Position [" + position + "] - Planet [" + clickedView.getText() + "]", Toast.LENGTH_SHORT).show();

            }
        });
        listView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "long press ne radi",Toast.LENGTH_LONG);
                getActivity().openContextMenu(getView());
                return false;
            }


        });
        SimpleAdapter simpleAdpt = new SimpleAdapter(getActivity(), planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
        listView.setAdapter(simpleAdpt);
        registerForContextMenu(listView);

    }

    private void initList() {
        // We populate the planets
        planetsList = new ArrayList<Map<String, String>>();

        planetsList.add(createPlanet("planet", "Mercury"));
        planetsList.add(createPlanet("planet", "Venus"));
        planetsList.add(createPlanet("planet", "Mars"));
        planetsList.add(createPlanet("planet", "Jupiter"));
        planetsList.add(createPlanet("planet", "Saturn"));
        planetsList.add(createPlanet("planet", "Uranus"));
        planetsList.add(createPlanet("planet", "Neptune"));
    }
    private HashMap<String, String> createPlanet(String key, String name) {
        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);

        return planet;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Implements our logic
        Toast.makeText(getActivity(), "Item id ["+itemId+"]", Toast.LENGTH_SHORT).show();
        return true;
    }*/
}