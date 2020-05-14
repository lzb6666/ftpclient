package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/25
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
@FTPResponseHandler(status = "550")
public class BadResponseHandler implements ResponseHandler {
    private static final Logger log= LoggerFactory.getLogger(BadResponseHandler.class);
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.setLastSuccessful(false);
        log.error(request + "失败");
        log.error(response);
    }
}
