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

package org.apache.dolphinscheduler.plugin.task.procedure;

import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.plugin.task.api.enums.DataType;
import org.apache.dolphinscheduler.plugin.task.api.enums.Direct;
import org.apache.dolphinscheduler.plugin.task.api.model.Property;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcedureParametersTest {

    @Test
    public void buildPythonExecuteCommand() throws Exception {
        ProcedureParameters procedureParameters = new ProcedureParameters();
        Map<String, Property> outProperty = new HashMap<>();
        outProperty.put("test", new Property("test", Direct.OUT, DataType.FILE, ""));
        procedureParameters.setOutProperty(outProperty);
        Property property = new Property("test", Direct.OUT, DataType.FILE, "path/testOld");
        Property property2 = new Property("test2", Direct.OUT, DataType.FILE, "path/testOld2");
        List<Property> list = new ArrayList<>();
        list.add(property);
        list.add(property2);
        procedureParameters.setVarPool(JSONUtils.toJsonString(list));
        procedureParameters.dealOutParam4Procedure("path/testNew", "test");
    }
}
