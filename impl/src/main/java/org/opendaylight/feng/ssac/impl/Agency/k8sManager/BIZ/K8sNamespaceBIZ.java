/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.CommonUtils;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.HttpConnectedUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class K8sNamespaceBIZ {


    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(K8sNamespaceBIZ.class);
    String endpoint_namespaces = "http://10.112.254.154:8080/api/v1/namespaces/";
    public List<String> getNamespaceList(){

        List<String> namespaceList = new ArrayList<String>();
        String result = HttpConnectedUtils.HTTP_GET(endpoint_namespaces);
        JSONObject object = new JSONObject(result);
        JSONArray NSarray = object.getJSONArray("items");
        for(int i = 0; i<NSarray.length(); i++){
            JSONObject tmp = NSarray.getJSONObject(i);
            namespaceList.add(tmp.getJSONObject("metadata").getString("name"));
        }
        return namespaceList;

    }
    public Map<String, String> CreateNamespaces(String filePath,Map<String, String> config){

        Map<String, String> resultMap = new HashMap<String, String>();
        String tmpJson = "";
        JSONObject jsonObject = null;
        String name = config.get("name");
        try {
//            tmpJson = CommonUtils.convertYamlToJson(filePath);
            jsonObject=new JSONObject(tmpJson);
            jsonObject.getJSONObject("metadata").put("name",name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = HttpConnectedUtils.HTTP_POST(endpoint_namespaces, jsonObject.toString());
        JSONObject resultMapper = new JSONObject(result);
        String result_name = resultMapper.getJSONObject("metadata").getString("name");
        String uuid_ns = resultMapper.getJSONObject("metadata").getString("uid");
        resultMap.put(result_name, uuid_ns);
        return resultMap;
    }

    public boolean deteleNamespace(String namespace){

        List<String> allNamespaces = getNamespaceList();
        if(allNamespaces.contains(namespace)){
            endpoint_namespaces +=namespace+"/";
            String result = HttpConnectedUtils.HTTP_DELETE(endpoint_namespaces);
            JSONObject object = new JSONObject(result);
            String status = object.getJSONObject("status").getString("phase");
            LOG.info("-------namespace:",namespace,"------status:  ",status,"------");
            return true;
        }else{
            LOG.info("---cannot found namesapce: "+namespace+"----");
            System.out.println("---cannot found namesapce: "+namespace+"----");
            return false;
        }
    }

}

