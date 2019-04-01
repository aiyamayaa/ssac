/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;

import java.util.List;

public class nodeRoadmConfig {
     private Short roadmId;
    private List<Short> outport11_Wavelength;
    private List<Short> outport12_Wavelength;
    private List<Short> outport13_Wavelength;
    private List<Short> outport14_Wavelength;

    private List<Short> outport21_Wavelength;
    private List<Short> outport22_Wavelength;
    private List<Short> outport23_Wavelength;
    private List<Short> outport24_Wavelength;

    private List<Short> outport31_Wavelength;
    private List<Short> outport32_Wavelength;
    private List<Short> outport33_Wavelength;
    private List<Short> outport34_Wavelength;

    private List<Short> outport41_Wavelength;
    private List<Short> outport42_Wavelength;
    private List<Short> outport43_Wavelength;
    private List<Short> outport44_Wavelength;

    public nodeRoadmConfig(){}

    public nodeRoadmConfig(List<Short> outport11_Wavelength, List<Short> outport12_Wavelength,
                           List<Short> outport13_Wavelength, List<Short> outport14_Wavelength,
                           List<Short> outport21_Wavelength, List<Short> outport22_Wavelength,
                           List<Short> outport23_Wavelength, List<Short> outport24_Wavelength,
                           List<Short> outport31_Wavelength, List<Short> outport32_Wavelength,
                           List<Short> outport33_Wavelength, List<Short> outport34_Wavelength,
                           List<Short> outport41_Wavelength, List<Short> outport42_Wavelength,
                           List<Short> outport43_Wavelength, List<Short> outport44_Wavelength) {
        this.outport11_Wavelength = outport11_Wavelength;
        this.outport12_Wavelength = outport12_Wavelength;
        this.outport13_Wavelength = outport13_Wavelength;
        this.outport14_Wavelength = outport14_Wavelength;
        this.outport21_Wavelength = outport21_Wavelength;
        this.outport22_Wavelength = outport22_Wavelength;
        this.outport23_Wavelength = outport23_Wavelength;
        this.outport24_Wavelength = outport24_Wavelength;
        this.outport31_Wavelength = outport31_Wavelength;
        this.outport32_Wavelength = outport32_Wavelength;
        this.outport33_Wavelength = outport33_Wavelength;
        this.outport34_Wavelength = outport34_Wavelength;
        this.outport41_Wavelength = outport41_Wavelength;
        this.outport42_Wavelength = outport42_Wavelength;
        this.outport43_Wavelength = outport43_Wavelength;
        this.outport44_Wavelength = outport44_Wavelength;
    }

    public nodeRoadmConfig(Short roadmId,
                           List<Short> outport11_Wavelength, List<Short> outport12_Wavelength,
                           List<Short> outport13_Wavelength, List<Short> outport14_Wavelength,
                           List<Short> outport21_Wavelength, List<Short> outport22_Wavelength, List<Short> outport23_Wavelength,
                           List<Short> outport24_Wavelength,
                           List<Short> outport31_Wavelength, List<Short> outport32_Wavelength, List<Short> outport33_Wavelength,
                           List<Short> outport34_Wavelength, List<Short> outport41_Wavelength,
                           List<Short> outport42_Wavelength, List<Short> outport43_Wavelength, List<Short> outport44_Wavelength) {
        this.roadmId = roadmId;
        this.outport11_Wavelength = outport11_Wavelength;
        this.outport12_Wavelength = outport12_Wavelength;
        this.outport13_Wavelength = outport13_Wavelength;
        this.outport14_Wavelength = outport14_Wavelength;
        this.outport21_Wavelength = outport21_Wavelength;
        this.outport22_Wavelength = outport22_Wavelength;
        this.outport23_Wavelength = outport23_Wavelength;
        this.outport24_Wavelength = outport24_Wavelength;
        this.outport31_Wavelength = outport31_Wavelength;
        this.outport32_Wavelength = outport32_Wavelength;
        this.outport33_Wavelength = outport33_Wavelength;
        this.outport34_Wavelength = outport34_Wavelength;
        this.outport41_Wavelength = outport41_Wavelength;
        this.outport42_Wavelength = outport42_Wavelength;
        this.outport43_Wavelength = outport43_Wavelength;
        this.outport44_Wavelength = outport44_Wavelength;
    }

