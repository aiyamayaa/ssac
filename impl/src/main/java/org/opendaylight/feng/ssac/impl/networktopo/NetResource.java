/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.networktopo;

import org.opendaylight.SliceMap.networkSlice.SliceMap;
import org.opendaylight.feng.ssac.impl.physicalNetwork.*;
import org.opendaylight.feng.ssac.impl.virtualNetwork.NetSlice;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetResource {
    protected static Map<Short,NodeConnectorRef> nodeIngress = new HashMap<>();



    protected static Map<Short,NodeAau> AauResource = new HashMap<>();
    protected static Map<Short,Procepool> DcResource = new HashMap<>();
    protected static Map<Short,NodeOLT> OltResource = new HashMap<>();
    protected static Map<Short,NodeRoadm> RoadmResource = new HashMap<>();
    protected static Map<Short,NodeEswitch> EswitchResource = new HashMap<>();


    protected static List<Short[]> NetpathResource = new ArrayList<>();
    protected static Map<Integer,Link> LinkResource = new HashMap<>();


    public static Map<Short, NetSlice> sliceMap = new HashMap<>();           //sliceId NetSlice

    public void linkChangeWave(Short flag,Short startNode,Short destNode,Short wave){
        Integer linkId=startNode*100+destNode;
        //1-add,2-remove

        if(LinkResource.containsKey(linkId)){
            Link link =LinkResource.get(linkId);
            List linkWavelength=link.getLinkWavelength();
            if (flag==1||flag==3){
                if(!linkWavelength.contains(wave)){
                    linkWavelength.add(wave);
                    link.setLinkWavelength(linkWavelength);
                    link.setLinkType("oo");
                    LinkResource.replace(linkId,link);
                }
            }else if (flag==2||flag==4){
                if(linkWavelength.contains(wave)){
                    linkWavelength.remove(wave);
                    link.setLinkWavelength(linkWavelength);
                    link.setLinkType("oo");
                    LinkResource.replace(linkId,link);
                }
            }

        }
    }

    public static void savePath( Short[] path) {
        try{
            NetpathResource.add(path);
        }catch (Exception e){

        }

    }

    public void saveSliceLinkWavelength(Short sliceId, Short startNode, Short destNode, Short wave){
        NetSlice slice;
        if(sliceMap.containsKey(sliceId)){
            slice = sliceMap.get(sliceId);
        }else{
             slice=new NetSlice();
        }
        slice.setSliceLinkResource(startNode,destNode,wave);
        sliceMap.replace(sliceId,slice);
    }
    public void saveSlicePath(Short sliceId,Short[] pathResource ){
        NetSlice slice;
        if(sliceMap.containsKey(sliceId)){
            slice = sliceMap.get(sliceId);
        }else{
             slice=new NetSlice();
        }
        slice.setSlicePathResource(pathResource);
        sliceMap.replace(sliceId,slice);
    }

    public void deleteSliceLinkWavelength(Short sliceId){

        if(sliceMap.containsKey(sliceId)){
            NetSlice  slice = sliceMap.get(sliceId);

        }

    }
}
