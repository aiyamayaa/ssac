/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl;

import org.json.JSONObject;
import org.opendaylight.feng.ssac.impl.networktopo.NetResource;
import org.opendaylight.feng.ssac.impl.physicalNetwork.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aauconfigreply.rev180606.PacketBniAauConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aaufeaturereply.rev180606.PacketBniAauFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcconfigreply.rev180122.PacketBniDcConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcfeaturereply.rev180122.PacketBniDcFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchconfigreply.rev180606.PacketBniEswitchConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchfeaturereply.rev180606.PacketBniEswitchFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltconfigreply.rev180606.PacketBniOltConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltfeaturereply.rev180606.PacketBniOltFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmconfigreply.rev180606.PacketBniRoadmConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmfeaturereply.rev180606.PacketBniRoadmFeatureReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*
*   ---------------------------------------
*   |   0-15   |   switch | (41,42,43,44) |
*   |   16-30  |   roadm  | (51,52,53,54) |
*   |   31-45  |   aau    | (61,62,63,64) |
*   |   46-55  |   pool   | (71,72,73,74) |
*   |   56-60  |   olt    | (81,82,83,84) |
*    --------------------------------------
* */
public class LinkProperty extends NetResource implements LinkPropertyService{
    private static Logger LOG = LoggerFactory.getLogger(LinkProperty.class);


    @Override
    public String getNetNode() {
        String outString = "";
        String outStringaau = "XXX--AauResource:&";
        String outStringdc = "XXX--ProcessingPoolResource:&";
        outString+="XXX-------------Network Resource---------------------&";
        outString+="XXX--Network Node:&";

        if(!AauResource.isEmpty()){
            outString += "XXX----Aau/OnuNode:";
            for (Short nodeId:AauResource.keySet()
                    ) {
                if(nodeId ==37){

                }else{
                    NodeAau node = AauResource.get(nodeId);
                    outString+=nodeId+",";
                    outStringaau+= "XXX----AauId:"+nodeId+"   TotalAntennas:"+node.getTotalAntennas()
                            +"    ResidualAntennas"+node.getResidualAntennas()+"&";
                }

            }
            outString+="&";
        }
        if(!DcResource.isEmpty()){
            outString += "XXX----ProcessingPool:";
            for (Short nodeId:DcResource.keySet()
                    ) {
                outString+=nodeId+",";
                Procepool node = DcResource.get(nodeId);
                outStringdc += "XXX----ProcePool:"+nodeId+"   TotalCapacity:"+node.getTotalCapacity()+"    ResidualCapacity:"
                        +node.getResidualCapacity()+"    virtualNodeNums:"+node.getVirtualNodeNums()+"&";

            }
            outString+="&";
        }
        if(!OltResource.isEmpty()){
            outString += "XXX----OLT:";
            for (Short nodeId:OltResource.keySet()
                    ) {

                outString+=nodeId+",";
            }
            outString+="&";
        }
        if(!RoadmResource.isEmpty()){
            outString += "XXX-----Roadm:";
            for (Short nodeId:RoadmResource.keySet()
                    ) {
                outString+=nodeId+",";
            }
            outString+="&";
        }
        if(!EswitchResource.isEmpty()){
            outString += "XXX----Eswitch:";
            for (Short nodeId:EswitchResource.keySet()
                    ) {
                outString+=nodeId+",";
            }
            outString+="&";
        }

        outString+=outStringaau;
        outString+=outStringdc;

        return outString;
    }