    public Short getRoadmId() {
        return roadmId;
    }

    public void setRoadmId(Short roadmId) {
        this.roadmId = roadmId;
    }

    public List<Short> getOutport11_Wavelength() {
        return outport11_Wavelength;
    }

    public void setOutport11_Wavelength(List<Short> outport11_Wavelength) {
        this.outport11_Wavelength = outport11_Wavelength;
    }

    public List<Short> getOutport12_Wavelength() {
        return outport12_Wavelength;
    }

    public void setOutport12_Wavelength(List<Short> outport12_Wavelength) {
        this.outport12_Wavelength = outport12_Wavelength;
    }

    public List<Short> getOutport13_Wavelength() {
        return outport13_Wavelength;
    }

    public void setOutport13_Wavelength(List<Short> outport13_Wavelength) {
        this.outport13_Wavelength = outport13_Wavelength;
    }

    public List<Short> getOutport14_Wavelength() {
        return outport14_Wavelength;
    }

    public void setOutport14_Wavelength(List<Short> outport14_Wavelength) {
        this.outport14_Wavelength = outport14_Wavelength;
    }

    public List<Short> getOutport21_Wavelength() {
        return outport21_Wavelength;
    }

    public void setOutport21_Wavelength(List<Short> outport21_Wavelength) {
        this.outport21_Wavelength = outport21_Wavelength;
    }

    public List<Short> getOutport22_Wavelength() {
        return outport22_Wavelength;
    }

    public void setOutport22_Wavelength(List<Short> outport22_Wavelength) {
        this.outport22_Wavelength = outport22_Wavelength;
    }

    public List<Short> getOutport23_Wavelength() {
        return outport23_Wavelength;
    }

    public void setOutport23_Wavelength(List<Short> outport23_Wavelength) {
        this.outport23_Wavelength = outport23_Wavelength;
    }

    public List<Short> getOutport24_Wavelength() {
        return outport24_Wavelength;
    }

    public void setOutport24_Wavelength(List<Short> outport24_Wavelength) {
        this.outport24_Wavelength = outport24_Wavelength;
    }

    public List<Short> getOutport31_Wavelength() {
        return outport31_Wavelength;
    }

    public void setOutport31_Wavelength(List<Short> outport31_Wavelength) {
        this.outport31_Wavelength = outport31_Wavelength;
    }

    public List<Short> getOutport32_Wavelength() {
        return outport32_Wavelength;
    }

    public void setOutport32_Wavelength(List<Short> outport32_Wavelength) {
        this.outport32_Wavelength = outport32_Wavelength;
    }

    public List<Short> getOutport33_Wavelength() {
        return outport33_Wavelength;
    }

    public void setOutport33_Wavelength(List<Short> outport33_Wavelength) {
        this.outport33_Wavelength = outport33_Wavelength;
    }

    public List<Short> getOutport34_Wavelength() {
        return outport34_Wavelength;
    }

    public void setOutport34_Wavelength(List<Short> outport34_Wavelength) {
        this.outport34_Wavelength = outport34_Wavelength;
    }

    public List<Short> getOutport41_Wavelength() {
        return outport41_Wavelength;
    }

    public void setOutport41_Wavelength(List<Short> outport41_Wavelength) {
        this.outport41_Wavelength = outport41_Wavelength;
    }

    public List<Short> getOutport42_Wavelength() {
        return outport42_Wavelength;
    }

    public void setOutport42_Wavelength(List<Short> outport42_Wavelength) {
        this.outport42_Wavelength = outport42_Wavelength;
    }

    public List<Short> getOutport43_Wavelength() {
        return outport43_Wavelength;
    }

    public void setOutport43_Wavelength(List<Short> outport43_Wavelength) {
        this.outport43_Wavelength = outport43_Wavelength;
    }

    public List<Short> getOutport44_Wavelength() {
        return outport44_Wavelength;
    }

    public void setOutport44_Wavelength(List<Short> outport44_Wavelength) {
        this.outport44_Wavelength = outport44_Wavelength;
    }
}
