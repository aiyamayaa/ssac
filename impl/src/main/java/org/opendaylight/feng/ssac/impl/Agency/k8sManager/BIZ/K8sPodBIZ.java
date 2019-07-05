/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ;

import org.json.JSONObject;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.domain.Params;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.domain.ResourceType;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.CommonUtils;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.ConfigUtils;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.HttpConnectedUtils;
import java.util.Map;

public class K8sPodBIZ {

    public String createPod(Map<String, String> config){

        String tmpJson = "";
        JSONObject jsonObject = null;
        String url_type =  config.get("url");
        String url =getUrl(url_type);
        String jobType = config.get("jobType");
        System.out.println("URL: " +url);
//        String name = config.get("name");
//        String selector = config.get("selector");
//        String namespace = config.get("namespace");
//        String request_cpu = config.get("request_cpu");
//        String request_memory = config.get("request_memory");
//        String limits_cpu = config.get("limits_cpu");
//        String limits_memory = config.get("limits_memory");
        String filePath = getJobType(jobType);
        System.out.println("filepath"+filePath);
//        tmpJson = CommonUtils.convertYamlToJson(filePath);
        jsonObject = new JSONObject(tmpJson);
//        jsonObject.getJSONObject("metadata").put("name", name);
//        jsonObject.getJSONObject("spec").getJSONObject("nodeSelector").put("kubernetes.io/hostname",selector);
//        jsonObject.getJSONObject("spec").getJSONArray("containers").getJSONObject(0).getJSONObject("resources").getJSONObject("limits").put("cpu",limits_cpu);
//        jsonObject.getJSONObject("spec").getJSONArray("containers").getJSONObject(0).getJSONObject("resources").getJSONObject("limits").put("memory",limits_memory);
//        jsonObject.getJSONObject("spec").getJSONArray("containers").getJSONObject(0).getJSONObject("resources").getJSONObject("requests").put("cpu",request_cpu);
//        jsonObject.getJSONObject("spec").getJSONArray("containers").getJSONObject(0).getJSONObject("resources").getJSONObject("requests").put("memory",request_memory);

//        Params params = new Params();
//        params.setResourceType(ResourceType.PODS);
//        params.setNamespace(namespace);
        String result = HttpConnectedUtils.HTTP_POST(url,jsonObject.toString());
        JSONObject retObj = new JSONObject(result);
        String uuid = retObj.getJSONObject("metadata").getString("uid");

        return uuid;
    }

    public boolean deletePod(String namespace, String name){

        Params params = new Params();
        params.setResourceType(ResourceType.PODS);
        params.setNamespace(namespace);
        params.setName(name);
        String result = HttpConnectedUtils.HTTP_DELETE(params.buildPath());
        if(result!=null){
            return true;
        }
        return false;
    }
    public String getUrl(String type) {

        String endpoint = "";
        switch (type) {
            case "1":
                endpoint = "http://10.112.254.154:8080/api/v1/namespaces/cf/pods/";
                break;
            case "2":
                endpoint = "http://10.112.244.94:8080/api/v1/namespaces/cf/pods/";
                break;
            case "3":
                endpoint = "http://10.112.49.140:8080/api/v1/namespaces/cf/pods/";
                break;
            default:
                endpoint = "";
        }
        return endpoint;
    }

    public String getJobType(String jobtype){
        String path = "";
        switch(jobtype){
            case "client1":
                path= ConfigUtils.CLIENT1;
                break;
            case "client2":
                path = ConfigUtils.CLIENT2;
                break;
            case "server":
                path = ConfigUtils.SERVER;
                break;
        }
        return path;
    }
}
