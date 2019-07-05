/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.southboundInterface;


import org.opendaylight.feng.ssac.impl.ChaNetRes;
import org.opendaylight.feng.ssac.impl.ComputeResourceManager.CRManager;
import org.opendaylight.feng.ssac.impl.TransportResourceManager.InstanceIdentifierUtils;
import org.opendaylight.feng.ssac.impl.TransportResourceManager.TrConfig;
import org.opendaylight.feng.ssac.impl.networkSliceData.NetworkSlice;
import org.opendaylight.feng.ssac.impl.networktopo.NetResource;
import org.opendaylight.feng.ssac.impl.physicalNetwork.*;
import org.opendaylight.feng.ssac.impl.virtualNetwork.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setroadmconfig.rev180606.PacketBniSetRoadmConfigInputBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SendPacket extends NetResource implements SendPacketService {
    //1 for establish / 2 for delete/ 3for add / 4for reduce


    TrConfig trConfig = new TrConfig();

    private static final Logger LOG = LoggerFactory.getLogger(SendPacket.class);

    CRManager manager = new CRManager();
    LinkPropertyService linkPropertyService = new LinkProperty();
    Long zero =new Long(0);



    @Override
    public void deleteSlice(Short sliceId) {
        if(sliceMap.containsKey(sliceId)){
            NetSlice slice =sliceMap.get(sliceId);

            Map<Short,String> duResults = slice.getDuResults();
            Map<Short,String> cuResults = slice.getCuResults();

            manager.deleteCu(cuResults);
            manager.deleteDu(duResults);

            ChaNetRes chaNetRes = new ChaNetRes();
            List<nodeDcConfig> dcs = slice.getSliceDc();
            if(dcs!=null){
                for(nodeDcConfig dc:dcs){
                    chaNetRes.changeNodeDc((short)2,dc);
                }
            }else {
                LOG.info("------deleteSliceError-------no--dcs-------------");
            }


            //aau config
            List<nodeAAuConfig> aaus = slice.getSliceAau();
            if(aaus!=null){
                for(nodeAAuConfig aau:aaus){
                    trConfig.SendAauConfig(aau);
                    chaNetRes.changNodeAau((short)2,aau);
                }

            }else {
                 LOG.info("------deleteSliceError-------no--aaus-------------");
            }

            //olt config
            nodeOltConfig olt = slice.getSliceOlt();
            if(olt!=null){
                Map<Short,Short> lightPathCapacityUp = olt.getLightPathCapacityUp();
                Map<Short,Short> lightPathCapacityDown = olt.getLightPathCapacityDown();
                if(lightPathCapacityUp!=null){
                    for (Short key:lightPathCapacityUp.keySet()
                    ) {
                        lightPathCapacityUp.replace(key,(short)0);
                    }
                    olt.setLightPathCapacityDown(lightPathCapacityDown);
                }else {
                     LOG.info("------deleteSliceError-------no-olt--lightPathCapacityUp------------{}");
                }
                if(lightPathCapacityDown!=null){
                    for (Short key:lightPathCapacityDown.keySet()
                    ) {
                        lightPathCapacityDown.replace(key,(short)0);
                    }
                    olt.setLightPathCapacityUp(lightPathCapacityUp);
                }else {
                     LOG.info("------deleteSliceError-------no--olt-lightPathCapacityDown------------");
                }

                trConfig.SendOltConfig(olt);
            }else {
                 LOG.info("------deleteSliceError-------no-----olt----------");
            }


            //switch config
            List<nodeEswitchConfig>  Eswitchs = slice.getSliceSwitch();
            if(Eswitchs!=null){
                for(nodeEswitchConfig Eswitch:Eswitchs){
                    Map<Short,Short> lightUp = Eswitch.getLightPathCapacityUp();
                    Map<Short,Short> lightDown = Eswitch.getLightPathCapacityDown();
                    if(lightUp!=null){
                        for (Short key:lightUp.keySet()
                        ) {
                            lightUp.replace(key,(short)0);
                        }
                        Eswitch.setLightPathCapacityDown(lightDown);
                    }else {
                        LOG.info("------deleteSliceError-------no-Eswitchs-lightUp-------------{}"+Eswitch.getEswitchId());
                    }
                    if(lightDown!=null){
                        for (Short key:lightDown.keySet()
                        ) {
                            lightDown.replace(key,(short)0);
                        }
                        Eswitch.setLightPathCapacityUp(lightUp);
                    }else {
                        LOG.info("------deleteSliceError-------no-Eswitchs--lightDown------------{}"+Eswitch.getEswitchId());
                    }

                    trConfig.SendEswitchConfig(Eswitch);
                }
            }else {
                LOG.info("------deleteSliceError-------no------Eswitchs---------");
            }


            //link



            Map<Integer,Link> links = slice.getSliceLink();
            if(links!=null){
                for (Integer linkID:links.keySet()) {
                    chaNetRes.changeLink((short)2,links.get(linkID));
                }
            }else {
                LOG.info("------deleteSliceError-------no---links------------");
            }

            //delete database Link path
//        List<Short[]> slicePath = slice.getSlicePathResource();
//        chaNetRes.changeNetPathResource((short)2,slicePath);
            sliceMap.remove(sliceId);
        }else{
            LOG.info("------deleteSliceError-------no-such-slice-------------");
        }

        //ducu configsu



    }

    @Override
    public void sendOolink(Short sliceId,Short startNode,Short destNode,Short waveNumbet){

        linkChangeWave((short)1,startNode,destNode,waveNumbet);
        saveSliceLinkWavelength( sliceId, startNode, destNode, waveNumbet);
    }
    @Override
    public void sendPath(Short sliceId,Short[] path){
        saveSlicePath(sliceId,path);
        savePath(path);
    }





    @Override
    public void sendvAauConfig(Short flag, Short SliceId, Short vAauId, Integer antenna, Short aauId) {
        LOG.info("------SendPacket-------sendvAauConfig------start---");
        nodeAAuConfig configbody = new nodeAAuConfig(aauId,SliceId,vAauId,antenna,flag);
        trConfig.SendAauConfig(configbody);
        LOG.info("------SendPacket-------sendvAauConfig------configbody---{}"+configbody);
        LOG.info("------SendPacket-------sendvAauConfig------end---");
        ChaNetRes cha = new ChaNetRes();
        cha.changNodeAau(flag,SliceId,vAauId,antenna,aauId);
        if(flag==1){
            //create slice,save the slice
            NetSlice slice;
            if(sliceMap.containsKey(SliceId)){
                slice = sliceMap.get(SliceId);
            }else{
                slice =new NetSlice();
            }
            slice.sliceAddAAu(configbody);
            sliceMap.put(SliceId,slice);
        }else if(flag==2){
            //delete slice
            //***find slice in the slicemap ,get the "configbody" change the flag to 2,and SendAauConfig(configbody)
        }



//        send37aau();
    }
//
    @Override
    public void sendDuConfig(Short flag, Short SliceId, Short DuID, Short DcID, Integer capacity, Integer LinkId,
                             Map<Short, Integer> vlinkCapacity) {
        LOG.info("------SendPacket-------sendDuConfig------start---");
        nodeDcConfig configbody = new nodeDcConfig(DcID,(short)0,SliceId,DuID,(long)capacity,vlinkCapacity);
        LOG.info("------SendPacket-------sendDuConfig------configbody---{}"+configbody);
        trConfig.SendDcConfig(configbody);
        LOG.info("------SendPacket------sendDuConfig-------end---");


//******lianyu********************************************************
        String duId = manager.createDuCu(DuID,"du");

            //create slice,save the slice
            NetSlice slice;
            if(sliceMap.containsKey(SliceId)){
                slice = sliceMap.get(SliceId);
            }else{
                slice =new NetSlice();
            }
            slice.sliceAddDc(configbody);
            slice.sliceAddDuCuId(DuID);
//*******lianyu****************************************************
            slice.sliceSetDuResult(DuID,duId);  //

            sliceMap.put(SliceId,slice);

        ChaNetRes cha = new ChaNetRes();
        cha.changNodeDc(flag,SliceId,DuID,DcID,capacity,LinkId,vlinkCapacity);

    }
//
    @Override
    public void sendCuConfig(Short flag, Short SliceId, Short CuId, Short DcID, Integer capacity, Integer LinkId,
                             Map<Short, Integer> vlinkCapacity) {

        LOG.info("------SendPacket-------sendCuConfig------start---");
        LOG.info("------SendPacket-------sendCuConfig------start---"+flag+SliceId+CuId+DcID);
        nodeDcConfig configbody = new nodeDcConfig(DcID,(short)1,SliceId,CuId,(long)capacity,vlinkCapacity);
        LOG.info("------SendPacket-------sendCuConfig------configbody---{}"+configbody);
        trConfig.SendDcConfig(configbody);
//*******lianyu****************************************************
        String cuID =  manager.createDuCu(CuId,"cu");

            //create slice,save the slice
            NetSlice slice;
            if(sliceMap.containsKey(SliceId)){
                slice = sliceMap.get(SliceId);
            }else{
                slice =new NetSlice();
            }
            slice.sliceAddDc(configbody);
            slice.sliceAddDuCuId(CuId);
//*******lianyu****************************************************
            slice.sliceSetCuResult(CuId,cuID);
            sliceMap.put(SliceId,slice);

        ChaNetRes cha = new ChaNetRes();
        cha.changNodeDc(flag,SliceId,CuId,DcID,capacity,LinkId,vlinkCapacity);

        LOG.info("------SendPacket-------sendCuConfig------end---");
    }

    @Override
    public void sendRoadmConfig(Short nodeId, List<Short> outport11_Wavelength,
                                List<Short> outport12_Wavelength, List<Short> outport13_Wavelength, List<Short> outport14_Wavelength,
                                List<Short> outport21_Wavelength, List<Short> outport22_Wavelength, List<Short> outport23_Wavelength,
                                List<Short> outport24_Wavelength, List<Short> outport31_Wavelength, List<Short> outport32_Wavelength,
                                List<Short> outport33_Wavelength, List<Short> outport34_Wavelength, List<Short> outport41_Wavelength,
                                List<Short> outport42_Wavelength, List<Short> outport43_Wavelength, List<Short> outport44_Wavelength) {
        LOG.info("------SendPacket-------sendRoadmConfig------start---");
        nodeRoadmConfig configbody = new nodeRoadmConfig(nodeId,outport11_Wavelength,outport12_Wavelength,outport13_Wavelength,outport14_Wavelength,
                outport21_Wavelength,outport22_Wavelength,outport23_Wavelength,outport24_Wavelength,
                outport31_Wavelength,outport32_Wavelength,outport33_Wavelength,outport34_Wavelength,
                outport41_Wavelength,outport42_Wavelength,outport43_Wavelength,outport44_Wavelength);
        trConfig.SendRoadmConfig(configbody);
        LOG.info("------SendPacket-------sendRoadmConfig------configbody---{}"+configbody);


         ChaNetRes cha = new ChaNetRes();
         cha.changeNodeRoadm(nodeId,outport11_Wavelength,outport12_Wavelength,outport13_Wavelength,outport14_Wavelength,
                outport21_Wavelength,outport22_Wavelength,outport23_Wavelength,outport24_Wavelength,
                outport31_Wavelength,outport32_Wavelength,outport33_Wavelength,outport34_Wavelength,
                outport41_Wavelength,outport42_Wavelength,outport43_Wavelength,outport44_Wavelength);
        LOG.info("------SendPacket-------sendRoadmConfig------end---");
        if(nodeId==26){
            if(RoadmResource.containsKey(20)) {
                LOG.info("------SendPacket-------sendRoadmConfig------sending20roadm---");
                send26roadm();
            }
        }

        send26roadm();
//        if(nodeId==19){
//            LOG.info("------SendPacket-------sendRoadmConfig------sending19roadm---");
//            send19roadm();
//        }


    }
    @Override
    public void sendRoadm(short roadmId,Long outport11Wavelength,Long outport12Wavelength,Long outport13Wavelength,Long outport14Wavelength,
                          Long outport21Wavelength,Long outport22Wavelength,Long outport23Wavelength,Long outport24Wavelength,
                          Long outport31Wavelength,Long outport32Wavelength,Long outport33Wavelength,Long outport34Wavelength,
                          Long outport41Wavelength,Long outport42Wavelength,Long outport43Wavelength,Long outport44Wavelength
                          ){
        LOG.info("---send26Roadm----jiaxin--start-roadmId-{}"+roadmId);
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get((short)26);
        LOG.info("----Send26RoadmConfig-----jiaxin--{}"+linkPropertyService.getNodeMapIgress().get((short)26));
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetRoadmConfigInputBuilder builder = new PacketBniSetRoadmConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));
        builder.setRoadmId(roadmId);
        builder.setBlank((short)1);
        builder.setBlank1(3);

        builder.setOutport11Wavelength(outport11Wavelength);
        builder.setOutport12Wavelength(outport12Wavelength);
        builder.setOutport13Wavelength(outport13Wavelength);
        builder.setOutport14Wavelength(outport14Wavelength);
        builder.setOutport21Wavelength(outport21Wavelength);
        builder.setOutport22Wavelength(outport22Wavelength);
        builder.setOutport23Wavelength(outport23Wavelength);
        builder.setOutport24Wavelength(outport24Wavelength);
        builder.setOutport31Wavelength(outport31Wavelength);
        builder.setOutport32Wavelength(outport32Wavelength);
        builder.setOutport33Wavelength(outport33Wavelength);
        builder.setOutport34Wavelength(outport34Wavelength);
        builder.setOutport41Wavelength(outport41Wavelength);
        builder.setOutport42Wavelength(outport42Wavelength);
        builder.setOutport43Wavelength(outport43Wavelength);
        builder.setOutport44Wavelength(outport44Wavelength);


        LOG.info("---sendRoadm----jiaxin--end1--{}"+builder);
        trConfig.bniSetRoadmConfigService.packetBniSetRoadmConfig(builder.build());
        LOG.info("---sendRoadm----jiaxin--end");
    }

    @Override
    public void showSlice() {
        LOG.info("------showSlice-------start--------------");
        Map<Short, NetworkSlice> networkSlice = ChaNetRes.netSliceData;
        for (Short sliceId:networkSlice.keySet()
                ) {
            NetworkSlice slice = networkSlice.get(sliceId);
            LOG.info("------showSlice---------------------{}"+slice.toString());
        }
        LOG.info("------showSlice-------end--------------");
    }

    @Override
    public void showNetResource() {
        LOG.info("------showNetResource----------start-----------");
        Map<Integer,Link> linkResource = LinkResource;

        Map<Short,NodeAau> aauResource = AauResource;
        Map<Short,Procepool> dcResource = DcResource;
        Map<Short,NodeOLT> oltResource = OltResource;
        Map<Short,NodeRoadm> roadmResource = RoadmResource;
        Map<Short,NodeEswitch> eswitchResource = EswitchResource;
        for (Integer key:linkResource.keySet()
                ) {
            LOG.info("------showNetResource------linkResource---------------()"+linkResource.get(key).toString());
        }
        for (Short key:aauResource.keySet()
                ) {
            LOG.info("------showNetResource------aauResource---------------()"+aauResource.get(key).toString());
        }
        for (Short key:dcResource.keySet()
                ) {
            LOG.info("------showNetResource------dcResource---------------()"+dcResource.get(key).toString());
        }
        for (Short key:oltResource.keySet()
                ) {
            LOG.info("------showNetResource--------oltResource-------------()"+oltResource.get(key).toString());
        }
        for (Short key:roadmResource.keySet()
                ) {
            LOG.info("------showNetResource-------roadmResource--------------()"+roadmResource.get(key).toString());
        }
        for (Short key:eswitchResource.keySet()
                ) {
            LOG.info("------showNetResource--------eswitchResource-------------()"+eswitchResource.get(key).toString());
        }
        LOG.info("------showNetResource---------end------------");
    }


