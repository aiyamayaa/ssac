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

public class NodeOLT extends Node{

    private NodeConnectorRef nodeIgress;
    private Short nodeId;
    private String nodeType = "olt";

    private Map<Integer,Short> connectedLink = new HashMap<>();   //connected linkID, portId

//    private Map<Short,Short> portTotalBandwith = new HashMap<>();  //null
//    private Map<Short,Short> portResidualBandwith = new HashMap<>(); //mull

    public NodeOLT(){}
    public NodeOLT(Short nodeId,NodeConnectorRef nodeIgress){
        this.nodeId = nodeId;
        this.nodeIgress = nodeIgress;
    }
    public NodeOLT(Short nodeId,Map<Integer,Short> connectedLink){  //bingo
        this.nodeId = nodeId;
        this.connectedLink = connectedLink;
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
        this.connectedLink = connectedLink;
    }



    @Override
    public String toString() {
        return "NodeOLT{" +
                "nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", connectedLink=" + connectedLink +
                '}';
    }
}
