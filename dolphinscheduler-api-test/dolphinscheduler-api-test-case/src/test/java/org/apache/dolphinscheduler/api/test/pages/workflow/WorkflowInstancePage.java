/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.api.test.pages.workflow;

import org.apache.dolphinscheduler.api.test.core.Constants;
import org.apache.dolphinscheduler.api.test.entity.HttpResponse;
import org.apache.dolphinscheduler.api.test.utils.RequestClient;
import org.apache.dolphinscheduler.dao.entity.User;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class WorkflowInstancePage {

    private String sessionId;

    public HttpResponse queryWorkflowInstancesByTriggerCode(User loginUser, long projectCode, long triggerCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginUser", loginUser);
        params.put("triggerCode", triggerCode);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SESSION_ID_KEY, sessionId);

        RequestClient requestClient = new RequestClient();
        String url = String.format("/projects/%s/workflow-instances/trigger", projectCode);
        return requestClient.get(url, headers, params);
    }

    public HttpResponse queryWorkflowInstanceList(User loginUser, long projectCode, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginUser", loginUser);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SESSION_ID_KEY, sessionId);

        RequestClient requestClient = new RequestClient();
        String url = String.format("/projects/%s/workflow-instances", projectCode);
        return requestClient.get(url, headers, params);
    }

    public HttpResponse queryTaskListByWorkflowInstanceId(User loginUser, long projectCode, long workflowInstanceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginUser", loginUser);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SESSION_ID_KEY, sessionId);
        RequestClient requestClient = new RequestClient();
        String url = String.format("/projects/%s/workflow-instances/%s/tasks", projectCode, workflowInstanceId);
        return requestClient.get(url, headers, params);
    }

    public HttpResponse queryWorkflowInstanceById(User loginUser, long projectCode, long workflowInstanceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginUser", loginUser);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SESSION_ID_KEY, sessionId);

        RequestClient requestClient = new RequestClient();
        String url = String.format("/projects/%s/workflow-instances/%s", projectCode, workflowInstanceId);
        return requestClient.get(url, headers, params);
    }

    public HttpResponse deleteWorkflowInstanceById(User loginUser, long projectCode, long workflowInstanceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginUser", loginUser);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.SESSION_ID_KEY, sessionId);

        RequestClient requestClient = new RequestClient();
        String url = String.format("/projects/%s/workflow-instances/%s", projectCode, workflowInstanceId);
        return requestClient.delete(url, headers, params);
    }

}
