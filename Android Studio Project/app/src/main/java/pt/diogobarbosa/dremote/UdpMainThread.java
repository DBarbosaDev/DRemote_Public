package pt.diogobarbosa.dremote;

import android.util.Log;
import org.json.JSONObject;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UdpMainThread extends Thread implements Runnable {

    private String serverIp;
    private int serverPort;
    private String message;
    private byte[] buffer = new byte[1000];

    private DatagramSocket udpSocket;
    private DatagramPacket datagramPacket;
    private InetAddress serverAddress;

    private boolean inThread;

    public UdpMainThread(String serverIp, String serverPort, String message) {
        this.serverIp = serverIp;
        this.serverPort = Integer.parseInt(serverPort);
        this.message = message;
    }

    @Override
    public void run() {
        createUdpSocket();
        sendByteMessage();
        receiveMessage();

        udpSocket.close();

    }

    private void createUdpSocket() {
        try {
            udpSocket = new DatagramSocket(serverPort);
            this.serverAddress = InetAddress.getByName(serverIp);
        } catch (Exception e) {
            Log.e("Udp Socket:", "Error:", e);
        }
    }

    private void sendByteMessage() {
        try {
            byte[] byteMessage = (this.message).getBytes();
            this.datagramPacket = new DatagramPacket(byteMessage, byteMessage.length, serverAddress, serverPort);
            udpSocket.send(this.datagramPacket);

        } catch (Exception e) {
            Log.e("Sending byte message:", "Error:", e);
        }
    }

    private void receiveMessage() {
        inThread = true;
        try {
            while (inThread) {
                try {

                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

                    udpSocket.setSoTimeout(10000);
                    udpSocket.receive(datagramPacket);

                    String serverResponse = new String(buffer, 0, datagramPacket.getLength());
                    JSONObject responseObj = new JSONObject(serverResponse);

                    AsyncUdpMainThread._serverResponse = responseObj;

                    Log.e("Socket Open:", String.valueOf(responseObj));

                    inThread = false;
                } catch (SocketTimeoutException s) {
                    JSONObject timedOutResponse = new JSONObject();
                    timedOutResponse.put("error", "true");
                    timedOutResponse.put("message", "Problems reaching the server. Try again latter.");

                    AsyncUdpMainThread._serverResponse = timedOutResponse;

                    inThread = false;
                    udpSocket.close();
                }
            }
        } catch (Exception e) {
            Log.e("Socket Open:", "Error:", e);
        }
    }
}