    @Override
    public String getPath(){
        String outString="";
        outString+="XXX-------------Network Path Resource---------------------:";
        outString+="&";
        if(!NetpathResource.isEmpty()){
            for(Short[] path:NetpathResource){
                outString+="XXX---";
                for(Short i:path){
                    outString+=i;
                    outString+=",";
                }
                outString+="&";
            }

        }

        return outString;
    }
    @Override
    public String getNetLinkresource() {
        String outString = "";
        String oeLink = "";
        String ooLink = "";
        outString+="XXX-------------Network Link Resource---------------------&XXX--LinkId:";
        if(!LinkResource.isEmpty()){
            for (Integer id:LinkResource.keySet()
                    ) {
                outString+=id+",";
            }
            outString+="&";

            for (Map.Entry<Integer, Link> map:LinkResource.entrySet()

                    ) {
                Link link = map.getValue();
                if(link.getStartNode()==26||link.getDestNode()==26){

                }else if(link.getLinkType().equals("oe")){
                    oeLink=oeLink+"XXX--LinkId:"+link.getLinkId()+"    Linkcapacity:"+link.getLinkCapacity()
                            +"    LinkLength:"+link.getLinkLength()+"&";

                }else if(link.getLinkType().equals("oo")){
                    if(link.getStartNode()<=30&&link.getStartNode()>=16){
                        oeLink=oeLink+"XXX--LinkId:"+link.getLinkId()+"    Linkcapacity:"+link.getLinkCapacity()+link.getLinkWavelength()
                                +"    LinkLength:"+link.getLinkLength()+"&";
                    }
                    ooLink=ooLink+"XXX--LinkId:"+link.getLinkId()+"    Linkcapacity:"+link.getLinkCapacity()+link.getLinkWavelength()
                            +"    LinkLength:"+link.getLinkLength()+"&";

                }
            }
            outString+=oeLink;
            outString+=ooLink;
        }

        return outString;
    }

    public void AauHandler(PacketBniAauFeatureReply replyBody){
        LOG.info("---jiaxinTed---AauHandler---start---{}"+replyBody);
        NodeConnectorRef ingress = replyBody.getIngress();
        Short nodeId = Integer_to_Short((replyBody.getAauInfo()>>8) & 0x000000ff) ;
        nodeIngress.put(nodeId,ingress);
        LOG.info("---jiaxinTed---AauHandler------{}");
        LOG.info("---jiaxinTed---AauHandler------{}"+nodeIngress);
        int[] aauCapacity = Data_To_Array_2(replyBody.getAauCapacityInfo());
        Short[] linkinfo = Long_To_Array_4(replyBody.getLinkInfo());
        NodeAau node = new NodeAau(nodeId,linkinfo[3],aauCapacity[1],aauCapacity[0]);


        if(DcResource.containsValue(nodeId)){
            LOG.info("---jiaxin---AauHandler---containsValue---{}"+nodeId);
            AauResource.remove(nodeId);
            LOG.info("---jiaxin---AauHandler---DeleteValue---{}"+nodeId);
            AauResource.put(nodeId,node);
            LOG.info("---jiaxin---AauHandler---AddValue---{}"+nodeId);
        }else{
            AauResource.put(nodeId,node);
            LOG.info("---jiaxin---AauHandler---AddValue---{}"+nodeId);
        }
        LOG.info("Ted"+node.toString());

    }

    public void DcHandler(PacketBniDcFeatureReply replyBody) {
        LOG.info("---jiaxin---DcHandler---start---{}" + replyBody);
        NodeConnectorRef ingress = replyBody.getIngress();
        Short[] dcInfo = Long_To_Array_4(replyBody.getDcInfo());
        Short nodeId = dcInfo[3];
        nodeIngress.put(nodeId, ingress);
        LOG.info("---jiaxin---DcHandler---saving---{}");
        int[] dcResource = Data_To_Array_2(replyBody.getDcResource());
        Procepool node = new Procepool(nodeId, dcInfo[1], dcResource[1], dcResource[0]);

        if(DcResource.containsValue(nodeId)){
            LOG.info("---jiaxin---DcHandler---containsValue---{}"+nodeId);
            DcResource.remove(nodeId);
            LOG.info("---jiaxin---DcHandler---DeleteValue---{}"+nodeId);
            DcResource.put(nodeId,node);
            LOG.info("---jiaxin---DcHandler---AddValue---{}"+nodeId);

        }else{
            DcResource.put(nodeId,node);
            LOG.info("---jiaxin---DcHandler---AddValue---{}"+nodeId);
        }
        LOG.info("Ted"+node.toString());
    }


    public int doubletiInt(Double in){
        return in.intValue();
}

