/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.physicalNetwork;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

public class Procepool extends Node{

    private NodeConnectorRef nodeIgress;
    private Short nodeId;
    private String nodeType = "Ppool";
    private int totalCapacity;
    private int residualCapacity;
    private Short virtualNodeNums;



    public Procepool(){}

    public Procepool(Short nodeId,NodeConnectorRef nodeIgress){
        this.nodeId = nodeId;
        this.nodeIgress = nodeIgress;
    }

    public Procepool(Short nodeId,Short virtualNodeNums,int totalCapacity,int residualCapacity){
        this.nodeId = nodeId;
        this.virtualNodeNums = virtualNodeNums;
        this.totalCapacity = totalCapacity;
        this.residualCapacity = residualCapacity;

    }

    public Procepool(NodeConnectorRef nodeIgress, Short nodeId, int totalCapacity,
                     int residualCapacity, Short virtualNodeNums) {

        this.nodeIgress = nodeIgress;
        this.nodeId = nodeId;
        this.totalCapacity = totalCapacity;
        this.residualCapacity = residualCapacity;
        this.virtualNodeNums = virtualNodeNums;
    }
    public Short getVirtualNodeNums() {
        return virtualNodeNums;
    }

    public void setVirtualNodeNums(Short virtualNodeNums) {
        this.virtualNodeNums = virtualNodeNums;
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

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getResidualCapacity() {
        return residualCapacity;
    }

    public void setResidualCapacity(int residualCapacity) {
        this.residualCapacity = residualCapacity;
    }

    @Override
    public String toString() {
        return "Procepool{" +
                "nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", totalCapacity=" + totalCapacity +
                ", residualCapacity=" + residualCapacity +
                '}';
    }
}
