package ftp;

/**
 * create by zhong
 * socket
 * Date 2019/4/25
 */
public interface ResponseHandler {
    void process(FTPSiteContext context, String request, String response);
}
