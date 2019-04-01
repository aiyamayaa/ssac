/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;

public class nodeAAuConfig {
    private Short aauID;
    private Short sliceId;
    private Short vAauId;
    private Integer capacity;
    private Short flag;   //1-2-3-4-


    private Short wavelengId;
    private Short linkCapacity;

    public nodeAAuConfig(){}


    public nodeAAuConfig(Short aauID, Short sliceId, Short vAauId, Integer capacity, Short wavelengId, Short linkCapacity, Short flag) {
        this.aauID = aauID;
        this.sliceId = sliceId;
        this.vAauId = vAauId;
        this.capacity = capacity;
        this.wavelengId = wavelengId;
        this.linkCapacity = linkCapacity;
        this.flag = flag;
    }
    public nodeAAuConfig(Short aauID, Short sliceId, Short vAauId, Integer capacity,  Short flag) {
        this.aauID = aauID;
        this.sliceId = sliceId;
        this.vAauId = vAauId;
        this.capacity = capacity;
        this.wavelengId = 0;
        this.linkCapacity = 0;
        this.flag = flag;
    }



    public Short getAauID() {
        return aauID;
    }

    public void setAauID(Short aauID) {
        this.aauID = aauID;
    }

    public Short getSliceId() {
        return sliceId;
    }

    public void setSliceId(Short sliceId) {
        this.sliceId = sliceId;
    }

    public Short getvAauId() {
        return vAauId;
    }

    public void setvAauId(Short vAauId) {
        this.vAauId = vAauId;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Short getWavelengId() {
        return wavelengId;
    }

    public void setWavelengId(Short wavelengId) {
        this.wavelengId = wavelengId;
    }

    public Short getLinkCapacity() {
        return linkCapacity;
    }

    public void setLinkCapacity(Short linkCapacity) {
        this.linkCapacity = linkCapacity;
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }
}
