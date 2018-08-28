package org.ucl.newton.drepublisher;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class FTPConfigTest {
    public FTPConfig config;
    public PluginHostStorage storage;
    public PluginHostContext context;
    @Before
    public void setUp() {
        String[] configs = {"hostName","userName","userPassword","21"};
        config = new FTPConfig();
        config.setValues(configs);

        context = Mockito.mock(PluginHostContext.class);
        storage = Mockito.mock(PluginHostStorage.class);

    }
    @Test
    public void getValuesTest(){
        Map<String,String> values = config.getValues();
        Assert.assertEquals("hostName",values.get("hostName"));
        Assert.assertEquals("userName",values.get("userName"));
        Assert.assertEquals("userPassword",values.get("userPassword"));
        Assert.assertEquals("21",values.get("port"));
    }

    @Test
    public void setValuesTest() throws IOException{
        Mockito.when(storage.getOutputStream(anyString())).thenReturn(new FileOutputStream("test"));
        Mockito.when(context.getStorage()).thenReturn(storage);
        config.setContext(context);

        Map<String,List<String>> values = new HashMap<>();
        values.put("hostName",buildList("hostName"));
        values.put("userName",buildList("userName"));
        values.put("userPassword",buildList("userPassword"));
        values.put("port",buildList("21"));

        config.setValues(values);

        Mockito.verify(context,times(1)).getStorage();
        Mockito.verify(storage,times(1)).getOutputStream(anyString());

        FileUtils.deleteQuietly(new File("test"));

    }

    private List<String> buildList(String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        return list;
    }

}
