package brama.com.hearthum;

/**
 * Created by ABM on 16.07.2015..
 */
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class FragmentRecording extends Fragment {
    Button play,stop,record;
    Button recordA, recordP, recordE, recordT,recordM;
    private MediaRecorder myAudioRecorder;
    private MediaPlayer myMediaPlayer;
    private String outputFile = null;
    Record audioFile;
    public FragmentRecording() {
    }

    /*public void doRecord(View v){
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        }

        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        record.setEnabled(false);
        stop.setEnabled(true);

        Toast.makeText(getActivity().getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
    }*/

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

            // TODO Create a new record in database with the given data and get the ID of a record
            audioFile.setID(0);

            outputFile = audioFile.getFullPath();

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
}