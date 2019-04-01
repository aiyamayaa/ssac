/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.physicalNetwork;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Link {
//    private NodeConnectorRef nodeIgress;
    private Integer linkId;
    private String linkType = "other";  //ee(aau-olt) oo(roadm-roadm) oe(other)
    private Short startNode;
    private Short destNode;

    private int linkLength;
    private Map<Short,Integer> linkCapacity = new HashMap<>();  //(lightpath-Id)waveId-slots

    private List<Short> linkWavelength = new ArrayList<>();  //oo-link
    private int eeLinkCapacity = 10000;  //onu-olt
    private Short attriNode;



    public Link(){

    }
    public  Link(Integer linkID,Map<Short,Integer> linkCapacity){
        this.linkId =linkID;
        this.linkCapacity = linkCapacity;
    }
    public Link(Short startNode,Short destNode){
        this.linkId = startNode*100+destNode;
        this.startNode = startNode;
        this.destNode = destNode;
        this.linkType = "ee";
        this.attriNode = startNode;
    }
    public Link(Short startNode,Short destNode,List<Short> linkWavelength){
        this.linkId = startNode*100+destNode;
        this.startNode = startNode;
        this.destNode = destNode;
        this.linkType = "oo";
        this.linkWavelength = linkWavelength;
        this.attriNode = startNode;
    }


    public Link(Short startNode,Short destNode,Map<Short,Integer> linkCapacity){
        this.linkId = startNode*100+destNode;
        this.startNode = startNode;
        this.destNode = destNode;
        this.linkType = "oe";
        this.linkCapacity = linkCapacity;
        this.attriNode = startNode;
    }
    //for test
    public Link(Short startNode,Short destNode,int linkLength,String linkType){
        this.linkId = startNode*100+destNode;
        this.startNode = startNode;
        this.destNode = destNode;
        this.linkLength = linkLength;
        this.linkType = linkType;
    }

    public void addWave(Short waveId){
       try {
           linkWavelength.add(waveId);
       }catch (Exception e){

       }

    }
    public void deleteWave(Short waveId){
       try {
           linkWavelength.remove(waveId);
       }catch (Exception e){

       }

    }
    public void addWaves(List<Short> wavesId){

            try {
                linkWavelength.addAll(wavesId);

            }catch (Exception e){

            }

    }
    public void deleteWaves(List<Short> wavesId){

            try {
                linkWavelength.removeAll(wavesId);

            }catch (Exception e){

            }

    }

    public void addLinkCapacity(Map<Short, Integer> vlinkCapacity){
        if(!(vlinkCapacity==null)){
            for(Short waveId:vlinkCapacity.keySet()){
                if(linkCapacity.containsKey(waveId)){
                    Integer slot = linkCapacity.get(waveId);
                    slot+=vlinkCapacity.get(waveId);
                    linkCapacity.replace(waveId,slot);
                }else {
                    Integer slot =vlinkCapacity.get(waveId);
                    linkCapacity.put(waveId,slot);
                }
            }
        }


    }
    public void deleteLinkCapacity(Map<Short, Integer> vlinkCapacity){
        if(vlinkCapacity==null){

        }else{
            for(Short waveId:vlinkCapacity.keySet()){
                if(linkCapacity.containsKey(waveId)){
                    Integer slot = linkCapacity.get(waveId);
                    slot-=vlinkCapacity.get(waveId);
                    linkCapacity.replace(waveId,slot);
                }else {

                }
            }
        }

    }
    public List<Short> getLinkWavelength() {
    return linkWavelength;
    }

    public void setLinkWavelength(List<Short> linkWavelength) {
        this.linkWavelength = linkWavelength;
    }
    public Short getStartNode() {
        return startNode;
    }

    public void setStartNode(Short startNode) {
        this.startNode = startNode;
    }
    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Short getDestNode() {
        return destNode;
    }

    public void setDestNode(Short destNode) {
        this.destNode = destNode;
    }

    public int getLinkLength() {
        return linkLength;
    }

    public void setLinkLength(int linkLength) {
        this.linkLength = linkLength;
    }

    public Map<Short, Integer> getLinkCapacity() {
        return linkCapacity;
    }

    public void setLinkCapacity(Map<Short, Integer> linkCapacity) {
        this.linkCapacity = linkCapacity;
    }

    public Short getAttriNode() {
        return attriNode;
    }

    public void setAttriNode(Short atttriNode) {
        this.attriNode = atttriNode;
    }


    @Override
    public String toString() {
        if (linkType.equals("oo")) {
            return "Link{" +
                    "linkId=" + linkId +
                    ", startNode=" + startNode +
                    ", destNode=" + destNode +
                    ", linkLength=" + linkLength +
                    ", linkWavelength=" + linkWavelength +
                    '}';
        }
        if (linkType.equals("oe")) {
            return "Link{" +
                    "linkId=" + linkId +
                    ", startNode=" + startNode +
                    ", destNode=" + destNode +
                    ", linkLength=" + linkLength +
                    ", linkCapacity=" + linkCapacity +
                    '}';
        }
        if (linkType.equals("ee")) {
            return "Link{" +
                    "linkId=" + linkId +
                    ", startNode=" + startNode +
                    ", destNode=" + destNode +
                    ", linkLength=" + linkLength +
                    ", eeLinkCapacity=" + eeLinkCapacity +
                    '}';
        }
        return "no such link!";
    }
}
