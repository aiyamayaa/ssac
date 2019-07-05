/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ;

import org.json.JSONObject;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.CommonUtils;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.HttpConnectedUtils;


import java.util.Map;

public class K8sDeploymentBIZ {

    private static String url = "http://10.112.254.154:8080/";

    public String createDeployment(Map<String, String> config, String filePath){

        String tmpJson = "";
        JSONObject jsonObject = null;
        String name = config.get("name");
        String selector = config.get("selector");
        String namespace = config.get("namespace");
        String request_cpu = config.get("request_cpu");
        String request_memory = config.get("request_memory");
        String limits_cpu = config.get("limits_cpu");
        String limits_memory = config.get("limits_memory");
//        tmpJson = CommonUtils.convertYamlToJson(filePath);
        jsonObject = new JSONObject(tmpJson);

        jsonObject.getJSONObject("metadata").put("name",name);
        jsonObject.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONObject("nodeSelector").
                put("nodeId",selector);
        jsonObject.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers").
                getJSONObject(0).getJSONObject("resources").getJSONObject("limits").put("cpu",limits_cpu);
        jsonObject.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers").
                getJSONObject(0).getJSONObject("resources").getJSONObject("limits").put("memory",limits_memory);
        jsonObject.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers").
                getJSONObject(0).getJSONObject("resources").getJSONObject("requests").put("cpu",request_cpu);
        jsonObject.getJSONObject("spec").getJSONObject("template").getJSONObject("spec").getJSONArray("containers").
                getJSONObject(0).getJSONObject("resources").getJSONObject("requests").put("memory",request_memory);
        String formatstr = CommonUtils.formatJson(jsonObject.toString());
        System.out.println(formatstr);
        String deployment_url = url+"apis/extensions/v1beta1/namespaces/"+namespace+"/deployments/";
        System.out.println(deployment_url);
        String result = HttpConnectedUtils.HTTP_POST(deployment_url,jsonObject.toString());
        JSONObject retObj = new JSONObject(result);
        String uuid = retObj.getJSONObject("metadata").getString("uid");
        return uuid;
    }

    public boolean deleteDeployment(String namespace, String name){

        String deployment_url = url+
                "apis/extensions/v1beta1/namespaces/"+namespace+"/deployments/"+name+"/";
        String result = HttpConnectedUtils.HTTP_DELETE(deployment_url);
        if(result!=null){
            return true;
        }
        return false;
    }

    public String updateDeployment(Map<String, String> config, String filePath){

        String tmpJson = "";
        JSONObject jsonObject = null;
        String name = config.get("name");
        String selector = config.get("selector");
        String namespace = config.get("namespace");
        String request_cpu = config.get("request_cpu");
        String request_memory = config.get("request_memory");
        String limits_cpu = config.get("limits_cpu");
        String limits_memory = config.get("limits_memory");
//        tmpJson = CommonUtils.convertYamlToJson(filePath);
        jsonObject = new JSONObject(tmpJson);
        if(name!=null && name.length()!=0){
            jsonObject.getJSONObject("spec").getJSONObject("template").
                    getJSONObject("metadata").put("name",name);
        }
        if(selector!=null && selector.length()!=0){
            jsonObject.getJSONObject("spec").getJSONObject("template").
                    getJSONObject("spec").getJSONObject("nodeSelector").put("nodeId",selector);
        }
        if(request_cpu!=null && request_cpu.length()!=0){
            jsonObject.getJSONObject("spec").getJSONObject("template").
                    getJSONObject("spec").getJSONArray("containers").getJSONObject(0).
                    getJSONObject("resources").getJSONObject("requests").put("cpu",request_cpu);
        }
        if(request_memory!=null && request_memory.length()!=0){
            jsonObject.getJSONObject("spec").getJSONObject("template").
                    getJSONObject("spec").getJSONArray("containers").getJSONObject(0).
                    getJSONObject("resources").getJSONObject("requests").put("memory",request_memory);
        }
        if(limits_cpu!=null && limits_cpu.length()!=0){
            jsonObject.getJSONObject("spec").getJSONObject("template").
                    getJSONObject("spec").getJSONArray("containers").getJSONObject(0).
                    getJSONObject("resources").getJSONObject("limits").put("cpu",limits_cpu);
        }
        if(limits_memory!=null && limits_memory.length()!=0){
            jsonObject.getJSONObject("spec").getJSONArray("containers").getJSONObject(0).
                    getJSONObject("resources").getJSONObject("limits").put("memory",limits_memory);
        }
        String deployment_url = url+"" +
                "apis/extensions/v1beta1/namespaces/"+namespace+"/deployments/";
        String result = HttpConnectedUtils.HTTP_PUT(deployment_url,jsonObject.toString());
        JSONObject retObj = new JSONObject(result);
        String uuid = retObj.getJSONObject("metadata").getString("uid");
        return uuid;
    }
}
