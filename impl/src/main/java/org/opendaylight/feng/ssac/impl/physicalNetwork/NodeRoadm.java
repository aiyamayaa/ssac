/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.physicalNetwork;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeRoadm extends Node{


    private NodeConnectorRef nodeIgress;
    private Short nodeId;
    private String nodeType = "roadm";
    private Map<Integer, Short> connectedLink;//<Link,port>
    //    private Map<Short,Boolean> port1Res = new HashMap<>();
//    private Map<Short,Boolean> port2Res = new HashMap<>();
//    private Map<Short,Boolean> port3Res = new HashMap<>();
//    private Map<Short,Boolean> port4Res = new HashMap<>();
    private List<Short> inport1outport1wave = new ArrayList();
    private List<Short> inport1outport2wave = new ArrayList();
    private List<Short> inport1outport3wave = new ArrayList();
    private List<Short> inport1outport4wave = new ArrayList();

    private List<Short> inport2outport1wave = new ArrayList();
    private List<Short> inport2outport2wave = new ArrayList();
    private List<Short> inport2outport3wave = new ArrayList();
    private List<Short> inport2outport4wave = new ArrayList();

    private List<Short> inport3outport1wave = new ArrayList();
    private List<Short> inport3outport2wave = new ArrayList();
    private List<Short> inport3outport3wave = new ArrayList();
    private List<Short> inport3outport4wave = new ArrayList();

    private List<Short> inport4outport1wave = new ArrayList();
    private List<Short> inport4outport2wave = new ArrayList();
    private List<Short> inport4outport3wave = new ArrayList();
    private List<Short> inport4outport4wave = new ArrayList();

    public NodeRoadm() {
    }

    public NodeRoadm(Short nodeId, Map<Integer, Short> connectedLink, List<Short> inport1outport1wave,
                     List<Short> inport1outport2wave, List<Short> inport1outport3wave, List<Short> inport1outport4wave,
                     List<Short> inport2outport1wave, List<Short> inport2outport2wave, List<Short> inport2outport3wave,
                     List<Short> inport2outport4wave, List<Short> inport3outport1wave, List<Short> inport3outport2wave,
                     List<Short> inport3outport3wave, List<Short> inport3outport4wave, List<Short> inport4outport1wave,
                     List<Short> inport4outport2wave, List<Short> inport4outport3wave, List<Short> inport4outport4wave) {
        this.nodeId = nodeId;
        this.connectedLink = connectedLink;
        this.nodeType = "roadm";
        this.inport1outport1wave = inport1outport1wave;
        this.inport1outport2wave = inport1outport2wave;
        this.inport1outport3wave = inport1outport3wave;
        this.inport1outport4wave = inport1outport4wave;
        this.inport2outport1wave = inport2outport1wave;
        this.inport2outport2wave = inport2outport2wave;
        this.inport2outport3wave = inport2outport3wave;
        this.inport2outport4wave = inport2outport4wave;
        this.inport3outport1wave = inport3outport1wave;
        this.inport3outport2wave = inport3outport2wave;
        this.inport3outport3wave = inport3outport3wave;
        this.inport3outport4wave = inport3outport4wave;
        this.inport4outport1wave = inport4outport1wave;
        this.inport4outport2wave = inport4outport2wave;
        this.inport4outport3wave = inport4outport3wave;
        this.inport4outport4wave = inport4outport4wave;
    }

    public NodeRoadm(Short nodeId, NodeConnectorRef nodeIgress, Map<Integer, Short> portNoderes) {
        this.nodeId = nodeId;
        this.nodeIgress = nodeIgress;
        this.connectedLink = portNoderes;

    }

    public Short getNodeId() {
        return nodeId;
    }

    public void setNodeId(Short nodeId) {
        this.nodeId = nodeId;
    }

//    public NodeRoadm(NodeConnectorRef nodeIgress, Short nodeId, String nodeType,
//                     Map<Short, Short> portNoderes, Map<Short, Boolean> port1Res,
//                     Map<Short, Boolean> port2Res, Map<Short, Boolean> port3Res,
//                     Map<Short, Boolean> port4Res) {
//
//        this.nodeIgress = nodeIgress;
//        this.nodeId = nodeId;
//        this.nodeType = nodeType;
//        this.connectedLink = portNoderes;
//        this.port1Res = port1Res;
//        this.port2Res = port2Res;
//        this.port3Res = port3Res;
//        this.port4Res = port4Res;
//    }

    public NodeConnectorRef getNodeIgress() {
        return nodeIgress;
    }

    public void setNodeIgress(NodeConnectorRef nodeIgress) {
        this.nodeIgress = nodeIgress;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Map<Integer, Short> getConnectedLink() {
        return connectedLink;
    }

    public void setConnectedLink(Map<Integer, Short> connectedLink) {
        this.connectedLink = connectedLink;
    }

    public List<Short> getInport1outport1wave() {
        return inport1outport1wave;
    }

    public void setInport1outport1wave(List<Short> inport1outport1wave) {
        this.inport1outport1wave = inport1outport1wave;
    }

    public List<Short> getInport1outport2wave() {
        return inport1outport2wave;
    }

    public void setInport1outport2wave(List<Short> inport1outport2wave) {
        this.inport1outport2wave = inport1outport2wave;
    }

    public List<Short> getInport1outport3wave() {
        return inport1outport3wave;
    }

    public void setInport1outport3wave(List<Short> inport1outport3wave) {
        this.inport1outport3wave = inport1outport3wave;
    }

    public List<Short> getInport1outport4wave() {
        return inport1outport4wave;
    }

    public void setInport1outport4wave(List<Short> inport1outport4wave) {
        this.inport1outport4wave = inport1outport4wave;
    }

    public List<Short> getInport2outport1wave() {
        return inport2outport1wave;
    }

    public void setInport2outport1wave(List<Short> inport2outport1wave) {
        this.inport2outport1wave = inport2outport1wave;
    }

    public List<Short> getInport2outport2wave() {
        return inport2outport2wave;
    }

    public void setInport2outport2wave(List<Short> inport2outport2wave) {
        this.inport2outport2wave = inport2outport2wave;
    }

    public List<Short> getInport2outport3wave() {
        return inport2outport3wave;
    }

    public void setInport2outport3wave(List<Short> inport2outport3wave) {
        this.inport2outport3wave = inport2outport3wave;
    }

    public List<Short> getInport2outport4wave() {
        return inport2outport4wave;
    }

    public void setInport2outport4wave(List<Short> inport2outport4wave) {
        this.inport2outport4wave = inport2outport4wave;
    }

    public List<Short> getInport3outport1wave() {
        return inport3outport1wave;
    }

    public void setInport3outport1wave(List<Short> inport3outport1wave) {
        this.inport3outport1wave = inport3outport1wave;
    }

    public List<Short> getInport3outport2wave() {
        return inport3outport2wave;
    }

    public void setInport3outport2wave(List<Short> inport3outport2wave) {
        this.inport3outport2wave = inport3outport2wave;
    }

    public List<Short> getInport3outport3wave() {
        return inport3outport3wave;
    }

    public void setInport3outport3wave(List<Short> inport3outport3wave) {
        this.inport3outport3wave = inport3outport3wave;
    }

    public List<Short> getInport3outport4wave() {
        return inport3outport4wave;
    }

    public void setInport3outport4wave(List<Short> inport3outport4wave) {
        this.inport3outport4wave = inport3outport4wave;
    }

    public List<Short> getInport4outport1wave() {
        return inport4outport1wave;
    }

    public void setInport4outport1wave(List<Short> inport4outport1wave) {
        this.inport4outport1wave = inport4outport1wave;
    }

    public List<Short> getInport4outport2wave() {
        return inport4outport2wave;
    }

    public void setInport4outport2wave(List<Short> inport4outport2wave) {
        this.inport4outport2wave = inport4outport2wave;
    }

    public List<Short> getInport4outport3wave() {
        return inport4outport3wave;
    }

    public void setInport4outport3wave(List<Short> inport4outport3wave) {
        this.inport4outport3wave = inport4outport3wave;
    }

    public List<Short> getInport4outport4wave() {
        return inport4outport4wave;
    }

    public void setInport4outport4wave(List<Short> inport4outport4wave) {
        this.inport4outport4wave = inport4outport4wave;
    }

    @Override
    public String toString() {
        return "NodeRoadm{" +
                "nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", connectedLink=" + connectedLink +
                ", inport1outport1wave=" + inport1outport1wave +
                ", inport1outport2wave=" + inport1outport2wave +
                ", inport1outport3wave=" + inport1outport3wave +
                ", inport1outport4wave=" + inport1outport4wave +
                ", inport2outport1wave=" + inport2outport1wave +
                ", inport2outport2wave=" + inport2outport2wave +
                ", inport2outport3wave=" + inport2outport3wave +
                ", inport2outport4wave=" + inport2outport4wave +
                ", inport3outport1wave=" + inport3outport1wave +
                ", inport3outport2wave=" + inport3outport2wave +
                ", inport3outport3wave=" + inport3outport3wave +
                ", inport3outport4wave=" + inport3outport4wave +
                ", inport4outport1wave=" + inport4outport1wave +
                ", inport4outport2wave=" + inport4outport2wave +
                ", inport4outport3wave=" + inport4outport3wave +
                ", inport4outport4wave=" + inport4outport4wave +
                '}';
    }
}
