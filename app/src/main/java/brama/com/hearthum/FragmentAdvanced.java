package brama.com.hearthum;

/**
 * Created by ABM on 17.07.2015..
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


// TODO save and load data from shared preferendes
// http://stackoverflow.com/questions/23024831/android-shared-preferences-example
public class FragmentAdvanced extends Fragment {
    private static final String S3_ACCESS_KEY = "AKIAJQOEY2INBUEDHDGQ";
    private static final String S3_SECRET = "Nu5tgPgLuvanSFkIkzKAfh/6HayD0F6JhsuPHykt";
    private static final String S3_BUCKETNAME = "hearthum.files";
    private static final String TAG = "upload";


    Switch awsSwitch;
    Switch periodicalCheckSwitch;
    Switch emailMeSwitch;
    EditText emailEditText;
    Button btnUpload;
    Button btnCheck;
    TextView txtStatus;



    private ArrayList<HashMap<String, Object>> transferRecordMaps;



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
        txtStatus = (TextView)getActivity().findViewById(R.id.textViewStatus);

        awsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //write to settings
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
                uploadFile();
            }
        });

        loadSettings();

    }

    public void uploadFile(){
        /*String filePath = ((MainActivity) getActivity()).getFileName();
        AWSCredentials credentials = new BasicAWSCredentials(S3_ACCESS_KEY, S3_SECRET);
        TransferManager manager = new TransferManager(credentials);
        try {
            Upload upload = manager.upload(S3_BUCKETNAME, "TEST.m4a", new File(filePath));
            while (upload.isDone() == false) {
                //System.out.println(upload.getProgress().getPercentTransferred() + "%");
                txtStatus.setText(upload.getProgress().getPercentTransferred() + "%");
            }
            txtStatus.setText("100%\n" + upload.getDescription());

        }catch (Exception e){
            e.printStackTrace();
        }*/



        File file = new File(((MainActivity) getActivity()).getFileName());
        //new HttpRequestTask().execute();

        //DOBAR, RADI
        //new HttpPostTask().execute();
        List<Record> recordList = new ArrayList<Record>();
        LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
        String filesPaths[] = null;
        try{
            recordList = db.getAllRecords();
            filesPaths = new String[recordList.size()];
            Log.d("Files to upload", String.format("%d", recordList.size()));
            int i = 0;
            for (Record r : recordList){
                filesPaths[i]=r.getFullPath();
                Log.d("About to upload:", filesPaths[i]);
                i++;

            }
            new MultipleFileUpload().execute(filesPaths);
        }catch (Exception e){
            // Todo database error handling
            e.printStackTrace();
        }

        //RADI
        //new FileUpload().execute(filesPaths);



        //new FileUpload().execute(((MainActivity) getActivity()).getFileName());
        // /new LongOperation().execute("http://192.168.1.182:8080/HeartHumWS/rs/service/getSomething?request=oleeeeeeeeee");
        //new FileUpload().execute("http://localhost:8080/HeartHumWS/rs/service/postRealFile", ((MainActivity) getActivity()).getFileName());


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
        emailMeSwitch.setChecked(prefs.getBoolean("emailMe", false));;
    }

    //ovo šljaka
    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            Toast.makeText(getActivity(), greeting.getId() + "\n" + greeting.getContent(), Toast.LENGTH_LONG).show();
        }
    }

    private class HttpPostTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                // The connection URL
                String url = "192.168.1.182/greeting";
                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                // Add the String message converter
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                // Make the HTTP GET request, marshaling the response to a String
                String result = restTemplate.getForObject(url, String.class, "Android");
                return result;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
        }
    }

    private class FileUpload extends AsyncTask<String, Void, ResponseEntity<String>> {

        String urlLocation="no_loc";
        String fileLocation="no_file";
        TextView txt;

        @Override
        protected ResponseEntity<String> doInBackground(String... params) {
            ResponseEntity<String> responseEntity=null;

                FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
                formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add( formHttpMessageConverter );
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

                String uri = "http://192.168.1.182:8080/fileUpload";
                String filePath = params[0];
                File file = new File(filePath);

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("name", file.getName()+".m4a");
                map.add("file", new FileSystemResource(filePath));

                HttpHeaders imageHeaders = new HttpHeaders();
                imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

                try {
                    responseEntity = restTemplate.exchange(uri, HttpMethod.POST, imageEntity, String.class);
                }catch (Exception e){
                    e.printStackTrace();
                }


            return responseEntity;
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> result) {
            if(result != null){
                Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "response failed", Toast.LENGTH_LONG).show();
            }
        }


    }

    private class MultipleFileUpload extends AsyncTask<String, Void, ResponseEntity<String>> {

        String urlLocation="no_loc";
        String fileLocation="no_file";
        TextView txt;

        @Override
        protected ResponseEntity<String> doInBackground(String... params) {
            try {

                ResponseEntity<String> responseEntity = null;

                FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
                formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(formHttpMessageConverter);
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

                String uri = "http://192.168.1.182:8080/multipleFileUpload";
                String filePath[] = new String[params.length];
                File file[] = new File[params.length];
                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                for (int i = 0; i < params.length; i++) {
                    Log.d("Ubacivanje elemenata", "file: " + params[i]);
                    filePath[i] = params[i];
                    file[i] = new File(filePath[i]);
                    map.add("file", new FileSystemResource(filePath[i]));
                }


                //map.add("name", file.getName()+".m4a");


                HttpHeaders imageHeaders = new HttpHeaders();
                imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

                try {
                    responseEntity = restTemplate.exchange(uri, HttpMethod.POST, imageEntity, String.class);
                } catch (Exception e) {
                    Log.e("MultipleFileUpload", "Greska kod exchange: " + e.getMessage());
                    e.printStackTrace();
                }


                return responseEntity;
            }catch (Exception e){
                Log.e("MultipleFileUpload", "WTF: " + e.getMessage());
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> result) {
            if(result != null){
                String body = result.getBody();
                Log.d("postRequestresult", body);
                Toast.makeText(getActivity(), body, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "response is null", Toast.LENGTH_LONG).show();
            }
        }


    }
}
