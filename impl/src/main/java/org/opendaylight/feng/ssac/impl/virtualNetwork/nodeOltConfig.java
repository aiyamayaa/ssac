/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;

import java.util.HashMap;
import java.util.Map;

public class nodeOltConfig {
    private Short oltId;
    Short flag;
    private Map<Short,Short> lightPathCapacityUp;
    private Map<Short,Short> lightPathCapacityDown;

    private Map<Short,Integer> lightPathCapacityUp1;
    private Map<Short,Integer> lightPathCapacityDown1;
    public nodeOltConfig(){}




    public nodeOltConfig(Short oltId, Map<Short, Integer> lightPathCapacityUp1, Map<Short, Integer> lightPathCapacityDown1) {
        this.oltId = oltId;
        this.lightPathCapacityUp1 = lightPathCapacityUp1;
        this.lightPathCapacityDown1 = lightPathCapacityDown1;
        Map<Short,Short> kk = new HashMap<>();
        Map<Short,Short> kkk = new HashMap<>();
        if(lightPathCapacityUp1!=null){
            for(Short key:lightPathCapacityUp1.keySet()){
                kk.put(key,lightPathCapacityUp1.get(key).shortValue());
            }
            this.lightPathCapacityUp = kk;
        }
        if(lightPathCapacityDown1!=null){
            for(Short key:lightPathCapacityDown1.keySet()){
                kkk.put(key,lightPathCapacityDown1.get(key).shortValue());
            }
            this.lightPathCapacityDown =kkk;
        }

    }

    public Short getOltId() {
        return oltId;
    }

    public void setOltId(Short oltId) {
        this.oltId = oltId;
    }

    public Map<Short, Short> getLightPathCapacityUp() {
        return lightPathCapacityUp;
    }

    public void setLightPathCapacityUp(Map<Short, Short> lightPathCapacityUp) {
        this.lightPathCapacityUp = lightPathCapacityUp;
    }

    public Map<Short, Short> getLightPathCapacityDown() {
        return lightPathCapacityDown;
    }

    public void setLightPathCapacityDown(Map<Short, Short> lightPathCapacityDown) {
        this.lightPathCapacityDown = lightPathCapacityDown;
    }

    public Map<Short, Integer> getLightPathCapacityUp1() {
        return lightPathCapacityUp1;
    }

    public void setLightPathCapacityUp1(Map<Short, Integer> lightPathCapacityUp1) {
        this.lightPathCapacityUp1 = lightPathCapacityUp1;
    }

    public Map<Short, Integer> getLightPathCapacityDown1() {
        return lightPathCapacityDown1;
    }

    public void setLightPathCapacityDown1(Map<Short, Integer> lightPathCapacityDown1) {
        this.lightPathCapacityDown1 = lightPathCapacityDown1;
    }
}
