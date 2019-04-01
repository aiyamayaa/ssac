/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;

import org.opendaylight.feng.ssac.impl.physicalNetwork.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.opendaylight.SliceMap.networkSlice.SliceMap;

public class NetSlice {
    private Short  sliceId;
//    private nodeDu sliceDu;
//    private nodeCu slcieCu;
    private nodeOltConfig sliceOlt;

    private List<nodeAAuConfig> sliceAau = new ArrayList<>();
    private List<nodeRoadmConfig> sliceRoadm = new ArrayList<>();
    private List<nodeDcConfig> sliceDc= new ArrayList<>();

    private Map<Integer,Link> sliceLink = new HashMap<>();

    private List<nodeEswitchConfig> sliceSwitch = new ArrayList<>();
    private Map<Short,String> duResults = new HashMap<>();
    private Map<Short,String> cuResults = new HashMap<>();
    private List<Short> ducuId = new ArrayList<>(  );

    public List<Short> getDuCuId(){
        return ducuId;
    }


    protected static Map<Integer,Link> sliceLinkResource = new HashMap<>();
    protected static List<Short[]> slicePathResource = new ArrayList<>();

    //--------------------------------------------------------------------------------------------------------------//


    public static List<Short[]> getSlicePathResource() {
        return slicePathResource;
    }

    public static void setSlicePathResource(List<Short[]> slicePathResource) {
        NetSlice.slicePathResource = slicePathResource;
    }

    public void setSliceLinkResource(Short startNode, Short destNode, Short wave){
        Integer linkId= startNode*100+destNode;
        if(sliceLink.containsKey(linkId)){
            Link link = sliceLink.get(linkId);
            link.addWave(wave);
            link.setLinkType("oo");
            sliceLink.replace(linkId,link);
        } else{
            Link link = new Link(startNode,destNode);
            link.addWave(wave);
            link.setLinkType("oo");
            sliceLink.put(linkId,link);
        }

    }
    public void sliceAddLink(Link link){
        try{
            sliceLink.put(link.getLinkId(),link);
        }catch(Exception e){

        }

    }
    public void deleteSliceLinkResource(){
        this.sliceLinkResource=null;
    }
    public void setSlicePathResource(Short[] pathResource){
        try{
            slicePathResource.add(pathResource);
        }catch (Exception e){

        }

    }


    public Map<Integer,Link> getSliceLink(){
        return sliceLink ;
    }





    public void sliceAddDuCuId(Short id){
        ducuId.add(id);
    }
    //switch
    //roadm
    public void sliceAddAAu(nodeAAuConfig nodeAau){
        nodeAau.setFlag((short)2);
        this.sliceAau.add(nodeAau);
    }

    public void sliceAddDc(nodeDcConfig nodeDc){

        this.sliceDc.add(nodeDc);
    }
    public void sliceAddRoadm(nodeRoadmConfig nodeRoadm){
        this.sliceRoadm.add(nodeRoadm);
    }

    public void sliceAddOlt(nodeOltConfig sliceOlt){
        sliceOlt.flag = 2;
        this.sliceOlt = sliceOlt;
    }

    public void sliceSetDuResult(Short id,String result){
        if(!duResults.containsKey(id)){
            duResults.put(id,result);
        }

    }
    public void sliceSetCuResult(Short id,String result){
        if(!cuResults.containsKey(id)){
            cuResults.put(id,result);
        }

    }

    public Map<Short, String> getDuResults() {
        return duResults;
    }

    public void setDuResults(Map<Short, String> duResults) {
        this.duResults = duResults;
    }

    public Map<Short, String> getCuResults() {
        return cuResults;
    }

    public void setCuResults(Map<Short, String> cuResults) {
        this.cuResults = cuResults;
    }

    public void sliceAddSwitch(nodeEswitchConfig eswitch){
        this.sliceSwitch.add(eswitch);
    }



    public Short getSliceId() {
        return sliceId;
    }

    public void setSliceId(Short sliceId) {
        this.sliceId = sliceId;
    }

    public List<nodeEswitchConfig> getSliceSwitch() {
        return sliceSwitch;
    }

    public void setSliceSwitch(List<nodeEswitchConfig> sliceSwitch) {
        this.sliceSwitch = sliceSwitch;
    }


    public nodeOltConfig getSliceOlt() {
        return sliceOlt;
    }

    public void setSliceOlt(nodeOltConfig sliceOlt) {
        this.sliceOlt = sliceOlt;
    }

    public List<nodeAAuConfig> getSliceAau() {
        return sliceAau;
    }

    public void setSliceAau(List<nodeAAuConfig> sliceAau) {
        this.sliceAau = sliceAau;
    }

    public List<nodeRoadmConfig> getSliceRoadm() {
        return sliceRoadm;
    }

    public void setSliceRoadm(List<nodeRoadmConfig> sliceRoadm) {
        this.sliceRoadm = sliceRoadm;
    }

    public List<nodeDcConfig> getSliceDc() {
        return sliceDc;
    }

    public void setSliceDc(List<nodeDcConfig> sliceDc) {
        this.sliceDc = sliceDc;
    }




}
