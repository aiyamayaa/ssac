/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.virtualNetwork;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class nodeDcConfig {
    private Short dcId;
    private Short resourceTypeFlag;   //0=du  1-cu
    private Short sliceId;
    private Short vDcId=0;  //DU-CU
    private Long vNodeCapacity;
    private Map<Short,Short> lightPathCapacity;     //<wavelength,slot>
    private Map<Short,Integer> lightPathCapacity1;

    private static final Logger LOGGER = LoggerFactory.getLogger(nodeDcConfig.class);

    public nodeDcConfig(){}


    public nodeDcConfig(Short dcId, Short resourceTypeFlag, Short sliceId, Short vDcId, Long vNodeCapacity,
                        Map<Short, Integer> lightPathCapacity1) {
        LOGGER.info("----------nodeDcConfig----------{}"+dcId+"-resourceTypeFlag-"+resourceTypeFlag+"-sliceId-"+sliceId
                +"-vDcId-"+vDcId+"-vNodeCapacity-"+vNodeCapacity);
        this.dcId = dcId;
        this.resourceTypeFlag = resourceTypeFlag;
        this.sliceId = sliceId;
        this.vDcId = vDcId;
        this.vNodeCapacity = vNodeCapacity;
        this.lightPathCapacity1 = lightPathCapacity1;
        LOGGER.info("----------nodeDcConfig----------{}"+lightPathCapacity1);
        if(lightPathCapacity1!=null){
            Map<Short,Short> ff = new HashMap<>();
            for(Short key:lightPathCapacity1.keySet()){

                Integer value1 = lightPathCapacity1.get(key);
                Short value = value1.shortValue();
                ff.put(key,value);
                LOGGER.info("----------nodeDcConfig----------{}"+key);
            }
            LOGGER.info("--------nodeDcConfig--------{}"+ff);
            this.lightPathCapacity = ff;
            LOGGER.info("----------nodeDcConfig----------{}"+lightPathCapacity1);
        }


    }


    public Short getDcId() {
        return dcId;
    }

    public void setDcId(Short dcId) {
        this.dcId = dcId;
    }

    public Short getResourceTypeFlag() {
        return resourceTypeFlag;
    }

    public void setResourceTypeFlag(Short resourceTypeFlag) {
        this.resourceTypeFlag = resourceTypeFlag;
    }

    public Short getSliceId() {
        return sliceId;
    }

    public void setSliceId(Short sliceId) {
        this.sliceId = sliceId;
    }

    public Short getvDcId() {
        return vDcId;
    }

    public void setvDcId(Short vDcId) {
        this.vDcId = vDcId;
    }

    public Long getvNodeCapacity() {
        return vNodeCapacity;
    }

    public void setvNodeCapacity(Long vNodeCapacity) {
        this.vNodeCapacity = vNodeCapacity;
    }

    public Map<Short, Integer> getLightPathCapacity1() {
        return lightPathCapacity1;
    }

    public void setLightPathCapacity1(Map<Short, Integer> lightPathCapacity1) {
        this.lightPathCapacity1 = lightPathCapacity1;

    }

    public Map<Short, Short> getLightPathCapacity() {
        return lightPathCapacity;
    }

    public void setLightPathCapacity(Map<Short, Short> lightPathCapacity) {
        this.lightPathCapacity = lightPathCapacity;
    }
}
