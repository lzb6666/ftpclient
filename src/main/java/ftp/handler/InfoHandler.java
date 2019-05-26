package ftp.handler;


import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socket.AbstractFileSupporter;

import java.io.IOException;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/2
 */
@FTPResponseHandler(status = "150")
public class InfoHandler implements ResponseHandler{
    private static final Logger log= LoggerFactory.getLogger(InfoHandler.class);
    @Override
    public void process(FTPSiteContext context,String request, String response) {
        if (response.contains("150 Here comes the directory listing.")){
            try {
                System.out.println(response);
                System.out.println(context.getFileSupporter().getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (response.contains("150 Opening BINARY mode data connection for")){
            AbstractFileSupporter supporter= (AbstractFileSupporter) context.getFileSupporter();
            new Thread(()->{
                try {
                    supporter.download(context.getLocalDirectory()+"\\"+fileName(context.getRemoteDirectory(),response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            /*String complete= null;
            try {
                complete = context.getCmdExecutor().exec(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (complete.startsWith("226")){
                supporter.setComplete(true);
                log.info("下载完成");
            }*/

        }else if (response.contains("150 Ok to send data")){
                //FTPFileSocket socket=context.getFileSocket();
                new Thread(()->{
                    try {
                        context.getFileSupporter().upload(context.getLocalDirectory()+"\\"+getFileName(request));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
    }

    private String fileName(String pwd,String response){
        int start=response.indexOf(pwd);
        return response.substring(start+pwd.length(),response.indexOf(" ("));
    }

    private String getFileName(String request){
        return request.substring(request.indexOf(" ")+1,request.length());
    }
}
