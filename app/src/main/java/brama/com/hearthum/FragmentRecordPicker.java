package brama.com.hearthum;

/**
 * Created by ABM on 18.07.2015..
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

// Todo change colors and fix menus
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
        final int button1id = View.generateViewId();
        text.setId(button1id); // id
        text.setText("RADI LI OVO");
        text.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        text.setBackgroundColor(542);
        text.setClickable(true);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "tekst kliknut", Toast.LENGTH_LONG).show();
                text.setBackgroundColor(R.drawable.abc_list_pressed_holo_dark);
            }
        });
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.listContainer);
        linearLayout.addView(text);

        //ovako se uèitava custom layout u postojeæi

        //OVO RADI
        /*TextView text2 = (TextView)headerView.findViewById(R.id.textViewFileName);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "tekst kliknut", Toast.LENGTH_LONG).show();

            }
        });
        text2.setLongClickable(true);
        text2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                headerView.setBackgroundColor(R.drawable.abc_list_pressed_holo_dark);

                return false;
            }
        });*/
        List<Record> recordList = new ArrayList<Record>();

        LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
        try{
            recordList = db.getAllRecords();
            text.setText("There are " + recordList.size()
                    + " records" );
        }catch (Exception e){
            // Todo database error handling
            text.setText("Error in database" );
        }



        View[] headerView = new View[recordList.size()];
        int i = 0;
        for (Record r : recordList){
            headerView[i] = View.inflate(getActivity(), R.layout.element_record_picker, null);
            TextView txtFileName = (TextView) headerView[i].findViewById(R.id.textViewFileName);
            TextView txtFileDesc = (TextView) headerView[i].findViewById(R.id.textViewDescription);
            txtFileName.setText(r.getFileName() + "_" + r.getID() + R.string.file_extension);
            txtFileDesc.setText(r.getFileDirectory() + "\n" + r.getTimeRecorded() + "\n" + r.getHeartPositionListened());
            headerView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), r.getFullPath() + " SELECTED", Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).setFileName(r.getFullPath());
                }
            });
            linearLayout.addView(headerView[i]);
            i++;
        }
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