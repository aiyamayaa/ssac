/*
 * Copyright © 2017 BNI, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.networktopo;

import org.opendaylight.feng.ssac.impl.physicalNetwork.*;
import sun.java2d.loops.ProcessPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Tools {
    /*
       link: startnode destnode linkid capacity
        2 3 oo 12,23,43,12
        2 1 4 oe 12,34,21,43,45,45,65,55
        2 4 6 ee
        node: nodeId nodeType
        1 aau 3,200,200
        2 switch 3212,0,3454,1
        3 olt 3212,0,3454,1
        4 pool 300,300

    */
    /*
    enum nodetype {
        aau,pool,olt,eswitch,roadm
    }
    public EnumMap<nodetype, Object> getNetworkNodes(String fileAddress){

        EnumMap<nodetype,Object> outNode = new EnumMap<nodetype, Object>(nodetype.class);

        Map<Integer,NodeAau> aauResource = new HashMap<>();
        Map<Integer,Procepool> poolResource = new HashMap<>();
        Map<Integer,NodeEswitch> eswitchResource = new HashMap<>();
        Map<Integer,NodeOLT> oltResource = new HashMap<>();
        Map<Integer,NodeRoadm> roadmResource = new HashMap<>();

        List<String> nodefile = getFile(fileAddress);


        outNode.put(nodetype.aau,aauResource);
        outNode.put(nodetype.pool,poolResource);
        outNode.put(nodetype.eswitch,eswitchResource);
        outNode.put(nodetype.olt,oltResource);
        outNode.put(nodetype.roadm,roadmResource);
        return outNode;
    }
    public Map<Integer,? extends Node> getNetworkNode(String fileAddress){
        Map<Integer,NodeAau> netAau = new HashMap<>();
        List<String> nodefile = getFile(fileAddress);

        return  netAau;
    }
    */

    /*
    * finished test
    * */
    /*
    public Map<Integer, Link> getNetworkLink(String fileAddress){
        Map<Integer,Link> networkLink = new HashMap<>();
        List<String> linkfile = getFile(fileAddress);
        for (String links:linkfile
             ) {
            String[] str = links.split(" ");
            Link link = new Link(Short.parseShort(str[0]),Short.parseShort(str[1]),Integer.parseInt(str[2]),
                    str[3]);

            switch (str[3]){
                case "oo":
                    String capacity = str[4];
                    String[] oocapacity = capacity.split(",");
                    List<Short> oocapa = new ArrayList<>();
                    for (String capa:oocapacity
                         ) {
                        oocapa.add(Short.parseShort(capa));
                    }
                    link.setLinkWavelength(oocapa);
                    break;
                case "oe":
                    String capacity1 = str[4];
                    String[] oecapacity = capacity1.split(",");
                    Map<Short,Integer> oecapa = new HashMap<>();
                    for(int i=0;i<oecapacity.length;i++){
                        oecapa.put(Short.parseShort(oecapacity[i]),Integer.parseInt(oecapacity[i+1]));
                        i++;
                    }
                    link.setLinkCapacity(oecapa);
                    break;
                case "ee":
                    break;
                default:
                    break;
            }

            networkLink.put(link.getLinkId(),link);
        }
        return networkLink;
    }
    /*
    * finished test
    * */
    /*
    public  List<String> getFile(String fileAddress){
        List<String> outfile = new ArrayList<>();
        File file = new File(fileAddress);
        if(file.isFile() && file.exists()){
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = bufferedReader.readLine())!=null){
                    outfile.add(line);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return outfile;
    }
    */
}
