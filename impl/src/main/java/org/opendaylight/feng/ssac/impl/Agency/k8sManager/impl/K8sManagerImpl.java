/*
 * Copyright © 2016 Jiaxin,Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.feng.ssac.impl.Agency.k8sManager.impl;

import org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ.K8sDeploymentBIZ;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ.K8sNamespaceBIZ;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.BIZ.K8sPodBIZ;
import org.opendaylight.feng.ssac.impl.Agency.k8sManager.utils.ConfigUtils;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import java.util.HashMap;
import java.util.Map;

public class K8sManagerImpl {
//    public Future<RpcResult<CreateDeploymentOutput>> createDeployment(CreateDeploymentInput input) {
//
//        CreateDeploymentOutputBuilder outputBuilder = new CreateDeploymentOutputBuilder();
//        K8sDeploymentBIZ dpBIZ = new K8sDeploymentBIZ();
//
//        String name = input.getName();
//        String selector = input.getSelector();
//        String namespace = input.getNamespace();
//        String request_cpu = input.getQuota().getRequests().getCpu();
//        String request_memory = input.getQuota().getRequests().getMemory();
//        String limits_cpu = input.getQuota().getLimits().getCpu();
//        String limits_memory = input.getQuota().getLimits().getMemory();
//        Map<String, String> config = new HashMap<>();
//        config.put("name", name);
//        config.put("selector",selector);
//        config.put("namespace",namespace);
//        config.put("request_cpu", request_cpu);
//        config.put("request_memory", request_memory);
//        config.put("limits_cpu", limits_cpu);
//        config.put("limits_memory", limits_memory);
//        String result = dpBIZ.createDeployment(config, ConfigUtils.DEPLYMENTPATH);
//        outputBuilder.setResult(result);
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//
//    public Future<RpcResult<UpdateDeploymentOutput>> updateDeployment(UpdateDeploymentInput input) {
//
//        UpdateDeploymentOutputBuilder outputBuilder = new UpdateDeploymentOutputBuilder();
//        K8sDeploymentBIZ dpBIZ = new K8sDeploymentBIZ();
//        String name = input.getName();
//        String selector = input.getSelector();
//        String namespace = input.getNamespace();
//        String request_cpu = input.getQuota().getRequests().getCpu();
//        String request_memory = input.getQuota().getRequests().getMemory();
//        String limits_cpu = input.getQuota().getLimits().getCpu();
//        String limits_memory = input.getQuota().getLimits().getMemory();
//        Map<String, String> config = new HashMap<>();
//        config.put("name", name);
//        config.put("selector",selector);
//        config.put("namespace",namespace);
//        config.put("request_cpu", request_cpu);
//        config.put("request_memory", request_memory);
//        config.put("limits_cpu", limits_cpu);
//        config.put("limits_memory", limits_memory);
//        String result = dpBIZ.updateDeployment(config,ConfigUtils.DEPLYMENTPATH);
//        outputBuilder.setResult(result);
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//
//    public Future<RpcResult<DeleteDeploymentOutput>> deleteDeployment(DeleteDeploymentInput input) {
//
//        DeleteDeploymentOutputBuilder outputBuilder = new DeleteDeploymentOutputBuilder();
//        boolean flag = false;
//        K8sDeploymentBIZ dpBIZ = new K8sDeploymentBIZ();
//        flag = dpBIZ.deleteDeployment(input.getNamespace(),input.getName());
//        if(flag){
//            outputBuilder.setResult("success!");
//        }else{
//            outputBuilder.setResult("failed!");
//        }
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//    public Future<RpcResult<DeletePodOutput>> deletePod(DeletePodInput input) {
//        DeletePodOutputBuilder outputBuilder = new DeletePodOutputBuilder();
//        boolean flag = false;
//        K8sPodBIZ PODBiz = new K8sPodBIZ();
//        flag = PODBiz.deletePod(input.getNamespace(),input.getName());
//        if(flag){
//            outputBuilder.setResult("success");
//        }else {
//            outputBuilder.setResult("failed");
//        }
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//    public Future<RpcResult<DeleteNamespaceOutput>> deleteNamespace(DeleteNamespaceInput input) {
//
//        DeleteNamespaceOutputBuilder outputBuilder = new DeleteNamespaceOutputBuilder();
//        boolean flag = false;
//        K8sNamespaceBIZ NSBiz = new K8sNamespaceBIZ();
//        flag = NSBiz.deteleNamespace(input.getName());
//        if(flag){
//            outputBuilder.setResult("success");
//        }else {
//            outputBuilder.setResult("failed");
//        }
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//    public Future<RpcResult<CreateNamespaceOutput>> createNamespace(CreateNamespaceInput input) {
//        CreateNamespaceOutputBuilder outputBuilder = new CreateNamespaceOutputBuilder();
//        String name = input.getName();
//        Map<String, String> config = new HashMap<>();
//        config.put("name", name);
//        K8sNamespaceBIZ nsBiz = new K8sNamespaceBIZ();
//
//        Map<String, String> result = nsBiz.CreateNamespaces(ConfigUtils.NSPATH,config);
//        outputBuilder.setResult(result.get(name));
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
//
//
//    public Future<RpcResult<CreatePodOutput>> createPod(CreatePodInput input) {
//
//        CreatePodOutputBuilder outputBuilder = new CreatePodOutputBuilder();
//
//        String name = input.getName();
//        String selector = input.getSelector();
//        String namespace = input.getNamespace();
//        String request_cpu = input.getQuota().getRequests().getCpu();
//        String request_memory = input.getQuota().getRequests().getMemory();
//        String limits_cpu = input.getQuota().getLimits().getCpu();
//        String limits_memory = input.getQuota().getLimits().getMemory();
//        String url = input.getUrl();
//        String jobType = input.getJobType();
//        Map<String, String> config = new HashMap<>();
//        config.put("name", name);
//        config.put("selector",selector);
//        config.put("namespace",namespace);
//        config.put("request_cpu", request_cpu);
//        config.put("request_memory", request_memory);
//        config.put("limits_cpu", limits_cpu);
//        config.put("limits_memory", limits_memory);
//        config.put("url",url);
//        config.put("jobType",jobType);
//        K8sPodBIZ podBIZ = new K8sPodBIZ();
//        String result = podBIZ.createPod(config);
//        outputBuilder.setResult(result);
//        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
//    }
}

