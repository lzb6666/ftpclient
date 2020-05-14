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
@FTPResponseHandler(status = "250")
public class ChangeWorkDirHandler implements ResponseHandler {
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        request = request.substring(3, request.length());
        String pwd = request.replaceAll(" ", "");
        context.setRemoteDirectory(pwd);
    }
}
