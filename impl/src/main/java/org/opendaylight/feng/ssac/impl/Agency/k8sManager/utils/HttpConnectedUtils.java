/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;


public class HttpConnectedUtils {

    public String getPayload(String type){
        String payload= "{\"input\":{\"operation\":\"createVM\",\"name\":\""+type+"\",\"endpoint\":\"3\"}}";
        return payload;
    }

    public static String HTTP_POST(String endpoint, String payload){
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(payload.getBytes("UTF-8"));
            out.flush();
            out.close();
            System.out.println(connection.getResponseCode());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String lines ="";
            while ((lines=reader.readLine())!=null){
                lines = new String(lines.getBytes("UTF-8"));
                sb.append(lines);
            }
            connection.disconnect();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String HTTP_GET(String endpoint){
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();


            System.out.println(connection.getResponseCode());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String lines ="";
            while ((lines=reader.readLine())!=null){
                lines = new String(lines.getBytes("UTF-8"));
                sb.append(lines);
            }
            connection.disconnect();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String HTTP_DELETE(String endpoint){

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String lines ="";
            while ((lines=reader.readLine())!=null){
                lines = new String(lines.getBytes("UTF-8"));
                sb.append(lines);
            }
            connection.disconnect();
            return sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String HTTP_PUT(String endpoint, String payload){
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(payload.getBytes("UTF-8"));
            out.flush();
            out.close();
            System.out.println(connection.getResponseCode());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String lines ="";
            while ((lines=reader.readLine())!=null){
                lines = new String(lines.getBytes("UTF-8"));
                sb.append(lines);
            }
            connection.disconnect();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
