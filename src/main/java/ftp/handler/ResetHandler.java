package ftp.handler;

import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socket.FileSocket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/12
 */
@FTPResponseHandler(status = "350")
public class ResetHandler implements ResponseHandler {
    private static final Logger log= LoggerFactory.getLogger(ResetHandler.class);
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.getFileSupporter().setOffset(getOffset(request));
    }

    private long getOffset(String request){
        String pattern="[0-9]*";
        Pattern pattern1=Pattern.compile(pattern);
        Matcher matcher=pattern1.matcher(request);
        if (matcher.find()){
            return Long.valueOf(matcher.group());
        }else {
            log.error(request+"找不到偏移");
            return 0;
        }

    }
}
