/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.southboundInterface;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;

import java.util.List;
import java.util.Map;

public interface LinkPropertyService {


    public Map<Short,NodeConnectorRef> getNodeMapIgress();

    public List<String> getAauResource();
    public List<String> getDcResource();
    public List<String> getEswitchResource();
    public List<String> getRoadmResource();
    public List<String> getOltResource();

    public List<String> getLinkResource();

    //for print
    public String getNetNode();
    public String getNetLinkresource();
    public String getPath();


}
