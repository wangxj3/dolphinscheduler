/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.dolphinscheduler.e2e.cases;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.dolphinscheduler.e2e.core.DolphinScheduler;
import org.apache.dolphinscheduler.e2e.core.WebDriverWaitFactory;
import org.apache.dolphinscheduler.e2e.pages.LoginPage;
import org.apache.dolphinscheduler.e2e.pages.datasource.DataSourcePage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DisableIfTestFails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@DolphinScheduler(composeFiles = "docker/datasource-mysql/docker-compose.yaml")
@DisableIfTestFails
public class MysqlDataSourceE2ETest {

    private static RemoteWebDriver browser;

    private static final String tenant = System.getProperty("user.name");

    private static final String user = "admin";

    private static final String password = "dolphinscheduler123";

    private static final String dataSourceType = "MYSQL";

    private static final String dataSourceName = "mysql_test";

    private static final String dataSourceDescription = "mysql_test";

    private static final String ip = "mysql";

    private static final String port = "3306";

    private static final String userName = "root";

    private static final String mysqlPassword = "123456";

    private static final String database = "mysql";

    private static final String jdbcParams = "{\"useSSL\": false}";

    @BeforeAll
    public static void setup() {
        new LoginPage(browser)
                .login(user, password)
                .goToNav(DataSourcePage.class);
    }

    @Test
    @Order(10)
    void testCreateMysqlDataSource() {
        final DataSourcePage page = new DataSourcePage(browser);

        page.createDataSource(dataSourceType, dataSourceName, dataSourceDescription, ip, port, userName, mysqlPassword,
                database, jdbcParams);

        WebDriverWaitFactory.createWebDriverWait(page.driver()).until(ExpectedConditions.invisibilityOfElementLocated(
                new By.ByClassName("dialog-create-data-source")));

        Awaitility.await().untilAsserted(() -> assertThat(page.dataSourceItemsList())
                .as("DataSource list should contain newly-created database")
                .extracting(WebElement::getText)
                .anyMatch(it -> it.contains(dataSourceName)));
    }

    @Test
    @Order(20)
    void testDeleteMysqlDataSource() {
        final DataSourcePage page = new DataSourcePage(browser);

        page.delete(dataSourceName);

        Awaitility.await().untilAsserted(() -> {
            browser.navigate().refresh();

            assertThat(
                    page.dataSourceItemsList()).noneMatch(
                            it -> it.getText().contains(dataSourceName));
        });
    }

}
