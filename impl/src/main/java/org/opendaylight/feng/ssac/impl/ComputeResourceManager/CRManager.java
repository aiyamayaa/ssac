/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.ComputeResourceManager;

import org.json.JSONObject;
import org.opendaylight.feng.ssac.impl.Agency.OpenstackAgency;
import org.opendaylight.feng.ssac.impl.Agency.OsmAgency;
import org.opendaylight.feng.ssac.impl.TransportResourceManager.SendPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CRManager {
    private static final Logger LOG = LoggerFactory.getLogger(SendPacket.class);

    OsmAgency osmAgency = new OsmAgency(23456,"10.112.197.221");
    OpenstackAgency connected = new OpenstackAgency();

    public void configOsm(String cmd){
        Thread t = new Thread(()->{
            osmAgency.getResult(cmd);
        });
        t.start();
    }



    public Boolean deleteDu(Map<Short,String> duResults){

        if(duResults!=null){
            for(Map.Entry<Short, String> m :duResults.entrySet()){
                connected.delete(m.getValue());
            }
            return true;
        }else{
            LOG.info("------deleteSliceError-------no-duResults--------------");
            return false;
        }
    }


    public Boolean deleteCu(Map<Short,String> cuResults){
        //-----------cuilu---------
        if(cuResults!=null){
            for(Map.Entry<Short, String> m :cuResults.entrySet()){
                connected.delete(m.getValue());
            }
            return true;
        }else{
            LOG.info("------deleteSliceError-------no-cuResults--------------");
            return false;
        }
    }

    public String createDuCu(Short id,String type){
        String result = connected.createVM(type+id);
        JSONObject object = new JSONObject(result);
        JSONObject tmp = object.getJSONObject("output");
        String duId = tmp.getString("result");
        System.out.println("++++=Du++++++"+duId);
        LOG.info("------SendPacket-------sendDuConfig---OpenstackAgency------");
        return duId;
    }
}
