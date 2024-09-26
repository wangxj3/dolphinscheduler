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

package org.apache.dolphinscheduler.server.worker.rpc;

import org.apache.dolphinscheduler.extract.base.config.NettySslConfig;
import org.apache.dolphinscheduler.server.worker.config.WorkerConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkerRpcServerTest {

    private final WorkerRpcServer workerRpcServer = new WorkerRpcServer(new WorkerConfig(), new NettySslConfig());

    @Test
    void testStart() {
        Assertions.assertDoesNotThrow(workerRpcServer::start);
    }

    @Test
    void testClose() {
        Assertions.assertDoesNotThrow(workerRpcServer::close);
    }

}
