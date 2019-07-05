/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.binding.api.NotificationService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.feng.ssac.impl.TransportResourceManager.TedHandler;
import org.opendaylight.feng.ssac.impl.TransportResourceManager.TrConfig;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.aaufeaturerequest.rev180606.PacketBniAauFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.dcfeaturerequest.rev180606.PacketBniDcFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.eswitchfeaturerequest.rev180606.PacketBniEswitchFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.oltfeaturerequest.rev180606.PacketBniOltFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.roadmfeaturerequest.rev180314.PacketBniRoadmFeatureRequestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setaauconfig.rev180606.PacketBniSetAauConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setdcconfig.rev180606.PacketBniSetDcConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.seteswitchconfig.rev180606.PacketBniSetEswitchConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setoltconfig.rev180606.PacketBniSetOltConfigService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.bni.setroadmconfig.rev180606.PacketBniSetRoadmConfigService;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.NotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SsacProvider {

    private static final Logger LOG = LoggerFactory.getLogger(SsacProvider.class);

    private final DataBroker dataBroker;
    private TedHandler tedHandler;

    private final NotificationPublishService notificationPublishService;
    private final NotificationService notificationService;

    private ListenerRegistration<NotificationListener> registration = null;


    private final RpcProviderRegistry rpcRegistry;

    public SsacProvider(final DataBroker dataBroker,
                        final NotificationPublishService notificationPublishService,
                        final NotificationService notificationService,
                        final RpcProviderRegistry rpcRegistry) {
        this.dataBroker = dataBroker;
        this.notificationPublishService = notificationPublishService;
        this.notificationService = notificationService;
        this.rpcRegistry = rpcRegistry;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("SsacProvider Session Initiated");
        TrConfig.bniSetDcConfigService = rpcRegistry.getRpcService(PacketBniSetDcConfigService.class);
        TrConfig.bniSetAauConfigService = rpcRegistry.getRpcService(PacketBniSetAauConfigService.class);
        TrConfig.bniSetEswitchConfigService = rpcRegistry.getRpcService(PacketBniSetEswitchConfigService.class);
        TrConfig.bniSetOltConfigService = rpcRegistry.getRpcService(PacketBniSetOltConfigService.class);
        TrConfig.bniSetRoadmConfigService = rpcRegistry.getRpcService(PacketBniSetRoadmConfigService.class);

        TrConfig.bniAauFeatureRequestService = rpcRegistry.getRpcService(PacketBniAauFeatureRequestService.class);
        TrConfig.bniDcFeatureRequestService = rpcRegistry.getRpcService(PacketBniDcFeatureRequestService.class);
        TrConfig.bniEswitchFeatureRequestService = rpcRegistry.getRpcService(PacketBniEswitchFeatureRequestService.class);
        TrConfig.bniOltFeatureRequestService = rpcRegistry.getRpcService(PacketBniOltFeatureRequestService.class);
        TrConfig.bniRoadmFeatureRequestService = rpcRegistry.getRpcService(PacketBniRoadmFeatureRequestService.class);

        if(notificationService != null){
            LOG.info("NotificationSercive is"+notificationService.toString());
            tedHandler = new TedHandler();
            registration = notificationService.registerNotificationListener(tedHandler);
        }

    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("SsacProvider Closed");
        if(registration != null){
            registration.close();

        }
    }
}