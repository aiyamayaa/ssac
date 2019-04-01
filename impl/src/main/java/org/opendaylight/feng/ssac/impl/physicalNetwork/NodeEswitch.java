/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.physicalNetwork;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import java.util.HashMap;
import java.util.Map;

public class NodeEswitch extends Node{

    private NodeConnectorRef nodeIgress;
    private Short nodeId;
    private String nodeType = "eswitch";

    private Map<Integer,Short> connectedLink = new HashMap<>();   // LinkID -- portID



//    private Map<Short,Short> portTotalBandwith;
//    private Map<Short,Short> portResidualBandwith;

    public NodeEswitch(){}
    public NodeEswitch(Short nodeId,Map<Integer,Short> connectedLink){
        this.nodeId = nodeId;
        this.connectedLink = connectedLink;
    }
    public NodeEswitch(Short nodeId,String nodeType,NodeConnectorRef nodeIgress){
     this.nodeIgress = nodeIgress;
     this.nodeId = nodeId;
     this.nodeType = nodeType;

    }



    public NodeConnectorRef getNodeIgress() {
        return nodeIgress;
    }

    public void setNodeIgress(NodeConnectorRef nodeIgress) {
        this.nodeIgress = nodeIgress;
    }

    public Short getNodeId() {
        return nodeId;
    }

    public void setNodeId(Short nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Map<Integer, Short> getConnectedNode() {
        return connectedLink;
    }

    public void setConnectedNode(Map<Integer, Short> connectedNode) {
        this.connectedLink = connectedNode;
    }

//    public Map<Short, Short> getPortTotalBandwith() {
//        return portTotalBandwith;
//    }
//
//    public void setPortTotalBandwith(Map<Short, Short> portTotalBandwith) {
//        this.portTotalBandwith = portTotalBandwith;
//    }
//
//    public Map<Short, Short> getPortResidualBandwith() {
//        return portResidualBandwith;
//    }
//
//    public void setPortResidualBandwith(Map<Short, Short> portResidualBandwith) {
//        this.portResidualBandwith = portResidualBandwith;
//    }

    @Override
    public String toString() {
        return "NodeEswitch{" +
                "nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", connectedLink=" + connectedLink +
                '}';
    }
}
