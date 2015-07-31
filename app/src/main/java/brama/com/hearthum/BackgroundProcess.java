package brama.com.hearthum;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by vedra on 31.07.2015..
 */
public class BackgroundProcess extends AsyncTask<Void, Void, Void> {
    Context context;
    BackgroundProcess(Context context)    {
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        //Do stuff that you want after completion of background task and also dismiss progress here.
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //create and show progress dialog here
    }
}
