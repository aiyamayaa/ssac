/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.physicalNetwork;

public class NodeAau extends Node{

    private Short nodeId;
    private String nodeType = "aau";

    //private Short vNodeNums;
    private int totalAntennas;
    private int residualAntennas;
    private Short connectedNodeId;

    public NodeAau(){}

    public NodeAau(Short nodeId, Short connectedNodeId,int totalAntennas, int residualAntennas) {
        this.nodeId = nodeId;
        //this.vNodeNums = vNodeNums;
        this.totalAntennas = totalAntennas;
        this.residualAntennas = residualAntennas;
        this.connectedNodeId = connectedNodeId;
    }

    public Short getNodeId() {
        return nodeId;
    }

    public void setNodeId(Short nodeId) {
        this.nodeId = nodeId;
    }

//    public Short getvNodeNums() {
//        return vNodeNums;
//    }
//
//    public void setvNodeNums(Short vNodeNums) {
//        this.vNodeNums = vNodeNums;
//    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public int getTotalAntennas() {
        return totalAntennas;
    }

    public void setTotalAntennas(int totalAntennas) {
        this.totalAntennas = totalAntennas;
    }

    public int getResidualAntennas() {
        return residualAntennas;
    }

    public void setResidualAntennas(int residualAntennas) {
        this.residualAntennas = residualAntennas;
    }

    public Short getConnectedNodeId() {
        return connectedNodeId;
    }

    public void setConnectedNodeId(Short connectedNodeId) {
        this.connectedNodeId = connectedNodeId;
    }

    @Override
    public String toString() {
        return "NodeAau{" +
                "nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", totalAntennas=" + totalAntennas +
                ", residualAntennas=" + residualAntennas +
                ", connectedNodeId=" + connectedNodeId +
                '}';
    }
}
