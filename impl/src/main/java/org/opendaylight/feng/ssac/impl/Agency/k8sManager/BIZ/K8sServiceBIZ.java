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
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.domain.Params;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.domain.ResourceType;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.CommonUtils;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.HttpConnectedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class K8sServiceBIZ {

    /**
     * query all services
     * if namespace existed and namespace not equal null
     * 		query soecified ${namesapce} all services
     * @param namespace
     * @return
     */
    public List<String> getAllServices(String namespace){
        Params params = new Params();
        params.setResourceType(ResourceType.SERVICES);

        List<String> serviceNameList = new ArrayList<String>();
        String result = HttpConnectedUtils.HTTP_GET(params.buildPath());
        JSONObject object = new JSONObject(result);
        JSONArray tmpArray = object.getJSONArray("items");
        for(int i = 0; i<tmpArray.length(); i++){
            serviceNameList.add(tmpArray.getJSONObject(i).
                    getJSONObject("metadata").getString("name"));
        }
        return serviceNameList;
    }

    public Map<String, String> createServices(String namespace, String filePath){
        Params params = new Params();
        if(null!= namespace && namespace!=""){
            params.setNamespace(namespace);
        }
        String serviceStr = "";
        String result = "";
        Map<String, String> resultMap = new HashMap<String, String>();
        String tmpJson = "";
        JSONObject jsonObject = null;
        try {
//            tmpJson = CommonUtils.convertYamlToJson(filePath);
            result = HttpConnectedUtils.HTTP_POST(params.buildPath(), serviceStr);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(result);

        return null;
    }

}

