package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import socket.AbstractFileSupporter;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/26
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
@FTPResponseHandler(status = "213")
public class SizeHandler implements ResponseHandler {
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        AbstractFileSupporter supporter= (AbstractFileSupporter)context.getFileSupporter();
        supporter.setTotalSize(getSize(response));
    }

    private long getSize(String response){
        return Long.parseLong(response.substring(4,response.length()));
    }
}
