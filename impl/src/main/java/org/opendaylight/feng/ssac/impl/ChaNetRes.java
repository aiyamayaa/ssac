/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl;

import org.opendaylight.feng.ssac.impl.networkSliceData.NetworkSlice;
import org.opendaylight.feng.ssac.impl.networktopo.NetResource;
import org.opendaylight.feng.ssac.impl.physicalNetwork.Link;
import org.opendaylight.feng.ssac.impl.physicalNetwork.NodeAau;
import org.opendaylight.feng.ssac.impl.physicalNetwork.NodeRoadm;
import org.opendaylight.feng.ssac.impl.physicalNetwork.Procepool;
import org.opendaylight.feng.ssac.impl.virtualNetwork.nodeAAuConfig;
import org.opendaylight.feng.ssac.impl.virtualNetwork.nodeDcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChaNetRes extends NetResource{

    private static final Logger LOG = LoggerFactory.getLogger(ChaNetRes.class);
    public static Map<Short,NetworkSlice> netSliceData;
    //flag
    //1 create
    //2 delete
    //3 add
    //4 reduce

    //--------------------------------------------------------------------------------------------------------------------//
//change net statement
//--------------------------------------------------------------------------------------------------------------------//

    public  void changNodeAau(Short flag, nodeAAuConfig node){

        changNodeAau(flag,node.getSliceId(),node.getvAauId(),node.getCapacity(),node.getAauID());
    }
    public void changNodeAau(Short flag, Short SliceId, Short vAauId, Integer antenna, Short aauId){


            if(AauResource.containsKey(aauId)){
                if(flag == 1 || flag == 3) {
                    LOG.info("----sliceMapping---reducing resource-------------{}"+aauId);
                    NodeAau node = AauResource.get(aauId);
                    int aauAntenna = node.getResidualAntennas();
                    aauAntenna = aauAntenna - antenna;
                    node.setResidualAntennas(aauAntenna);
                    AauResource.replace(aauId, node);
                }else if(flag == 2 || flag == 4){
                    LOG.info("----sliceMapping---adding resource-------------{}"+aauId);
                    NodeAau node = AauResource.get(aauId);
                    int aauAntenna = node.getResidualAntennas();
                    aauAntenna = aauAntenna + antenna;
                    node.setResidualAntennas(aauAntenna);
                    AauResource.replace(aauId,node);
                }
            }else {
                LOG.info("----sliceMapping---no such Aau-------------{}"+aauId);
            }

        //------------------------------------------------------------------------------------------------//
        //save the slice
        //------------------------------------------------------------------------------------------------//
//        if (netSliceData.containsKey(SliceId)){
//                NetworkSlice networkSlice = netSliceData.get(SliceId);
//                networkSlice.addAau(aauId);
//                networkSlice.addNode(aauId);
//                netSliceData.replace(SliceId,networkSlice);
//        }else {
//            NetworkSlice networkSlice = new NetworkSlice(SliceId);
//            networkSlice.addAau(aauId);
//            networkSlice.addNode(aauId);
//            netSliceData.put(SliceId,networkSlice);
//        }
    }
    public void changeNodeDc(Short flag,nodeDcConfig node){
        changNodeDc(flag,node.getSliceId(),node.getvDcId(),node.getDcId(),node.getvNodeCapacity().intValue(),
               0,node.getLightPathCapacity1());
    }
    public void changNodeDc(Short flag, Short SliceID, Short DuID, Short DcID, Integer capacity, Integer LinkId,
                            Map<Short, Integer> vlinkCapacity){
        if(DcResource.containsKey(DcID)){
            LOG.info("----sliceMapping---Dc-------------{}");
            if(flag == 1|| flag == 3){
                LOG.info("----sliceMapping---reducing resource-------------{}"+DcID);
                Procepool node = DcResource.get(DcID);
                int residualcapacity = node.getResidualCapacity();
                Short nodeNums  = node.getVirtualNodeNums();
                nodeNums++;
                residualcapacity -= capacity;
                node.setResidualCapacity(residualcapacity);
                node.setVirtualNodeNums(nodeNums);
                DcResource.replace(DcID,node);
            } else if(flag ==2 || flag == 4){
                LOG.info("----sliceMapping---adding resource-------------{}"+DcID);
                Procepool node = DcResource.get(DcID);
                int residualcapacity = node.getResidualCapacity();
                Short nodeNums  = node.getVirtualNodeNums();
                nodeNums--;
                residualcapacity += capacity;
                node.setResidualCapacity(residualcapacity);
                node.setVirtualNodeNums(nodeNums);
                DcResource.replace(DcID,node);
            }
        }else {
            LOG.info("----sliceMapping---no such Dc-------------{}"+DcID);
        }



    }
    public void changeOoLink(Short flag,Short startNode,Short destNode,Short wave){
        LOG.info("---------replacelinklink------sta----");

            linkChangeWave(flag,startNode,destNode,wave);

    }
    public void  changeNetPathResource(Short flag ,List<Short[]> paths){
        try{
            if(flag==1||flag==3){
                NetpathResource.removeAll(paths);
            }else if(flag ==2||flag==4){
                NetpathResource.addAll(paths);
            }
        }catch (Exception e){

        }



    }

    public void changeNodeRoadm(Short nodeId, List<Short> outport11_Wavelength,
                                List<Short> outport12_Wavelength, List<Short> outport13_Wavelength, List<Short> outport14_Wavelength,
                                List<Short> outport21_Wavelength, List<Short> outport22_Wavelength, List<Short> outport23_Wavelength,
                                List<Short> outport24_Wavelength, List<Short> outport31_Wavelength, List<Short> outport32_Wavelength,
                                List<Short> outport33_Wavelength, List<Short> outport34_Wavelength, List<Short> outport41_Wavelength,
                                List<Short> outport42_Wavelength, List<Short> outport43_Wavelength, List<Short> outport44_Wavelength) {

        if (RoadmResource.containsKey(nodeId)) {
            NodeRoadm node = RoadmResource.get(nodeId);
            node.setInport1outport1wave(outport11_Wavelength);
            node.setInport1outport2wave(outport12_Wavelength);
            node.setInport1outport3wave(outport13_Wavelength);
            node.setInport1outport4wave(outport14_Wavelength);
            node.setInport2outport1wave(outport21_Wavelength);
            node.setInport2outport2wave(outport22_Wavelength);
            node.setInport2outport3wave(outport23_Wavelength);
            node.setInport2outport4wave(outport24_Wavelength);
            node.setInport3outport1wave(outport31_Wavelength);
            node.setInport3outport2wave(outport32_Wavelength);
            node.setInport3outport3wave(outport33_Wavelength);
            node.setInport3outport4wave(outport34_Wavelength);
            node.setInport4outport1wave(outport41_Wavelength);
            node.setInport4outport2wave(outport42_Wavelength);
            node.setInport4outport3wave(outport43_Wavelength);
            node.setInport4outport4wave(outport44_Wavelength);
            LOG.info("---------replacelinkroadm----------");
            RoadmResource.replace(nodeId,node);
//

        }

    }


    public void changeLink(Short flag,Link link){

        if(link.getLinkType().equals("oe")){
            changeLink(flag,(short)0,link.getLinkId(),link.getLinkCapacity(),null);
        }
        if(link.getLinkType().equals("oo")){
            changeLink(flag,(short)0,link.getLinkId(),null,link.getLinkWavelength());
        }


    }



    public void changeLink(Short flag, Short SliceID, Integer LinkId, Map<Short, Integer> vlinkCapacity,List<Short> linkWavelength){

        if(LinkId != null || LinkId !=0){
            if(LinkResource.containsKey(LinkId)){
                Link link = LinkResource.get(LinkId);

                if(linkWavelength==null){

                    if(flag==1||flag==3){
                        link.deleteLinkCapacity(vlinkCapacity);
                    }else if(flag==4||flag==2){
                        link.addLinkCapacity(vlinkCapacity);
                    }

                }
                if(vlinkCapacity==null){

                    if(flag==1||flag==3){
                        link.addWaves(linkWavelength);
                    }else if(flag==4||flag==2){
                        link.deleteWaves(linkWavelength);
                    }
                }
                LinkResource.replace(LinkId,link);

            }
        }


    }


}
