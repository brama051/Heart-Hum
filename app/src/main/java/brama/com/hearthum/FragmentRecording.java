package brama.com.hearthum;

/**
 * Created by ABM on 16.07.2015..
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import brama.com.hearthum.entities.MultipleFileUploadResponse;

public class FragmentRecording extends Fragment {
    Button play,stop,record;
    Button recordA, recordP, recordE, recordT,recordM;
    private MediaRecorder myAudioRecorder;
    private MediaPlayer myMediaPlayer;
    private String outputFile = null;
    Record audioFile;
    String email;
    Boolean autoUpload;

    public FragmentRecording() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        audioFile = new Record();
        //Inflate the layout for this fragment
        play=(Button)getActivity().findViewById(R.id.buttonPlay);
        stop=(Button) getActivity().findViewById(R.id.buttonStop);
        record=(Button)getActivity().findViewById(R.id.buttonRec);
        recordA=(Button)getActivity().findViewById(R.id.buttonRecA);
        recordP=(Button)getActivity().findViewById(R.id.buttonRecP);
        recordE=(Button)getActivity().findViewById(R.id.buttonRecE);
        recordT=(Button)getActivity().findViewById(R.id.buttonRecT);
        recordM=(Button)getActivity().findViewById(R.id.buttonRecM);

        stop.setEnabled(false);
        play.setEnabled(false);
        record.setEnabled(true);
        recordA.setEnabled(true);
        recordP.setEnabled(true);
        recordE.setEnabled(true);
        recordT.setEnabled(true);
        recordM.setEnabled(true);

        //outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording2.3gp";




        record.setOnClickListener(btnRecordListener);
        recordA.setOnClickListener(btnRecordListener);
        recordP.setOnClickListener(btnRecordListener);
        recordE.setOnClickListener(btnRecordListener);
        recordT.setOnClickListener(btnRecordListener);
        recordM.setOnClickListener(btnRecordListener);


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMediaPlayer!=null && myMediaPlayer.isPlaying()){
                    myMediaPlayer.stop();
                    myMediaPlayer.release();
                }

                if (myAudioRecorder!=null){
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                }

                if( autoUpload && email!=null && email.length()!=0 ){
                    String[] fileAndEmail = new String[2];
                    fileAndEmail[0] = email ;
                    fileAndEmail[1] = outputFile;
                    new MultipleFileUpload().execute(fileAndEmail);
                }

                stop.setEnabled(false);
                play.setEnabled(true);
                record.setEnabled(true);
                recordA.setEnabled(true);
                recordP.setEnabled(true);
                recordE.setEnabled(true);
                recordT.setEnabled(true);
                recordM.setEnabled(true);

                Toast.makeText(getActivity().getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                stop.setEnabled(true);
                play.setEnabled(false);
                record.setEnabled(false);
                recordA.setEnabled(false);
                recordP.setEnabled(false);
                recordE.setEnabled(false);
                recordT.setEnabled(false);
                recordM.setEnabled(false);
                myMediaPlayer = new MediaPlayer();

                try {
                    myMediaPlayer.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    myMediaPlayer.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play.setEnabled(true);
                    }
                });
                myMediaPlayer.start();
                Toast.makeText(getActivity().getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        loadSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_recording, container, false);
    }

    private View.OnClickListener btnRecordListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myAudioRecorder=new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //myAudioRecorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
            myAudioRecorder.setAudioEncodingBitRate(16);
            //myAudioRecorder.setAudioSamplingRate(96000);

            record.setEnabled(false);
            recordA.setEnabled(false);
            recordP.setEnabled(false);
            recordE.setEnabled(false);
            recordT.setEnabled(false);
            recordM.setEnabled(false);
            stop.setEnabled(true);

            Button btn = (Button) v;
            //outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording2.3gp";
            audioFile.setFileDirectory(Environment.getExternalStorageDirectory().getAbsolutePath());
            audioFile.setFileName("HeartHum");
            audioFile.setTimeRecorded(new Date());
            audioFile.setHeartPositionListened(btn.getText().toString());
            audioFile.setExtension(getResources().getString(R.string.file_extension));
            audioFile.setIsUploaded(0);



            LocalDatabaseHandler db = new LocalDatabaseHandler(getActivity());
            audioFile.setID(db.createRecord());
            db.updateRecord(audioFile);
            outputFile = audioFile.getFullPath();

            //save racording data to object in main activity
            ((MainActivity)getActivity()).setmRecord(audioFile);
            ((MainActivity)getActivity()).setFileName(audioFile.getFullPath());

            myAudioRecorder.setOutputFile(outputFile);
            try {
                myAudioRecorder.prepare();
                myAudioRecorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity().getApplicationContext(), "Recording started: " + outputFile, Toast.LENGTH_LONG).show();
        }
    };

    public class MultipleFileUpload extends AsyncTask<String, Void, MultipleFileUploadResponse> {

        String urlLocation="no_loc";
        String fileLocation="no_file";
        TextView txt;

        @Override
        protected MultipleFileUploadResponse doInBackground(String... params) {
            try {

                MultipleFileUploadResponse responseEntity = null;

                FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
                formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(formHttpMessageConverter);
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
                String email = params[0];
                String uri = "http://192.168.1.182:8080/multipleFileUpload";
                String filePath[] = new String[params.length-1];
                File file[] = new File[params.length-1];
                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("email", email);
                for (int i = 1; i < params.length; i++) {
                    Log.d("Ubacivanje elemenata", "file: " + params[i]);
                    filePath[i-1] = params[i];
                    file[i-1] = new File(filePath[i-1]);
                    map.add("file", new FileSystemResource(filePath[i-1]));
                }

                HttpHeaders imageHeaders = new HttpHeaders();
                imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

                try {
                    responseEntity = restTemplate.postForObject(uri, imageEntity, MultipleFileUploadResponse.class);
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
        protected void onPostExecute(MultipleFileUploadResponse result) {
            if(result != null){
                //String body = result.getBody();
                Log.d("postRequestresult", result.toString());
                Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "response is null", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadSettings(){

        SharedPreferences prefs = getActivity().getSharedPreferences("HeartHum", Context.MODE_PRIVATE);
        String restoredText = prefs.getString("email", null);
        if (restoredText != null) {
            email = restoredText;
        }

        Boolean aws = prefs.getBoolean("AWS", false);
        autoUpload = aws;
        ;
    }
}