//    @Override
//    public void send37aau(){
//        LOG.info("----SendPacket------sendvAauConfig-------sengONU-");
//        Integer nouid = 37;
//        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(nouid.shortValue());
//        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());
//
//        PacketBniSetAauConfigInputBuilder builder = new PacketBniSetAauConfigInputBuilder();
//        builder.setAauId(nouid.shortValue());
//        builder.setBlank((short)0);
//        builder.setBlank1(0);
//
//        Long configCapacity = new Long(0);
//
//        Long configFrame = new Long(0x00000000652e0000);
//        builder.setConfigCapacity(configCapacity);
//        builder.setConfigFrame(configFrame);
//
//        bniSetAauConfigService.packetBniSetAauConfig(builder.build());
//    }

    @Override
    public void send26roadm(){
        sendRoadm((short)26,new Long(0x000000000000002a),zero,zero,zero,
                            zero,new Long(0x0000000000000028),zero,zero,
                            zero,new Long(0x000000000000002a),new Long(0x0000000000000028),zero,
                            zero,zero,zero,zero);
    }


    @Override
    public void sendVpath(Short flag, Short sliceId, Short startNode, Short destNode, List<Short> pathNodeId, Map<Short, Integer> lightPathCapacity) {
        LOG.info("------SendPacket-------------start---");
        LOG.info("------SendPacket-------------configbody---");
        LOG.info("------SendPacket-------------end---");
    }



    @Override
    public void sendvLinkConfig(Short flag, Short SliceID, Integer LinkId, Map<Short, Integer> vlinkCapacity) {

    }
}
