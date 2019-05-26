package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/4/27
 */
@FTPResponseHandler(status = "227")
public class Passive227Handler implements ResponseHandler{
    private static final Logger log=LoggerFactory.getLogger(Passive227Handler.class);
    @Override
    public void process(FTPSiteContext context,String request, String response) {
        try {
            context.setFilePort(getPort(response));
        } catch (IOException e) {
            log.error(response);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(response);
    }

    public short getPort(String response){
        String addr=response.substring(response.indexOf('('),response.indexOf(')'));
        String[] ports=addr.split(",");
        int port=Integer.valueOf(ports[4]);
        log.info(ports[4]);

        int temp=(port<<8)+Integer.valueOf(ports[5]);
        log.info(ports[5]);
        return (short) temp;
    }
}
