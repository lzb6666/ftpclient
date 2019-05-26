package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/26
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
@FTPResponseHandler(status = "226")
public class CompleteHandler implements ResponseHandler {
    @Override
    public void process(FTPSiteContext context, String request, String response) {

    }
}
