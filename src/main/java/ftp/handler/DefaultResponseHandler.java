package ftp.handler;

import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by zhong
 * socket
 * Date 2019/4/27
 */
public class DefaultResponseHandler implements ResponseHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultResponseHandler.class);

    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.setLastSuccessful(true);
    }
}
