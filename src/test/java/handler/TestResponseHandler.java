package handler;

import ftp.ResponseHandler;
import ftp.handler.Passive227Handler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * create by zhong
 * handler
 * Date 2019/4/25
 */

public class TestResponseHandler {
    private Passive227Handler handler;

    @Before
    public void init(){
        handler=new Passive227Handler();
    }

    @Test
    public void ftp227Handler(){
        int port=handler.getPort("227 Entering Passive Mode (193,112,44,141,39,39).");
        System.out.println(port);
        Assert.assertTrue("227端口提取成功",port==10023);

    }
}
