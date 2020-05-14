package ftp.handler;


import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;

@FTPResponseHandler(status = "553")
public class UploadError553Handler implements ResponseHandler {
    @Override
    public void process(FTPSiteContext context, String request, String response) {
        context.setLastSuccessful(false);
        context.setLastResponse(response);
    }
}
