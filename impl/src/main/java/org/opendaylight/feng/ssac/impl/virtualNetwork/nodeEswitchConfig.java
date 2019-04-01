/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;

import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class nodeEswitchConfig {
    private Short eswitchId;

    private Map<Short,Short> lightPathCapacityUp;
    private Map<Short,Short> LightPathCapacityDown;

    private Map<Short,Integer> lightPathCapacityUp1;
    private Map<Short,Integer> LightPathCapacityDown1;
    public nodeEswitchConfig(){}
    private static final Logger LOGGER = LoggerFactory.getLogger(nodeEswitchConfig.class);
    public nodeEswitchConfig(Short eswitchId, Map<Short, Integer> lightPathCapacityUp1, Map<Short, Integer> lightPathCapacityDown1) {
         LOGGER.info("----------nodeEswitchConfig----------{}"+eswitchId+"-lightPathCapacityUp1-"+lightPathCapacityUp1+"-lightPathCapacityDown1-"+
                lightPathCapacityDown1);
        this.eswitchId = eswitchId;
        this.lightPathCapacityUp1 = lightPathCapacityUp1;
        this.LightPathCapacityDown1 = lightPathCapacityDown1;
        Map<Short,Short> kk = new HashMap<>();
        Map<Short,Short> kkk = new HashMap<>();
        LOGGER.info("---------nodeEswitchConfig-------1----{}");
        if(lightPathCapacityUp1 != null){
            for (Short key:lightPathCapacityUp1.keySet()) {
                kk.put(key,(lightPathCapacityUp1.get(key)).shortValue());
            }
            this.lightPathCapacityUp = kk;
        }
        if(lightPathCapacityDown1 != null){
            LOGGER.info("-------nodeEswitchConfig--------2-----{}");
            for (Short key:LightPathCapacityDown1.keySet()
                    ) {
                kkk.put(key,LightPathCapacityDown1.get(key).shortValue());
            }
            this.LightPathCapacityDown = kkk;
        }

        LOGGER.info("------nodeEswitchConfig--------end------{}");
    }

    public nodeEswitchConfig(Map<Short, Short> lightPathCapacityUp, Map<Short, Short> lightPathCapacityDown) {
        this.lightPathCapacityUp = lightPathCapacityUp;
        this.LightPathCapacityDown = lightPathCapacityDown;
    }

    public Short getEswitchId() {
        return eswitchId;
    }

    public void setEswitchId(Short eswitchId) {
        this.eswitchId = eswitchId;
    }

    public Map<Short, Short> getLightPathCapacityDown() {
        return LightPathCapacityDown;
    }

    public void setLightPathCapacityDown(Map<Short, Short> lightPathCapacityDown) {
        LightPathCapacityDown = lightPathCapacityDown;
    }

    public Map<Short, Short> getLightPathCapacityUp() {
        return lightPathCapacityUp;
    }

    public void setLightPathCapacityUp(Map<Short, Short> lightPathCapacityUp) {
        this.lightPathCapacityUp = lightPathCapacityUp;
    }

    public Map<Short, Integer> getLightPathCapacityUp1() {
        return lightPathCapacityUp1;
    }

    public void setLightPathCapacityUp1(Map<Short, Integer> lightPathCapacityUp1) {
        this.lightPathCapacityUp1 = lightPathCapacityUp1;
    }

    public Map<Short, Integer> getLightPathCapacityDown1() {
        return LightPathCapacityDown1;
    }

    public void setLightPathCapacityDown1(Map<Short, Integer> lightPathCapacityDown1) {
        LightPathCapacityDown1 = lightPathCapacityDown1;
    }
}
