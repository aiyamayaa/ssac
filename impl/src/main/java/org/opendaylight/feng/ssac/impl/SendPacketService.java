/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl;

import java.util.List;
import java.util.Map;

public interface SendPacketService {

    public void sendvAauConfig(Short flag,Short SliceId,Short vAauId,Integer antenna,Short aauId);

    public void sendDuConfig(Short flag,Short SliceID,Short DuID,Short DcID,Integer capacity,Integer LinkId, Map<Short,Integer> vlinkCapacity);
    public void sendCuConfig(Short flag,Short SliceID,Short CuId,Short DcID,Integer capacity,Integer LinkId,Map<Short,Integer> vlinkCapacity);
    public void sendRoadmConfig( Short nodeId, List<Short> outport11_Wavelength, List<Short> outport12_Wavelength,
                                List<Short> outport13_Wavelength, List<Short> outport14_Wavelength,
                                List<Short> outport21_Wavelength, List<Short> outport22_Wavelength,
                                List<Short> outport23_Wavelength, List<Short> outport24_Wavelength,
                                List<Short> outport31_Wavelength, List<Short> outport32_Wavelength,
                                List<Short> outport33_Wavelength, List<Short> outport34_Wavelength,
                                List<Short> outport41_Wavelength, List<Short> outport42_Wavelength,
                                List<Short> outport43_Wavelength, List<Short> outport44_Wavelength);
    public void sendvLinkConfig(Short flag,Short SliceID, Integer LinkId,Map<Short,Integer> vlinkCapacity);
    public void sendVpath(Short flag,Short sliceId, Short startNode, Short destNode, List<Short> pathNodeId,
                          Map<Short, Integer> lightPathCapacity); //save the lightpath
    public void showSlice();
    public void showNetResource();
    //new
    public void sendOolink(Short sliceId,Short startNode,Short destNode,Short waveNumbet);
    public void sendPath(Short sliceId,Short[] path);

    public void deleteSlice(Short sliceId);

    public void send37aau();
    public void send20roadm();

}
