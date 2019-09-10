package pt.diogobarbosa.dremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Dremote_main extends AppCompatActivity {

    private EditText etMessage, destIP, destPort;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEdit;

    public static Context _context;
    public static TextView _tvState;
    public static Button _btnAction;

    public static LinearLayout _progressBarLayout, _formLayout, _serverFieldsLayout, _buttonOpenLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dremote_main);

        destIP = findViewById(R.id.destIP);
        destPort = findViewById(R.id.destPort);
        etMessage = findViewById(R.id.etMessage);

        getSharedPreferences();

        _context = this.getApplicationContext();
        _tvState = findViewById(R.id.tvState);
        _progressBarLayout = findViewById(R.id.progressBarLayout);
        _formLayout = findViewById(R.id.formLayout);
        _serverFieldsLayout = findViewById(R.id.serverFieldsLayout);
        _buttonOpenLayout = findViewById(R.id.buttonOpenLayout);
        _btnAction = findViewById(R.id.btnAction);

    }

    public void sendMessage(View v) {

        try {
            String serverInfo[] = new String[]{
                    destIP.getText().toString(),
                    destPort.getText().toString(),
                    etMessage.getText().toString()
            };
            if(!Boolean.parseBoolean(validateFields(serverInfo).get("error").toString())) {
                setSharedPreferences();
                this.startThreading(serverInfo);
            }else
                Toast.makeText(this, validateFields(serverInfo).get("message").toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void startThreading(String[] serverInfo) {

        new AsyncUdpMainThread().execute(serverInfo);
    }

    private Map validateFields(String[] fields){

        Map result = new HashMap<>();
        result.put("error",false);
        result.put("message","");

        if(fields.length != 3) {
            result.put("error",true);
            result.put("message","All fields must be filled.");
            return result;
        }

        if(fields[0].isEmpty() || fields[1].isEmpty() || fields[2].isEmpty()) {
            result.put("error",true);
            result.put("message","Some field have empty values.");
            return result;
        }

        if(Integer.parseInt(fields[1]) < 1024 || Integer.parseInt(fields[1]) > 65535) {
            result.put("error",true);
            result.put("message","The server port must be between 1024 and 65535.");
            return result;
        }

        return result;
    }

    private void getSharedPreferences(){
        sharedPreferences = getSharedPreferences("cacheServerInfo",MODE_PRIVATE);

        destIP.setText(sharedPreferences.getString("serverIp",""));
        destPort.setText(sharedPreferences.getString("serverPort",""));
        etMessage.setText(sharedPreferences.getString("serverMessage",""));

    }

    private void setSharedPreferences(){
        sharedEdit = getSharedPreferences("cacheServerInfo",MODE_PRIVATE).edit();

        sharedEdit.putString("serverIp",destIP.getText().toString());
        sharedEdit.putString("serverPort",destPort.getText().toString());
        sharedEdit.putString("serverMessage",etMessage.getText().toString());

        sharedEdit.apply();
    }
}
