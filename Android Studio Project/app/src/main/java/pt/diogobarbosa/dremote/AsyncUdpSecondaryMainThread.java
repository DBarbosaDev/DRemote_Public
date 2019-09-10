package pt.diogobarbosa.dremote;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

public class AsyncUdpSecondaryMainThread extends AsyncTask<String[], Void, String> {

    private Helper helper = new Helper();
    private int serverPort;

    public static JSONObject _serverResponse2;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String[]... serverInfo) {
        serverPort = Integer.parseInt(serverInfo[0][1]);
        try {
            Log.e("Info", "Secondary thread executed with delay");
            Thread secondThread = new Thread(new UdpSecondaryThread(serverPort));
            secondThread.start();
            secondThread.join();
        } catch (Exception e) {
            Log.e("Interrupted thread", e.toString());
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("Info", "Secondary thread PostExe. executed.");
        helper.validateSecondMessage(_serverResponse2);
    }

}
