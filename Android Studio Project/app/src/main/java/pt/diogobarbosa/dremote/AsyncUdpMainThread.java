package pt.diogobarbosa.dremote;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import org.json.JSONObject;

public class AsyncUdpMainThread extends AsyncTask<String[], Void, String> {

    private String[][] serverInfo;
    private Helper helper = new Helper();

    private String serverIp;
    private String serverPort;
    private String message;

    public static JSONObject _serverResponse;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Dremote_main._formLayout.setVisibility(View.GONE);
        Dremote_main._progressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String[]... serverInfo) {

        this.serverInfo = serverInfo;
        serverIp = serverInfo[0][0];
        serverPort = serverInfo[0][1];
        message = serverInfo[0][2];

        try {
            Thread firstThread = new Thread(new UdpMainThread(serverIp, serverPort, message));
            firstThread.start();
            firstThread.join();

        } catch (Exception e) {
            Log.e("Interrupted thread", e.toString());
        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        helper.validateFirstMessage(_serverResponse,serverInfo);

    }

}
