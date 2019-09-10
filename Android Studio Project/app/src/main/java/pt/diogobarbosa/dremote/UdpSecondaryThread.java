package pt.diogobarbosa.dremote;

import android.util.Log;
import org.json.JSONObject;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class UdpSecondaryThread extends Thread implements Runnable {

    private int serverPort;
    private byte[] buffer = new byte[1000];

    private DatagramSocket udpSocket;
    private DatagramPacket datagramPacket;

    private Boolean inThread;

    public UdpSecondaryThread(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        createUdpSocket();
        receiveMessage();

        udpSocket.close();
    }

    private void createUdpSocket() {
        try {
            udpSocket = new DatagramSocket(serverPort);
        } catch (Exception e) {
            Log.e("Udp Socket:", "Error:", e);
        }
    }

    private void receiveMessage() {
        Log.e("Info", "Secondary Receving in process");
        inThread = true;
        try {

            while (inThread) {
                try {

                    datagramPacket = new DatagramPacket(buffer, buffer.length);

                    udpSocket.setSoTimeout(10000);
                    udpSocket.receive(datagramPacket);

                    String serverResponse = new String(buffer, 0, datagramPacket.getLength());
                    JSONObject responseObj = new JSONObject(serverResponse);

                    Log.e("Socket Open:", String.valueOf(responseObj));

                    AsyncUdpSecondaryMainThread._serverResponse2 = responseObj;

                    inThread = false;

                } catch (SocketTimeoutException s) {
                    inThread = false;
                    udpSocket.close();
                }
            }
        } catch (Exception e) {
            Log.e("Socket Open:", "Error:", e);
        }
    }

}
