package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/2
 */
@FTPResponseHandler(status = "257")
public class PwdHandler implements ResponseHandler{

    private String getPwd(String response){
        int start=response.indexOf("\"");
        int end=response.indexOf("\"",start+1);
        return response.substring(start+1,end);
    }

    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.setRemoteDirectory(getPwd(response));
        System.out.println(response);
    }
}