    public void OltHandler(PacketBniOltFeatureReply replyBody){
         LOG.info("---jiaxin---OltHandler---start---{}", replyBody);

         LOG.info("---jiaxin---OltHandler---start---{}");
        NodeConnectorRef ingress = replyBody.getIngress();
        Short nodeId = replyBody.getOltId();
        nodeIngress.put(nodeId,ingress);
        Short[] connectedNodeId = Long_To_Array_4(replyBody.getConnectedNodeId());
        Map<Integer,Short> portLinkResource = new HashMap<>();
        for(Short i=0;i<connectedNodeId.length;i++){
            if(connectedNodeId[i]!=0){
                portLinkResource.put((connectedNodeId[i]*100+nodeId),i);
                portLinkResource.put((nodeId*100+connectedNodeId[i]),i);
                LOG.info("---jiaxin---OltHandler---start---{}");
                if(connectedNodeId[i]<=45&&connectedNodeId[i]>=31){
                    Link link = new Link(connectedNodeId[i],nodeId);
                    link.setLinkLength(doubletiInt(Math.random()*15 +5));
                    putLink(link);

                }
                LOG.info("---jiaxin---OltHandler---putLink---{}");
                if(connectedNodeId[i]>=0&&connectedNodeId[i]<=15){
                    Map<Short,Integer> linkCapacity1 = getLinkCapacity(replyBody.getWavelengthId3(),replyBody.getResidualSlots3());
                    Map<Short,Integer> linkCapacity2 = getLinkCapacity(replyBody.getWavelengthId4(),replyBody.getResidualSlots4());
                    Link link1 = new Link(nodeId,connectedNodeId[i],linkCapacity1);
                    link1.setLinkLength(doubletiInt(Math.random()*15 +5));
                    Link link2 = new Link(nodeId,connectedNodeId[i],linkCapacity2);
                    link2.setLinkLength(doubletiInt(Math.random()*15 +5));
                    putLink(link1);
                    putLink(link2);
                }
                LOG.info("---jiaxin---OltHandler---2---{}");
            }

        }
        NodeOLT node = new NodeOLT(nodeId,portLinkResource);


        if(OltResource.containsValue(nodeId)){
            LOG.info("---jiaxin---OltHandler---containsValue---{}"+nodeId);
            OltResource.remove(nodeId);
            LOG.info("---jiaxin---OltHandler---DeleteValue---{}"+nodeId);
            OltResource.put(nodeId,node);
            LOG.info("---jiaxin---OltHandler---AddValue---{}"+nodeId);

        }else{
            OltResource.put(nodeId,node);
            LOG.info("---jiaxin---OltHandler---AddValue---{}"+nodeId);
        }
        LOG.info("Ted"+node.toString());

    }
    public void putLink(Link link){
        if(LinkResource.containsKey(link.getLinkId())){
                    LOG.info("---jiaxin----putLink---Link has exist in LinkResource--{}" + link.getLinkId());
                    LinkResource.remove(link.getLinkId());

                    LOG.info("---jiaxin----putLink---Link has exist in LinkResource-removeLink-{}" + link.getLinkId());
                    LinkResource.put(link.getLinkId(),link);

                    LOG.info("---jiaxin---putLink----Link has exist in LinkResource-addLink-{}" + link.getLinkId());
                }else{
                    LOG.info("---jiaxin----putLink---Link not exist in LinkResource--{}" + link.getLinkId());
                    LinkResource.put(link.getLinkId(),link);
                }
    }

