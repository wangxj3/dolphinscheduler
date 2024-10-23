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

package org.apache.dolphinscheduler.e2e.cases.cluster;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.dolphinscheduler.e2e.cases.workflow.BaseWorkflowE2ETest;
import org.apache.dolphinscheduler.e2e.core.DolphinScheduler;
import org.apache.dolphinscheduler.e2e.core.WebDriverHolder;
import org.apache.dolphinscheduler.e2e.pages.LoginPage;
import org.apache.dolphinscheduler.e2e.pages.project.ProjectPage;
import org.apache.dolphinscheduler.e2e.pages.project.workflow.TaskInstanceTab;
import org.apache.dolphinscheduler.e2e.pages.project.workflow.WorkflowDefinitionTab;
import org.apache.dolphinscheduler.e2e.pages.project.workflow.WorkflowForm;
import org.apache.dolphinscheduler.e2e.pages.project.workflow.WorkflowInstanceTab;
import org.apache.dolphinscheduler.e2e.pages.project.workflow.task.ShellTaskForm;
import org.apache.dolphinscheduler.e2e.pages.security.SecurityPage;
import org.apache.dolphinscheduler.e2e.pages.security.TenantPage;
import org.apache.dolphinscheduler.e2e.pages.security.UserPage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junitpioneer.jupiter.DisableIfTestFails;

@TestMethodOrder(MethodOrderer.MethodName.class)
@DolphinScheduler(composeFiles = {"docker/cluster-test/docker-compose-api.yaml",
        "docker/cluster-test/docker-compose-master.yaml", "docker/cluster-test/docker-compose-worker.yaml"})
@DisableIfTestFails
public class ClusterShellTaskE2ETest extends BaseWorkflowE2ETest {

    @BeforeAll
    public static void setup() {
        System.out.println("-----start setup------");
        browser = WebDriverHolder.getWebDriver();
        System.out.println("-----start setup1------");

        TenantPage tenantPage = new LoginPage(browser)
                .login(adminUser)
                .goToNav(SecurityPage.class)
                .goToTab(TenantPage.class);
        System.out.println("-----start setup2------");

        if (tenantPage.tenants().stream().noneMatch(tenant -> tenant.tenantCode().equals(adminUser.getTenant()))) {
            System.out.println("-----start setup3------");
            tenantPage
                    .create(adminUser.getTenant())
                    .goToNav(SecurityPage.class)
                    .goToTab(UserPage.class)
                    .update(adminUser);
        }
        System.out.println("-----start setup4------");

        tenantPage
                .goToNav(ProjectPage.class)
                .createProjectUntilSuccess(projectName);
        System.out.println("-----start setup5------");
    }

    @Test
    void testRunShellTasks_SuccessCase() {
        System.out.println("-----start------");
        WorkflowDefinitionTab workflowDefinitionPage =
                new ProjectPage(browser)
                        .goToNav(ProjectPage.class)
                        .goTo(projectName)
                        .goToTab(WorkflowDefinitionTab.class);
        System.out.println("-----start1------");
        // todo: use yaml to define the workflow
        String workflowName = "SslSuccessCase";
        System.out.println("-----start2------");
        String taskName = "SslShellSuccess";
        System.out.println("-----start3------");
        workflowDefinitionPage
                .createWorkflow()
                .<ShellTaskForm>addTask(WorkflowForm.TaskType.SHELL)
                .script("echo hello world\n")
                .name(taskName)
                .submit()

                .submit()
                .name(workflowName)
                .submit();
        System.out.println("-----start4------");

        untilWorkflowDefinitionExist(workflowName);
        System.out.println("-----start5------");

        workflowDefinitionPage.publish(workflowName);
        System.out.println("-----start6------");

        runWorkflow(workflowName);
        System.out.println("-----start7------");
        untilWorkflowInstanceExist(workflowName);
        System.out.println("-----start8------");
        WorkflowInstanceTab.Row workflowInstance = untilWorkflowInstanceSuccess(workflowName);
        System.out.println("-----start9------");
        assertThat(workflowInstance.executionTime()).isEqualTo(1);
        System.out.println("-----start10------");

        TaskInstanceTab.Row taskInstance = untilTaskInstanceSuccess(workflowName, taskName);
        System.out.println("-----start11------");
        assertThat(taskInstance.retryTimes()).isEqualTo(0);
    }

}
