package brama.com.hearthum;

/**
 * Created by ABM on 18.07.2015..
 */


import android.content.res.Resources;
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

    View[] itemView;

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
                //text.setBackgroundColor(getResources().));
            }
        });
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.listContainer);
        linearLayout.addView(text);


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

        itemView = new View[recordList.size()];
        int i = 0;
        for (Record r : recordList){
            itemView[i] = View.inflate(getActivity(), R.layout.element_record_picker, null);
            TextView txtFileName = (TextView) itemView[i].findViewById(R.id.textViewFileName);
            TextView txtFileDesc = (TextView) itemView[i].findViewById(R.id.textViewDescription);
            txtFileName.setText(r.getFileName() + "_" + r.getID() + ".m4a");
            txtFileDesc.setText(r.getFileDirectory() + "\n" + r.getTimeRecorded() + "\n" + r.getHeartPositionListened());
            itemView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applyUnselectedStyle();
                    v.setBackground(getActivity().getResources().getDrawable(R.drawable.border_set_selected));
                    Toast.makeText(getActivity(), r.getFullPath() + " SELECTED", Toast.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).setFileName(r.getFullPath());
                }
            });
            linearLayout.addView(itemView[i]);
            i++;
        }
    }

    public void applyUnselectedStyle(){
        for (int i = 0; i<itemView.length;i++){
            itemView[i].setBackground(getResources().getDrawable(R.drawable.border_set));
            /*LinearLayout view = (LinearLayout)itemView[i].findViewById(R.id.layoutWrapper);
            view.setBackground(R.drawable.border_set);*/
        }

    }
}