    public void EswitchHandler(PacketBniEswitchFeatureReply replyBody){
        LOG.info("---jiaxin---EswitchHandler---start---{}"+replyBody);
        NodeConnectorRef ingress = replyBody.getIngress();
        Short nodeId = replyBody.getEswitchId();
        nodeIngress.put(nodeId,ingress);
        Short[] connectedNode = Long_To_Array_4(replyBody.getConnectedNodeId());
        Map<Integer,Short> portLinkResource = new HashMap<>();
        for (Short i = 0;i < connectedNode.length;i++){
            if(connectedNode[i] != 0){
                portLinkResource.put((connectedNode[i]*100+nodeId),i);
                portLinkResource.put((nodeId*100+connectedNode[i]),i);
            }

        }
        LOG.info("---jiaxin---EswitchHandler---portLinkResource---{}"+portLinkResource);
        NodeEswitch node = new NodeEswitch(nodeId,portLinkResource);
        if(EswitchResource.containsKey(nodeId)){
            LOG.info("---jiaxin---EswitchHandler---containsKey---{}" + nodeId);
            EswitchResource.remove(nodeId);
            LOG.info("---jiaxin---EswitchHandler---removeKey---{}" + nodeId);
            EswitchResource.put(nodeId,node);
            LOG.info("---jiaxin---EswitchHandler---putKey---{}" + nodeId);
        }else{
            EswitchResource.put(nodeId,node);
            LOG.info("---jiaxin---EswitchHandler---putKey2---{}" + nodeId);
        }
        //
        //
        //link handler
        for(Short cnode:connectedNode){
            if(46 <= cnode && cnode<= 55){          //pool
                LOG.info("---jiaxin---EswitchHandler----connectedProcessingPool--{}" + cnode);
                Map<Short,Integer> linkCapacity1 = getLinkCapacity(replyBody.getWavelengthId1(),
                        replyBody.getResidualSlots1());
                Map<Short,Integer> linkCapacity2 = getLinkCapacity(replyBody.getWavelengthId2(),
                        replyBody.getResidualSlots2());
                Link link1 = new Link(nodeId,cnode,linkCapacity1);
                link1.setLinkLength(2);
                Link link2 = new Link(cnode,nodeId,linkCapacity2);
                link2.setLinkLength(2);
                if(LinkResource.containsKey(link1.getLinkId())){
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource--{}" + link1.getLinkId());
                    LinkResource.remove(link1.getLinkId());
                    LinkResource.remove(link2.getLinkId());
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource-removeLink-{}" + link1.getLinkId());
                    LinkResource.put(link1.getLinkId(),link1);
                    LinkResource.put(link2.getLinkId(),link2);
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource-addLink-{}" + link1.getLinkId());
                }else{
                    LOG.info("---jiaxin---EswitchHandler----Link not exist in LinkResource--{}" + link1.getLinkId());
                    LinkResource.put(link1.getLinkId(),link1);
                    LinkResource.put(link2.getLinkId(),link2);
                    //ok?
                    LOG.info("---jiaxin---EswitchHandler----Link not exist in LinkResource-addLink-{}" + link1.getLinkId()+link2.getLinkId());
                }

            }else if(cnode >= 16 && cnode <= 30){       //roadm
                LOG.info("---jiaxin---EswitchHandler----connectedRoadm--{}" + cnode);
                Map<Short,Integer> linkCapacity1 = getLinkCapacity(replyBody.getWavelengthId3(),
                        replyBody.getResidualSlots3());
                Map<Short,Integer> linkCapacity2 = getLinkCapacity(replyBody.getWavelengthId4(),
                        replyBody.getResidualSlots4());
                Link link1 = new Link(nodeId,cnode,linkCapacity1);
                link1.setLinkLength(2);
                Link link2 = new Link(cnode,nodeId,linkCapacity2);  //roadm->eswitch
                link2.setLinkType("oo");
                link2.setLinkLength(2);
                if(LinkResource.containsKey(link1.getLinkId())){
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource--{}" + link1.getLinkId());
                    LinkResource.remove(link1.getLinkId());
                    LinkResource.remove(link2.getLinkId());
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource-removeLink-{}" + link1.getLinkId());
                    LinkResource.put(link1.getLinkId(),link1);
                    LinkResource.put(link2.getLinkId(),link2);
                    LOG.info("---jiaxin---EswitchHandler----Link has exist in LinkResource-addLink-{}" + link1.getLinkId());
                }else{
                    LOG.info("---jiaxin---EswitchHandler----Link not exist in LinkResource--{}" + link1.getLinkId());
                    LinkResource.put(link1.getLinkId(),link1);
                    LinkResource.put(link2.getLinkId(),link2);
                    //ok?
                    LOG.info("---jiaxin---EswitchHandler----Link not exist in LinkResource-addLink-{}" + link1.getLinkId()+link2.getLinkId());
                }
            }


        }

        LOG.info("Ted"+node.toString());

    }

