package ftp;

import ftp.handler.DefaultResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/4/27
 */
public class ResponseDispatcher {
    private ApplicationContext applicationContext;
    private static final Logger log= LoggerFactory.getLogger(ResponseDispatcher.class);
    private static final ResponseHandler defaultResponseHandler=new DefaultResponseHandler();
    public void dispatcher(FTPSiteContext ftpSiteContext,String request,String response){
        String r;
        try {
            r=ftpSiteContext.getCmdExecutor().exec(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response==null){
            log.error("response=null");
            return;
        }
        String code=response.substring(0,3);
        ResponseHandler handler;
        if (applicationContext.getHandlers().keySet().contains(code)){
            handler=(ResponseHandler) applicationContext.getHandlers().get(code);
        }else {
            handler=defaultResponseHandler;
        }
        handler.process(ftpSiteContext,request,response);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
