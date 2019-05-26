package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/25
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
@FTPResponseHandler(status = "550")
public class BadResponseHandler implements ResponseHandler {
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.setLastSuccessful(false);
        System.out.println(request+"失败");
        System.out.println(response);
    }
}
