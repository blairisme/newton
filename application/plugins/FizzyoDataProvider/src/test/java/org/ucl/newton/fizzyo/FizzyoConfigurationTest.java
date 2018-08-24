package org.ucl.newton.fizzyo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FizzyoConfigurationTest {
    public FizzyoConfiguration config;
    public PluginHostStorage storage;
    public PluginHostContext context;
    @Before
    public void setUp() throws IOException {
        String[] configs = {"clientId","syncSecret","1514764800","1533772800","requestData"};
        config = new FizzyoConfiguration();
        config.setValues(configs);

        context = mock(PluginHostContext.class);
        storage = mock(PluginHostStorage.class);
        when(storage.getOutputStream(anyString())).thenReturn(new FileOutputStream("test"));
        when(context.getStorage()).thenReturn(storage);
        config.setContext(context);
    }
    @Test
    public void getValuesTest(){
        Map<String,String> values = config.getValues();
        Assert.assertEquals("clientId",values.get("clientId"));
        Assert.assertEquals("syncSecret",values.get("syncSecret"));
        Assert.assertEquals("2018-01-01",values.get("startDate"));
        Assert.assertEquals("2018-08-09",values.get("endDate"));
        Assert.assertEquals("requestData",values.get("requestData"));
    }

    @Test
    public void setValuesTest() throws IOException{
        Map<String,List<String>> values = new HashMap<>();
        values.put("clientId",buildList("clientId"));
        values.put("syncSecret",buildList("syncSecret"));
        values.put("startDate",buildList("2018-01-01"));
        values.put("endDate",buildList("2018-08-09"));
        values.put("requestData",buildList("requestData"));
        config.setValues(values);

        verify(context,times(1)).getStorage();
        verify(storage,times(1)).getOutputStream(anyString());

        FileUtils.delete(new File("test"));

    }

    private List<String> buildList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }
}
