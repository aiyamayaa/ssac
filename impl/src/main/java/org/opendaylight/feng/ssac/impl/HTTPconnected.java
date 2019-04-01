/*
 * Copyright © 2017 BNI, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPconnected {

    private static final String endpoint = "http://10.112.160.151:8181/restconf/operations/mmdc:command";
    private static final String delete_url = "http://10.112.160.151:8181/restconf/operations/mmdc:delete";



    public String createVM(String type){
        String payload = getPayload(type);
        String res = HTTP_POST_Authorition(endpoint,payload);
        JSONObject object = new JSONObject(res);
        System.out.println(object.toString());
        return object.toString();
    }

    public Boolean delete( String id){
        String payload = "{\"input\":{\"endpoint\":\"3\",\"VmId\":\""+id+"\"}}";
//        String responsed = HTTP_DELETE_Authorition(delete_url,payload);
        String responsed = HTTP_POST_Authorition(delete_url, payload);
        System.out.println(responsed);
        if(responsed!=null){
            return true;
        }else{
            return false;
        }
    }

    public String deleteVM(){
        return null;
    }
    public String getPayload(String type){
        String payload= "{\"input\":{\"operation\":\"createVM\",\"name\":\""+type+"\",\"endpoint\":\"3\"}}";
        return payload;
    }

    public static String HTTP_POST_Authorition(String endpoint, String payload){

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Authorization","Basic YWRtaW46YWRtaW4=");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(payload.getBytes("UTF-8"));
            out.flush();
            out.close();

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

    public static String HTTP_DELETE_Authorition(String endpoint, String payload){

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("authorization","Basic YWRtaW46YWRtaW4=");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(payload.getBytes("UTF-8"));
            out.flush();
            out.close();

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
