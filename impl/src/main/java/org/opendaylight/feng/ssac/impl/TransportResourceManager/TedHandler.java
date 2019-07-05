/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.TransportResourceManager;

import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aauconfigreply.rev180606.PacketBniAauConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aauconfigreply.rev180606.PacketBniAauConfigReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aaufeaturereply.rev180606.PacketBniAauFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aaufeaturereply.rev180606.PacketBniAauFeatureReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcconfigreply.rev180122.PacketBniDcConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcconfigreply.rev180122.PacketBniDcConfigReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcfeaturereply.rev180122.PacketBniDcFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcfeaturereply.rev180122.PacketBniDcFeatureReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchconfigreply.rev180606.PacketBniEswitchConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchconfigreply.rev180606.PacketBniEswitchConfigReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchfeaturereply.rev180606.PacketBniEswitchFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchfeaturereply.rev180606.PacketBniEswitchFeatureReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltconfigreply.rev180606.PacketBniOltConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltconfigreply.rev180606.PacketBniOltConfigReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltfeaturereply.rev180606.PacketBniOltFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltfeaturereply.rev180606.PacketBniOltFeatureReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmconfigreply.rev180606.PacketBniRoadmConfigReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmconfigreply.rev180606.PacketBniRoadmConfigReplyListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmfeaturereply.rev180606.PacketBniRoadmFeatureReply;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmfeaturereply.rev180606.PacketBniRoadmFeatureReplyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.feng.ssac.impl.southboundInterface.LinkProperty;

public class TedHandler implements PacketBniRoadmFeatureReplyListener,PacketBniRoadmConfigReplyListener,PacketBniDcFeatureReplyListener,
        PacketBniDcConfigReplyListener,PacketBniOltFeatureReplyListener,PacketBniOltConfigReplyListener,PacketBniAauFeatureReplyListener,
        PacketBniAauConfigReplyListener,PacketBniEswitchFeatureReplyListener,PacketBniEswitchConfigReplyListener{

    LinkProperty linkProperty = new LinkProperty();
    private static final Logger LOG = LoggerFactory.getLogger(TedHandler.class);



    @Override
    public void onPacketBniAauFeatureReply(PacketBniAauFeatureReply notification) {
        LOG.info("--jiaxinTed---onPacketBniAauFeatureReply--report(notification)----{}",notification);
        linkProperty.AauHandler(notification);
    }
    @Override
    public void onPacketBniDcFeatureReply(PacketBniDcFeatureReply notification){
        LOG.info("--jiaxinTed--onPacketBniDcFeatureReply---report(notification)----{}",notification);
        linkProperty.DcHandler(notification);
    }
    @Override
    public void onPacketBniOltFeatureReply(PacketBniOltFeatureReply notification) {
        LOG.info("--jiaxinTed--onPacketBniOltFeatureReply---report(notification)----{}",notification);
        linkProperty.OltHandler(notification);
    }
    @Override
    public void onPacketBniRoadmFeatureReply(PacketBniRoadmFeatureReply notification) {
        LOG.info("--jiaxinTed--onPacketBniRoadmFeatureReply---report(notification)----{}",notification);
        linkProperty.RoadmHandler(notification);
    }
    @Override
    public void onPacketBniEswitchFeatureReply(PacketBniEswitchFeatureReply notification) {
        LOG.info("--jiaxinTed--onPacketBniEswitchFeatureReply---report(notification)----{}",notification);
        linkProperty.EswitchHandler(notification);
    }


    @Override
    public void onPacketBniAauConfigReply(PacketBniAauConfigReply notification) {
        LOG.info("--jiaxinTed--onPacketBniAauConfigReply---report(notification)----{}",notification);
        linkProperty.AauReplyHandler(notification);
    }
    @Override
    public void onPacketBniDcConfigReply(PacketBniDcConfigReply notification) {
        LOG.info("--jiaxinTed--onPacketBniDcConfigReply---report(notification)----{}",notification);
        linkProperty.DcReplyHandler(notification);
    }
    @Override
    public void onPacketBniOltConfigReply(PacketBniOltConfigReply notification) {
        LOG.info("--jiaxinTed--onPacketBniOltConfigReply---report(notification)----{}",notification);
        linkProperty.OltReplyHandler(notification);
    }
    @Override
    public void onPacketBniEswitchConfigReply(PacketBniEswitchConfigReply notification) {
        LOG.info("--jiaxinTed--onPacketBniEswitchConfigReply---report(notification)----{}",notification);
        linkProperty.EswitchReplyHandler(notification);
    }
    @Override
    public void onPacketBniRoadmConfigReply(PacketBniRoadmConfigReply notification) {
        LOG.info("--jiaxinTed--onPacketBniRoadmConfigReply---report(notification)----{}",notification);
        linkProperty.RoadmReplyHandler(notification);
    }

}
