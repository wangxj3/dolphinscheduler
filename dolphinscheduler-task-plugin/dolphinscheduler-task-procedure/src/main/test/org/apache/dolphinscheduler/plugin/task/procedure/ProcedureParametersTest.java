package org.apache.dolphinscheduler.plugin.task.procedure;

import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.plugin.task.api.enums.DataType;
import org.apache.dolphinscheduler.plugin.task.api.enums.Direct;
import org.apache.dolphinscheduler.plugin.task.api.model.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangxj
 * @date 2024/8/6 9:37
 */
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
        procedureParameters.dealOutParam4Procedure("path/testNew","test");
    }
}
