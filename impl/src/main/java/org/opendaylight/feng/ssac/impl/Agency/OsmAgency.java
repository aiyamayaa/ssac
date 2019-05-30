/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency;

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



}
