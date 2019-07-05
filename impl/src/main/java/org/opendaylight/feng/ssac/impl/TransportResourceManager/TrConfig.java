/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.TransportResourceManager;

import org.opendaylight.feng.ssac.impl.ChaNetRes;
import org.opendaylight.feng.ssac.impl.networktopo.NetResource;
import org.opendaylight.feng.ssac.impl.physicalNetwork.Link;
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
import org.opendaylight.feng.ssac.impl.southboundInterface.LinkProperty;
import org.opendaylight.feng.ssac.impl.southboundInterface.LinkPropertyService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class TrConfig extends NetResource {
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

    LinkPropertyService linkPropertyService = new LinkProperty();
    private static final Logger LOG = LoggerFactory.getLogger(TrConfig.class);
    Long zero =new Long(0);

    /**
     *
     * @param configBody
     */
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
        configCapacity = configCapacity | configBody.getCapacity() | (configBody.getvAauId() << 16) |
                (configBody.getSliceId() << 24);
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

//    public void send19roadm() {
//        sendRoadm((short) 27, new Long(0x0000000000000001), zero, zero, zero, zero,
//                new Long(0x00000000000000000004), zero, zero,
//                zero, zero, zero, zero,
//                zero, zero, zero, zero);
//    }

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

}
