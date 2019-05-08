/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.TransportResourceManager;


import org.opendaylight.feng.ssac.impl.ChaNetRes;
import org.opendaylight.feng.ssac.impl.networkSliceData.NetworkSlice;
import org.opendaylight.feng.ssac.impl.networktopo.NetResource;
import org.opendaylight.feng.ssac.impl.physicalNetwork.*;
import org.opendaylight.feng.ssac.impl.virtualNetwork.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aaufeaturerequest.rev180606.PacketBniAauFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcfeaturerequest.rev180606.PacketBniDcFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchfeaturerequest.rev180606.PacketBniEswitchFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltfeaturerequest.rev180606.PacketBniOltFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmfeaturerequest.rev180314.PacketBniRoadmFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setaauconfig.rev180606.PacketBniSetAauConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setaauconfig.rev180606.PacketBniSetAauConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setdcconfig.rev180606.PacketBniSetDcConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setdcconfig.rev180606.PacketBniSetDcConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.seteswitchconfig.rev180606.PacketBniSetEswitchConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.seteswitchconfig.rev180606.PacketBniSetEswitchConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setoltconfig.rev180606.PacketBniSetOltConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setoltconfig.rev180606.PacketBniSetOltConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setroadmconfig.rev180606.PacketBniSetRoadmConfigInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setroadmconfig.rev180606.PacketBniSetRoadmConfigService;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class SendPacket extends NetResource implements SendPacketService {
//1 for establish / 2 for delete/ 3for add / 4for reduce
    public static PacketBniSetAauConfigService bniSetAauConfigService = null;
    public static PacketBniSetRoadmConfigService bniSetRoadmConfigService = null;
    public static PacketBniSetOltConfigService bniSetOltConfigService = null;
    public static PacketBniSetEswitchConfigService bniSetEswitchConfigService = null;
    public static PacketBniSetDcConfigService bniSetDcConfigService = null;

    public static PacketBniAauFeatureRequestService bniAauFeatureRequestService = null;
    public static PacketBniRoadmFeatureRequestService bniRoadmFeatureRequestService = null;
    public static PacketBniOltFeatureRequestService bniOltFeatureRequestService = null;
    public static PacketBniEswitchFeatureRequestService bniEswitchFeatureRequestService = null;
    public static PacketBniDcFeatureRequestService bniDcFeatureRequestService = null;


    Long zero =new Long(0);

    private static final Logger LOG = LoggerFactory.getLogger(SendPacket.class);
    LinkProperty linkPropertyService = new LinkProperty();


    @Override
    public void deleteSlice(Short sliceId) {
        if(sliceMap.containsKey(sliceId)){
            NetSlice slice =sliceMap.get(sliceId);

            Map<Short,String> duResults = slice.getDuResults();
            Map<Short,String> cuResults = slice.getCuResults();
            /*OpenstackAgency connected = new OpenstackAgency();
            if(duResults!=null){
                for(Map.Entry<Short, String> m :duResults.entrySet()){
                    LOG.info("------deleteSliceError--------duResults--------------{}"+m.getValue());
                    System.out.println("DELETE++++++++duID+++"+m.getValue());
                    connected.delete(m.getValue());
                }
            }else{
                 LOG.info("------deleteSliceError-------no-duResults--------------");
            }
            //-----------cuilu---------

            if(cuResults!=null){
                for(Map.Entry<Short, String> m :cuResults.entrySet()){
                    LOG.info("------deleteSliceError--------cuResults--------------{}"+m.getValue());
                    System.out.println("DELETE++++++++cuID+++"+m.getValue());
                    connected.delete(m.getValue());
                }

            }else{
                 LOG.info("------deleteSliceError-------no-cuResults--------------");
            }
*/


            //------------------------
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
                    SendAauConfig(aau);
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

            SendOltConfig(olt);
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

                    SendEswitchConfig(Eswitch);
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


    public void SendAauConfig(nodeAAuConfig configBody) {
        LOG.info("AAU-Config jiaxin");
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(configBody.getAauID());
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetAauConfigInputBuilder builder = new PacketBniSetAauConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));
        builder.setAauId(configBody.getAauID());
        builder.setBlank((short)33);
        builder.setBlank1(34);

        Long configCapacity = new Long(0);
        configCapacity = configCapacity | configBody.getCapacity() | (configBody.getvAauId() << 16) | (configBody.getSliceId() << 24);
        LOG.info("config Frame"+ configCapacity);
        Long configFrame = new Long(0);
        configFrame = configFrame | configBody.getFlag() | ((long)configBody.getLinkCapacity() << 8) |
                ((long)configBody.getWavelengId()<<16);
        builder.setConfigCapacity(configCapacity);
        builder.setConfigFrame(configFrame);

        bniSetAauConfigService.packetBniSetAauConfig(builder.build());

    }


    public void SendDcConfig(nodeDcConfig configBody) {
        LOG.info("----SendDcConfig----- jiaxin");
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(configBody.getDcId());
        LOG.info("----SendDcConfig-----jiaxin--{}"+linkPropertyService.getNodeMapIgress().get(configBody.getDcId()));
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());
        LOG.info("----SendDcConfig--nodeRef---jiaxin--{}"+nodeRef);
        PacketBniSetDcConfigInputBuilder builder = new PacketBniSetDcConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));
        LOG.info("----SendDcConfig----builder-jiaxin--{}"+builder);
        Long configFrame = new Long(0);
        configFrame = configFrame | configBody.getvDcId() | (configBody.getSliceId()<<8) | (configBody.getResourceTypeFlag() << 16)
                | (configBody.getDcId()<<24);
        builder.setConfigFrame(configFrame);

        builder.setVnodeCapacity(configBody.getvNodeCapacity());
        BigInteger waveSlot = new BigInteger("0");
        BigInteger wavelength = new BigInteger("0");

        Map<Short,Short> lightPathCapacity = configBody.getLightPathCapacity();
        int i=0;
        LOG.info("----SendDcConfig-----jiaxin--{}"+builder);
        if(lightPathCapacity != null){
            for(Short wave:lightPathCapacity.keySet()){
                BigInteger wavelen = new BigInteger(wave.toString());
                BigInteger slot = new BigInteger(lightPathCapacity.get(wave).toString());
                wavelength = wavelength.or(wavelen.shiftLeft(i*8));
                waveSlot = waveSlot.or(slot.shiftLeft(i*8));
            }
        }
        LOG.info("----SendDcConfig-----jiaxin-builder-{}"+builder);
        builder.setWaveSlots(waveSlot);
        builder.setWaveLength(wavelength);
        LOG.info("----SendDcConfig-----jiaxin--builder{}"+builder);
        bniSetDcConfigService.packetBniSetDcConfig(builder.build());
        LOG.info("----SendDcConfig-----jiaxin--end{}");
    }

    public void SendRoadmConfig(nodeRoadmConfig configBody) {
        LOG.info("---sendRoadm----jiaxin--start");
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(configBody.getRoadmId());
        LOG.info("----SendDRoadmConfig-----jiaxin--{}"+linkPropertyService.getNodeMapIgress().get(configBody.getRoadmId()));
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetRoadmConfigInputBuilder builder = new PacketBniSetRoadmConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));
        builder.setRoadmId(configBody.getRoadmId());
        builder.setBlank((short)1);
        builder.setBlank1(3);
        LOG.info("------sendRoadm-----21---{}"+configBody.getOutport21_Wavelength());
        LOG.info("------sendRoadm-----22---{}"+configBody.getOutport22_Wavelength());
        LOG.info("------sendRoadm-----23---{}"+configBody.getOutport23_Wavelength());
        LOG.info("------sendRoadm-----24---{}"+configBody.getOutport24_Wavelength());
        LOG.info("------sendRoadm-----31---{}"+configBody.getOutport31_Wavelength());
        LOG.info("------sendRoadm-----32---{}"+configBody.getOutport32_Wavelength());
        LOG.info("------sendRoadm-----33---{}"+configBody.getOutport33_Wavelength());
        LOG.info("------sendRoadm-----34---{}"+configBody.getOutport34_Wavelength());
        Long outport11Wavelength = List_To_Long(configBody.getOutport11_Wavelength());
        Long outport12Wavelength = List_To_Long(configBody.getOutport12_Wavelength());
        Long outport13Wavelength = List_To_Long(configBody.getOutport13_Wavelength());
        Long outport14Wavelength = List_To_Long(configBody.getOutport14_Wavelength());

        Long outport21Wavelength = List_To_Long(configBody.getOutport21_Wavelength());
        Long outport22Wavelength = List_To_Long(configBody.getOutport22_Wavelength());
        Long outport23Wavelength = List_To_Long(configBody.getOutport23_Wavelength());
        Long outport24Wavelength = List_To_Long(configBody.getOutport24_Wavelength());

        Long outport31Wavelength = List_To_Long(configBody.getOutport31_Wavelength());
        Long outport32Wavelength = List_To_Long(configBody.getOutport32_Wavelength());
        Long outport33Wavelength = List_To_Long(configBody.getOutport33_Wavelength());
        Long outport34Wavelength = List_To_Long(configBody.getOutport34_Wavelength());

        Long outport41Wavelength = List_To_Long(configBody.getOutport41_Wavelength());
        Long outport42Wavelength = List_To_Long(configBody.getOutport42_Wavelength());
        Long outport43Wavelength = List_To_Long(configBody.getOutport43_Wavelength());
        Long outport44Wavelength = List_To_Long(configBody.getOutport44_Wavelength());


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
        bniSetRoadmConfigService.packetBniSetRoadmConfig(builder.build());
        LOG.info("---sendRoadm----jiaxin--end");
    }

    public void SendOltConfig(nodeOltConfig configBody) {
        LOG.info("---sendOlt----jiaxin--start");
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(configBody.getOltId());
        LOG.info("----SendDOltConfig-----jiaxin--{}"+linkPropertyService.getNodeMapIgress().get(configBody.getOltId()));
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetOltConfigInputBuilder builder = new PacketBniSetOltConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));

        builder.setOltId(configBody.getOltId());
        builder.setBlank((short)0);
        builder.setBlank1(0);
        builder.setBlank2(new Long(0));
        builder.setWaveSlots1(List_To_BigInteger_Value(configBody.getLightPathCapacityUp()));
        builder.setWaveSlots2(List_To_BigInteger_Value(configBody.getLightPathCapacityDown()));

        builder.setWaveLength1(List_To_BigInteger_Key(configBody.getLightPathCapacityUp()));
        builder.setWaveLength2(List_To_BigInteger_Key(configBody.getLightPathCapacityDown()));
        LOG.info("---sendOlt----jiaxin--end");
        bniSetOltConfigService.packetBniSetOltConfig(builder.build());
        LOG.info("---sendOlt----jiaxin--end");
    }

    public void SendEswitchConfig(nodeEswitchConfig configBody) {
        LOG.info("---sendEswitch----jiaxin--start");
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(configBody.getEswitchId());
         LOG.info("----SendDEswitchConfig-----jiaxin--{}"+linkPropertyService.getNodeMapIgress().get(configBody.getEswitchId()));
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetEswitchConfigInputBuilder builder = new PacketBniSetEswitchConfigInputBuilder();
        builder.setNode(new NodeRef(egressNode));

        builder.setWaveSlot1(List_To_BigInteger_Value(configBody.getLightPathCapacityUp()));
        builder.setWaveSlot2(List_To_BigInteger_Value(configBody.getLightPathCapacityDown()));

        builder.setWaveLength1(List_To_BigInteger_Key(configBody.getLightPathCapacityUp()));
        builder.setWaveLength2(List_To_BigInteger_Key(configBody.getLightPathCapacityDown()));

        LOG.info("---sendEswitch----jiaxin--end");
        bniSetEswitchConfigService.packetBniSetEswitchConfig(builder.build());
        LOG.info("---sendEswitch----jiaxin--end");

    }

    Long List_To_Long(List<Short> input){
        Long output = new Long("0");
        if(input !=null){

        int i = 0;
        for(Short in:input){
            output = output | (in << 8*i);
            i++;
        }
        }

        return output;
    }

    BigInteger List_To_BigInteger_Key(Map<Short,Short> input){
        BigInteger outputKey = new BigInteger("0");
        if(input !=null){
            int i = 0;
            for(Short in : input.keySet()){
                BigInteger inn = new BigInteger(in.toString());
                outputKey = outputKey.or(inn.shiftLeft(i*8));
            i++;
            if (i>8){
                LOG.info("error---get error List--jiaxintest");
                break;
            }
        }
        }

        return outputKey;
    }

    BigInteger List_To_BigInteger_Value(Map<Short,Short> input){

        BigInteger outputValue = new BigInteger("0");
        if(input!=null ){
            int i = 0;
            for(Short in : input.keySet()){
                BigInteger inn = new BigInteger(input.get(in).toString());
                outputValue = outputValue.or(inn.shiftLeft(i*8));
            i++;
            if (i>8){
                LOG.info("error---get error List--jiaxintest");
                break;
            }
        }
        }

        return outputValue;
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
        Map<Integer,Link> linkResource = LinkProperty.LinkResource;

        Map<Short,NodeAau> aauResource = LinkProperty.AauResource;
        Map<Short,Procepool> dcResource = LinkProperty.DcResource;
        Map<Short,NodeOLT> oltResource = LinkProperty.OltResource;
        Map<Short,NodeRoadm> roadmResource = LinkProperty.RoadmResource;
        Map<Short,NodeEswitch> eswitchResource = LinkProperty.EswitchResource;
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

    @Override
    public void sendvAauConfig(Short flag, Short SliceId, Short vAauId, Integer antenna, Short aauId) {
        LOG.info("------SendPacket-------sendvAauConfig------start---");
        nodeAAuConfig configbody = new nodeAAuConfig(aauId,SliceId,vAauId,antenna,flag);
        SendAauConfig(configbody);
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



        send37aau();
    }
//
    @Override
    public void sendDuConfig(Short flag, Short SliceId, Short DuID, Short DcID, Integer capacity, Integer LinkId,
                             Map<Short, Integer> vlinkCapacity) {
        LOG.info("------SendPacket-------sendDuConfig------start---");
        nodeDcConfig configbody = new nodeDcConfig(DcID,(short)0,SliceId,DuID,(long)capacity,vlinkCapacity);
        LOG.info("------SendPacket-------sendDuConfig------configbody---{}"+configbody);
        SendDcConfig(configbody);
        LOG.info("------SendPacket------sendDuConfig-------end---");
       /* OpenstackAgency connected = new OpenstackAgency();  //
        String result = connected.createVM("du"+DuID);
        JSONObject object = new JSONObject(result);
        JSONObject tmp = object.getJSONObject("output");
        String duId = tmp.getString("result");
        System.out.println("++++=Du++++++"+duId);
        LOG.info("------SendPacket-------sendDuConfig---OpenstackAgency------");

            //create slice,save the slice
            NetSlice slice;
            if(sliceMap.containsKey(SliceId)){
                slice = sliceMap.get(SliceId);
            }else{
                slice =new NetSlice();
            }
            slice.sliceAddDc(configbody);
            slice.sliceAddDuCuId(DuID);
            slice.sliceSetDuResult(DuID,duId);  //

            sliceMap.put(SliceId,slice);

        ChaNetRes cha = new ChaNetRes();
        cha.changNodeDc(flag,SliceId,DuID,DcID,capacity,LinkId,vlinkCapacity);
        */
    }
//
    @Override
    public void sendCuConfig(Short flag, Short SliceId, Short CuId, Short DcID, Integer capacity, Integer LinkId,
                             Map<Short, Integer> vlinkCapacity) {

        LOG.info("------SendPacket-------sendCuConfig------start---");
        LOG.info("------SendPacket-------sendCuConfig------start---"+flag+SliceId+CuId+DcID);
        nodeDcConfig configbody = new nodeDcConfig(DcID,(short)1,SliceId,CuId,(long)capacity,vlinkCapacity);
        LOG.info("------SendPacket-------sendCuConfig------configbody---{}"+configbody);
        SendDcConfig(configbody);
        /*OpenstackAgency connected = new OpenstackAgency();
        String result = connected.createVM("cu"+CuId);
        JSONObject object = new JSONObject(result);
        JSONObject tmp = object.getJSONObject("output");
        String cuID = tmp.getString("result");
        System.out.println("+++ssac+++"+result);

        LOG.info("------SendPacket-------sendCuConfig---OpenstackAgency------");

            //create slice,save the slice
            NetSlice slice;
            if(sliceMap.containsKey(SliceId)){
                slice = sliceMap.get(SliceId);
            }else{
                slice =new NetSlice();
            }
            slice.sliceAddDc(configbody);
            slice.sliceAddDuCuId(CuId);
            slice.sliceSetCuResult(CuId,cuID);
            sliceMap.put(SliceId,slice);


        ChaNetRes cha = new ChaNetRes();
        cha.changNodeDc(flag,SliceId,CuId,DcID,capacity,LinkId,vlinkCapacity);
        */
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
        SendRoadmConfig(configbody);
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
        bniSetRoadmConfigService.packetBniSetRoadmConfig(builder.build());
        LOG.info("---sendRoadm----jiaxin--end");
    }

    @Override
    public void send37aau(){
        LOG.info("----SendPacket------sendvAauConfig-------sengONU-");
        Integer nouid = 37;
        NodeConnectorRef nodeRef = linkPropertyService.getNodeMapIgress().get(nouid.shortValue());
        InstanceIdentifier<Node> egressNode = InstanceIdentifierUtils.getNodePath(nodeRef.getValue());

        PacketBniSetAauConfigInputBuilder builder = new PacketBniSetAauConfigInputBuilder();
        builder.setAauId(nouid.shortValue());
        builder.setBlank((short)0);
        builder.setBlank1(0);

        Long configCapacity = new Long(0);

        Long configFrame = new Long(0x00000000652e0000);
        builder.setConfigCapacity(configCapacity);
        builder.setConfigFrame(configFrame);

        bniSetAauConfigService.packetBniSetAauConfig(builder.build());
    }

    @Override
    public void send26roadm(){
        sendRoadm((short)26,new Long(0x0000000000000000),new Long(0x0000000000000000),zero,zero,
                zero,zero,new Long(0x0000000000000000),zero,
                zero,zero,new Long(0x0000000000000000),zero,
                zero,zero,zero,zero);
    }

    public void send19roadm() {
        sendRoadm((short) 27, new Long(0x0000000000000001), zero, zero, zero, zero,
                new Long(0x00000000000000000004), zero, zero,
                zero, zero, zero, zero,
                zero, zero, zero, zero);
    }

    public void sendvLinkConfig(Short flag, Short SliceId, Integer LinkId, Map<Short, Integer> vlinkCapacity) {
        LOG.info("------SendPacket------sendvLinkConfig-------start---");
        Integer startNodeId = LinkId/100;
        Integer destNodeId = LinkId%100;
        LOG.info("------SendPacket------sendvLinkConfig-------startNodeId---"+startNodeId);
        LOG.info("------SendPacket------sendvLinkConfig-------destNodeId---"+destNodeId);
        //----------------------------------------
        NetSlice slice;
        if(sliceMap.containsKey(SliceId)){
            slice = sliceMap.get(SliceId);
        }else{
            slice =new NetSlice();
        }
        //-----------------------
        if(startNodeId >= 0 && startNodeId <= 15) {
            LOG.info("------SendPacket------sendvLinkConfig-----------");
            nodeEswitchConfig configbody;
            if (destNodeId > 15 && destNodeId < 31) {
                LOG.info("------SendPacket------sendvLinkConfig-------eswitch-roadm---");
                 configbody = new nodeEswitchConfig(startNodeId.shortValue(), null, vlinkCapacity);
                SendEswitchConfig(configbody);
                if(flag==1){
                    //create slice,save the slice
                    slice.sliceAddSwitch(configbody);
                }
            } else if (destNodeId >= 46 && destNodeId <= 55) {
                LOG.info("------SendPacket------sendvLinkConfig-------eswitch-pool---");
                 configbody = new nodeEswitchConfig(startNodeId.shortValue(), vlinkCapacity, null);
                SendEswitchConfig(configbody);
                //-----------------------------
                if(flag==1){
                    //create slice,save the slice
                    slice.sliceAddSwitch(configbody);
                }
                //-----------------------------
            }

        }else if(startNodeId >= 56 && startNodeId <= 60){
            LOG.info("------SendPacket------sendvLinkConfig-------olt-eswitch---");
            nodeOltConfig configbody = new nodeOltConfig(startNodeId.shortValue(),vlinkCapacity,null);
            SendOltConfig(configbody);
            if(flag==1){
                //create slice,save the slice
                slice.sliceAddOlt(configbody);
            }
        }

        LOG.info("------SendPacket------sendvLinkConfig-------configbody---");
        LOG.info("------SendPacket------sendvLinkConfig-------end---");
         ChaNetRes cha = new ChaNetRes();
         cha.changeLink(flag,SliceId,LinkId,vlinkCapacity,null);
         Link link = new Link(LinkId,vlinkCapacity);
         link.setLinkType("oe");
        slice.sliceAddLink(link);
        sliceMap.put(SliceId,slice);
    }

    @Override
    public void sendVpath(Short flag, Short sliceId, Short startNode, Short destNode, List<Short> pathNodeId, Map<Short, Integer> lightPathCapacity) {
        LOG.info("------SendPacket-------------start---");
        LOG.info("------SendPacket-------------configbody---");
        LOG.info("------SendPacket-------------end---");
    }



}
