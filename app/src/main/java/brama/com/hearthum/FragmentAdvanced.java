package brama.com.hearthum;

/**
 * Created by ABM on 17.07.2015..
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

// TODO save and load data from shared preferendes
// http://stackoverflow.com/questions/23024831/android-shared-preferences-example
public class FragmentAdvanced extends Fragment {
    Switch awsSwitch;
    Switch periodicalCheckSwitch;
    Switch emailMeSwitch;
    EditText emailEditText;
    Button btnUpload;
    Button btnCheck;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_advanced, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        awsSwitch = (Switch)getActivity().findViewById(R.id.switchAmazonWS);
        emailMeSwitch =(Switch) getActivity().findViewById(R.id.switchEmailNotif);
        periodicalCheckSwitch = (Switch)getActivity().findViewById(R.id.switchListenBeacon);
        emailEditText = (EditText)getActivity().findViewById(R.id.emailTextEdit);
        btnUpload = (Button)getActivity().findViewById(R.id.btnUpload);
        btnCheck = (Button)getActivity().findViewById(R.id.btnCheckDiagnosis);

        awsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE).edit();
                editor.putBoolean("AWS", isChecked);
                editor.commit();
            }
        });

        emailMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE).edit();
                editor.putBoolean("emailMe", isChecked);
                editor.commit();
            }
        });

        periodicalCheckSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE).edit();
                editor.putBoolean("periodicalCheck", isChecked);
                editor.commit();

            }
        });


        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE).edit();
                    editor.putString("email", ((EditText) v).getText().toString());
                    //Toast.makeText(getActivity(), ((EditText) v).getText().toString(), Toast.LENGTH_LONG).show();
                    editor.commit();
                    ((EditText) v).setSelected(false);

                }
            }
        });
        emailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    v.clearFocus();
                    ((EditText) v).setCursorVisible(false);
                    //btnCheck.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //Toast.makeText(getActivity(), "Lose focus NOW", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        emailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) v).setCursorVisible(true);

            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo perform check
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo perform upload
            }
        });

        loadSettings();

    }
    public void loadSettings(){
        /*
        *  Switch awsSwitch;
        *  Switch periodicalCheckSwitch;
        *  Switch emailMeSwitch;
        *  EditText emailEditText;
        * */
        SharedPreferences prefs = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE);
        String restoredText = prefs.getString("email", null);
        if (restoredText != null) {
            emailEditText.setText(restoredText);
        }

        Boolean aws = prefs.getBoolean("AWS", false);
        awsSwitch.setChecked(aws);
        periodicalCheckSwitch.setChecked(prefs.getBoolean("periodicalCheck", false));
        emailMeSwitch.setChecked(prefs.getBoolean("emailMe", false));
    }

}