/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.networkSliceData;

import org.opendaylight.feng.ssac.impl.physicalNetwork.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkSlice {


    private Short sliceID;
    private List<Short> allNodeId = new ArrayList<>();
    private List<Short> aauId = new ArrayList<>();
    private Map<Short,Short> duId = new HashMap<>();
    private Map<Short,Short> cuId = new HashMap<>();
    private Map<Short,Integer> duResource = new HashMap<>();
    private Map<Short,Integer> cuResource = new HashMap<>();
    private List<Integer> linkId;

    private Map<Integer,Link> linkResource;

    public NetworkSlice(Short sliceID, List<Short> aauId, Map<Short, Short> duId, Map<Short, Short> cuId,
                        Map<Short, Integer> duResource, Map<Short, Integer> cuResource, List<Integer> linkId) {
        this.sliceID = sliceID;
        this.aauId = aauId;
        this.duId = duId;
        this.cuId = cuId;
        this.duResource = duResource;
        this.cuResource = cuResource;
        this.linkId = linkId;
    }

    public NetworkSlice(){}
    public NetworkSlice(Short sliceID) {
        this.sliceID = sliceID;
    }

    public void addAau(Short nodeId){
        List id = this.aauId;
        id.add(nodeId);
        this.aauId = id;
    }
    public void addNode(Short nodeId){
        List id = this.allNodeId;
        id.add(nodeId);
        this.allNodeId = id;
    }
    public void addDu(Short duID,Short dcID){
        Map<Short,Short> nodeID = this.duId;
        nodeID.put(duID,dcID);
        this.duId = nodeID;
    }
    public void addDuResource(Short id,Integer resource){
        Map<Short,Integer> nodeResourec = this.duResource;
        nodeResourec.put(id,resource);
        this.duResource=nodeResourec;
    }
    public void addCu(Short duID,Short dcID){
        Map<Short,Short> nodeID = this.cuId;
        nodeID.put(duID,dcID);
        this.cuId = nodeID;
    }
    public void addCuResource(Short id,Integer resource){
        Map<Short,Integer> nodeResourec = this.cuResource;
        nodeResourec.put(id,resource);
        this.cuResource=nodeResourec;
    }

    public void addlink(Integer linkid){
        List<Integer> link = this.linkId;
        link.add(linkid);
        this.linkId=link;
    }
    public void addLinkResource(Integer linkID,Link link){
        Map<Integer,Link> linkRes = this.linkResource;
        linkRes.put(linkID,link);
        this.linkResource=linkRes;
    }

    @Override
    public String toString() {
        return "NetworkSlice{" +
                "sliceID=" + sliceID +
                ", aauId=" + aauId +
                ", duId=" + duId +
                ", cuId=" + cuId +
                ", duResource=" + duResource +
                ", cuResource=" + cuResource +
                ", linkId=" + linkId +
                ", linkResource=" + showLinkRes()+
                '}';
    }
    public String showLinkRes(){
        String out="[";
        for (Integer id:this.linkResource.keySet()
             ) {
           out += id;
           out += "--";
           out += linkResource.get(id).toString();
           out += ",";
        }
        out+="]";
        return out;
    }


    public Short getSliceID() {
        return sliceID;
    }

    public void setSliceID(Short sliceID) {
        this.sliceID = sliceID;
    }

    public List<Short> getAauId() {
        return aauId;
    }

    public void setAauId(List<Short> aauId) {
        this.aauId = aauId;
    }

    public Map<Short, Short> getDuId() {
        return duId;
    }

    public void setDuId(Map<Short, Short> duId) {
        this.duId = duId;
    }

    public Map<Short, Short> getCuId() {
        return cuId;
    }

    public void setCuId(Map<Short, Short> cuId) {
        this.cuId = cuId;
    }

    public List<Integer> getLinkId() {
        return linkId;
    }

    public void setLinkId(List<Integer> linkId) {
        this.linkId = linkId;
    }

    public Map<Short, Integer> getDuResource() {
        return duResource;
    }

    public void setDuResource(Map<Short, Integer> duResource) {
        this.duResource = duResource;
    }

    public Map<Short, Integer> getCuResource() {
        return cuResource;
    }

    public void setCuResource(Map<Short, Integer> cuResource) {
        this.cuResource = cuResource;
    }
}
