package org.opendaylight.feng.ssac.impl.ComputeResourceManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OsmAgency {

    int port = 23456;
    String ip = "10.112.197.221";
    Socket socket = null;

    public OsmAgency(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public OsmAgency() {

    }

    public void getResult(String request){
        try {
            socket = new Socket(ip, port);
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes());
            out.flush();
            out.close();
            socket.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void configOsm(String cmd){
        OsmAgency agency = new OsmAgency(23456,"10.112.197.221");
        Thread t = new Thread(()->{
           agency.getResult(cmd);
        });
        t.start();
    }

}