    public void RoadmHandler(PacketBniRoadmFeatureReply replyBody){
        LOG.info("---jiaxin---RoadmHandler---start---{}" + replyBody);
        NodeConnectorRef ingress = replyBody.getIngress();
        Short nodeId = replyBody.getRoadmId();
        nodeIngress.put(nodeId,ingress);
        Map<Integer,Short> connectedLink = new HashMap<>();
        Short[] connectedNodeId = Long_To_Array_4(replyBody.getConnectedNodeId());
        for(Short i=0;i<connectedNodeId.length;i++){
            if(connectedNodeId[i]==0){

            }else {
                connectedLink.put((nodeId*100+connectedNodeId[i]),i);
                connectedLink.put((connectedNodeId[i]*100+nodeId),i);
            }

        }
        LOG.info("---jiaxin---RoadmHandler---connectedLink---{}" + connectedLink);
        List<Short> inport1outport1eave = Long_To_List(replyBody.getOutport11Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport1outport1eave----{}" + inport1outport1eave);
        List<Short> inport1outport2eave = Long_To_List(replyBody.getOutport12Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport1outport2eave----{}" + inport1outport2eave);
        List<Short> inport1outport3eave = Long_To_List(replyBody.getOutport13Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport1outport3eave----{}" + inport1outport3eave);
        List<Short> inport1outport4eave = Long_To_List(replyBody.getOutport14Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport1outport4eave----{}" + inport1outport4eave);

        List<Short> inport2outport1eave = Long_To_List(replyBody.getOutport21Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport2outport1eave----{}" + inport2outport1eave);
        List<Short> inport2outport2eave = Long_To_List(replyBody.getOutport22Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport2outport2eave----{}" + inport2outport2eave);
        List<Short> inport2outport3eave = Long_To_List(replyBody.getOutport23Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport2outport3eave----{}" + inport2outport3eave);
        List<Short> inport2outport4eave = Long_To_List(replyBody.getOutport24Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport2outport4eave----{}" + inport2outport4eave);

        List<Short> inport3outport1eave = Long_To_List(replyBody.getOutport31Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport3outport1eave----{}" + inport3outport1eave);
        List<Short> inport3outport2eave = Long_To_List(replyBody.getOutport32Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport3outport2eave----{}" + inport3outport2eave);
        List<Short> inport3outport3eave = Long_To_List(replyBody.getOutport33Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport3outport3eave----{}" + inport3outport3eave);
        List<Short> inport3outport4eave = Long_To_List(replyBody.getOutport34Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport3outport4eave----{}" + inport3outport4eave);

        List<Short> inport4outport1eave = Long_To_List(replyBody.getOutport41Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport4outport1eave----{}" + inport4outport1eave);
        List<Short> inport4outport2eave = Long_To_List(replyBody.getOutport42Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport4outport2eave----{}" +inport4outport2eave );
        List<Short> inport4outport3eave = Long_To_List(replyBody.getOutport43Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport4outport3eave----{}" + inport4outport3eave);
        List<Short> inport4outport4eave = Long_To_List(replyBody.getOutport44Wavelength());
        LOG.info("---jiaxin---RoadmHandler--inport4outport4eave----{}" + inport4outport4eave);
        NodeRoadm node = new NodeRoadm(nodeId,connectedLink,inport1outport1eave,inport1outport2eave,inport1outport3eave,inport1outport4eave,
                inport2outport1eave,inport2outport2eave,inport2outport3eave,inport2outport4eave,
                inport3outport1eave,inport3outport2eave,inport3outport3eave,inport3outport4eave,
                inport4outport1eave,inport4outport2eave,inport4outport3eave,inport4outport4eave);
        if(RoadmResource.containsKey(nodeId)){
            RoadmResource.remove(nodeId);
            RoadmResource.put(nodeId,node);
        }else {
            RoadmResource.put(nodeId,node);
        }
        List<Short> link1wave = new ArrayList<>();
        link1wave.addAll(inport1outport1eave);
        link1wave.addAll(inport2outport1eave);
        link1wave.addAll(inport3outport1eave);
        link1wave.addAll(inport4outport1eave);

        List<Short> link2wave = new ArrayList<>();
        link2wave.addAll(inport1outport2eave);
        link2wave.addAll(inport2outport2eave);
        link2wave.addAll(inport3outport2eave);
        link2wave.addAll(inport4outport2eave);

        List<Short> link3wave = new ArrayList<>();
        link3wave.addAll(inport1outport3eave);
        link3wave.addAll(inport2outport3eave);
        link3wave.addAll(inport3outport3eave);
        link3wave.addAll(inport4outport3eave);

        List<Short> link4wave = new ArrayList<>();
        link4wave.addAll(inport1outport4eave);
        link4wave.addAll(inport2outport4eave);
        link4wave.addAll(inport3outport4eave);
        link4wave.addAll(inport4outport4eave);
        if(nodeId==19){
            if(connectedNodeId[0]!=0){
                    LOG.info("---jiaxin---RoadmHandler--connectedNodeId not null--0--{}" + connectedNodeId[0]);
                    Link link = new Link(nodeId,connectedNodeId[0],link1wave);
                    link.setLinkLength(doubletiInt(Math.random()*20 +10));
                    if(LinkResource.containsKey(link.getLinkId())){
                        LinkResource.remove(link.getLinkId());
                         LinkResource.put(link.getLinkId(),link);
                    }else{
                        LinkResource.put(link.getLinkId(),link);
                    }
                     LOG.info("---jiaxin---RoadmHandler--AddLink----{}"+link.getLinkId());
                }

        }
        for(int i=1;i<connectedNodeId.length;i++){
            if(i==1){
                if(connectedNodeId[i]!=0){
                    LOG.info("---jiaxin---RoadmHandler--connectedNodeId not null--1--{}" + connectedNodeId[i]);
                    Link link = new Link(nodeId,connectedNodeId[i],link2wave);
                    link.setLinkLength(doubletiInt(Math.random()*20 +10));
                    if(LinkResource.containsKey(link.getLinkId())){
                        LinkResource.remove(link.getLinkId());
                         LinkResource.put(link.getLinkId(),link);
                    }else{
                        LinkResource.put(link.getLinkId(),link);
                    }
                     LOG.info("---jiaxin---RoadmHandler--AddLink----{}"+link.getLinkId());
                }

            }else if(i==2){
                if(connectedNodeId[i]!=0){
                    LOG.info("---jiaxin---RoadmHandler--connectedNodeId not null--2--{}" + connectedNodeId[i]);
                    Link link = new Link(nodeId,connectedNodeId[i],link3wave);
                    link.setLinkLength(doubletiInt(Math.random()*20 +10));
                    if(LinkResource.containsKey(link.getLinkId())){
                        LinkResource.remove(link.getLinkId());
                         LinkResource.put(link.getLinkId(),link);
                    }else{
                        LinkResource.put(link.getLinkId(),link);
                    }
                    LOG.info("---jiaxin---RoadmHandler--AddLink----{}"+link.getLinkId());
                }

            }else if(i==3){

                if(connectedNodeId[i]!=0){
                    LOG.info("---jiaxin---RoadmHandler--connectedNodeId not null--3--{}" + connectedNodeId[i]);
                    Link link = new Link(nodeId,connectedNodeId[i],link4wave);
                    link.setLinkLength(doubletiInt(Math.random()*20 +10));
                    if(LinkResource.containsKey(link.getLinkId())){
                        LinkResource.remove(link.getLinkId());
                         LinkResource.put(link.getLinkId(),link);
                    }else{
                        LinkResource.put(link.getLinkId(),link);
                    }
                    LOG.info("---jiaxin---RoadmHandler--AddLink----{}"+link.getLinkId());
                }
            }

        }
        LOG.info("Ted"+node.toString());
    }

    public void AauReplyHandler(PacketBniAauConfigReply replyBody){
        Long status = replyBody.getResult();

        LOG.info("--jiaxinTed--AauReplyHandler---CondigReplystatus----{}"+status);
    }
    public void DcReplyHandler(PacketBniDcConfigReply replyBody){
        Long status = replyBody.getResult();
        LOG.info("--jiaxinTed--DcReplyHandler---CondigReplystatus----{}"+status);

    }
    public void OltReplyHandler(PacketBniOltConfigReply replyBody){
        Long status = replyBody.getResult();
        LOG.info("--jiaxinTed--OltReplyHandler---CondigReplystatus----{}"+status);
    }
    public void EswitchReplyHandler(PacketBniEswitchConfigReply replyBody){
        Long status = replyBody.getResult();
        LOG.info("--jiaxinTed--EswitchReplyHandler---CondigReplystatus----{}"+status);
    }
    public void RoadmReplyHandler(PacketBniRoadmConfigReply replyBody){
        Long status = replyBody.getResult();
        LOG.info("--jiaxinTed--RoadmReplyHandler---CondigReplystatus----{}"+status);
    }


    @Override
    public Map<Short,NodeConnectorRef> getNodeMapIgress(){
        LOG.info("-----Tedget----getNodeMapIgress-----{}");
        return this.nodeIngress;


    }

    @Override
    public List<String> getAauResource() {

        List<String> output  = new ArrayList<>();
//        for(Short nodeAau:AauResource.keySet()){
////            JSONObject object = new JSONObject();
//            output.add(AauResource.get(nodeAau).toString());
//        }
//
        for (Map.Entry<Short, NodeAau> m: AauResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
                LOG.info("-----SSAC----getAauResource-----{}"+tmpstr);
        }

        LOG.info("-----Tedget----getAauResource-----{}"+output);
        return output;
    }

    @Override
    public List<String> getDcResource() {
        List<String> output = new ArrayList<>();
//        for(Short nodeDc:DcResource.keySet()){
//            output.add(DcResource.get(nodeDc).toString());
//        }
        for (Map.Entry<Short, Procepool> m: DcResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
            LOG.info("-----SSAC----getDcResource-----{}"+tmpstr);
        }
        LOG.info("-----Tedget-----getDcResource----{}"+output);
        return output;
    }

    @Override
    public List<String> getEswitchResource() {
        List<String> output = new ArrayList<>();
//        for(Short key:EswitchResource.keySet()){
//            output.add(EswitchResource.get(key).toString());
//        }
        for (Map.Entry<Short, NodeEswitch> m: EswitchResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
            LOG.info("-----SSAC----getEswitchResource-----{}"+tmpstr);
        }
        LOG.info("-----Tedget----getEswitchResource-----{}"+output);
        return output;
    }

    @Override
    public List<String> getRoadmResource() {
        List<String> output = new ArrayList<>();
//        for(Short key:RoadmResource.keySet()){
//            output.add(RoadmResource.get(key).toString());
//        }
         for (Map.Entry<Short, NodeRoadm> m: RoadmResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
             LOG.info("-----SSAC----getRoadmResource-----{}"+tmpstr);
        }
        LOG.info("-----Tedget----getRoadmResource-----{}"+output);
        return output;
    }

    @Override
    public List<String> getOltResource() {
        List<String> output = new ArrayList<>();
//        for(Short key:OltResource.keySet()){
//            output.add(OltResource.get(key).toString());
//        }
         for (Map.Entry<Short, NodeOLT> m: OltResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
             LOG.info("-----SSAC----getOltResource-----{}"+tmpstr);
        }
        LOG.info("-----Tedget----getOltResource-----{}"+output);
        return output;


    }

    @Override
    public List<String> getLinkResource() {
        List<String> output = new ArrayList<>();
//        for(Integer key:LinkResource.keySet()){
//            output.add(LinkResource.get(key).toString());
//        }
        for (Map.Entry<Integer,Link> m: LinkResource.entrySet()) {
            JSONObject tmpObject = new JSONObject(m.getValue());
            String tmpstr = tmpObject.toString();
            output.add(tmpstr);
            LOG.info("-----SSAC----getLinkResource-----{}"+tmpstr);
        }
        LOG.info("-----Tedget----getLinkResource-----{}"+output);
        return output;

    }

    public List<Short> Long_To_List(Long input){
        List<Short> output =  new ArrayList<>();
        if(input ==0){

        }else {
            Short[] outArray = Long_To_Array_4(input);
            for (Short i : outArray) {
                if (i != 0) {
                    output.add(i);
                }
            }
        }
        return output;
    }

    public Short[] BigInteger_to_ShortArray(BigInteger input){
        Short[] output = new Short[8];
        BigInteger num = new BigInteger("255");
        for(int i=0;i<8;i++){
            output[i] = (input.shiftRight(i*8)).and(num).shortValue();
        }
        return output;
    }


    public Map<Short,Integer> getLinkCapacity(BigInteger inputWavelength, BigInteger inputSlot){
        Map<Short,Integer> outputLinkCapacity = new HashMap<>();
        if(inputWavelength==new BigInteger("0")&&inputSlot==new BigInteger("0")){

        }else {
            Short[] wavelength = BigInteger_to_ShortArray(inputWavelength);
            Short[] slot = BigInteger_to_ShortArray(inputSlot);
            for (int i = 0; i < 8; i++) {
                if (wavelength[i] != 0) {
                    outputLinkCapacity.put(wavelength[i], slot[i].intValue());
                } else {
                    LOG.info("-----TedLinkproperty----getLinkCapacity-wavelength is null--" + i);
                }
            }
        }
        return outputLinkCapacity;
    }

    public short Long_to_Short(Long input){
        Short output;
        output = input.shortValue();
        return output;
    }
    public int Long_to_Int(Long input){
        int output;
        output = input.intValue();
        return output;
    }

    public short Integer_to_Short(Integer input){
        Short output;
        output = input.shortValue();
        return output;
    }
    //ok
    public int[] Data_To_Array_2(Long input){
//        LOG.info("Data_To_Array-{}",input);
        int[] output = new int[2];
        output[0] = Long_to_Int(input & 0x0000ffff);
        output[1] = Long_to_Int((input >> 16) & 0x0000ffff);

        return output;
    }
    //ok
    public Short[] Long_To_Array_4(Long input){
//        LOG.info("Data_To_Array-{}",input);
        Short[] output = new Short[4];
        output[0] = Long_to_Short(input & 0x000000ff);
        output[1] = Long_to_Short((input >> 8) & 0x000000ff);
        output[2] = Long_to_Short((input >> 16) & 0x000000ff);
        output[3] = Long_to_Short((input >> 24) & 0x000000ff);
        return output;
    }

    public List Data_to_List(Long input){
//        LOG.info("---jiaxin-Data_To_List-start");

        List<Short> output = new ArrayList<>();
        if((input&0x000f)!=0) {
            output.add(Long_to_Short(input & 0x000f));
        }
        if(((input >> 4) & 0x000f)!= 0){
            output.add(Long_to_Short((input >> 4) & 0x000f));
        }
        if(((input>>8)&0x000f)!=0) {
            output.add(Long_to_Short((input >> 8) & 0x000f));
        }
        if(((input >> 12) & 0x000f)!=0) {
            output.add(Long_to_Short((input >> 12) & 0x000f));
        }
        return output;

    }


    public static void setLinkResource(Map<Integer, Link> linkResource) {
        LinkResource = linkResource;
    }

    public static void setAauResource(Map<Short, NodeAau> aauResource) {
        AauResource = aauResource;
    }

    public static void setDcResource(Map<Short, Procepool> dcResource) {
        DcResource = dcResource;
    }

    public static void setOltResource(Map<Short, NodeOLT> oltResource) {
        OltResource = oltResource;
    }

    public static void setRoadmResource(Map<Short, NodeRoadm> roadmResource) {
        RoadmResource = roadmResource;
    }

    public static void setEswitchResource(Map<Short, NodeEswitch> eswitchResource) {
        EswitchResource = eswitchResource;
    }

}
