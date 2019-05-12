package ftp.handler;


import anno.FTPResponseHandler;
import ftp.FTPSiteContext;
import ftp.ResponseHandler;

import java.io.IOException;

/**
 * create by zhong
 * ftp.handler
 * Date 2019/5/2
 */
@FTPResponseHandler(status = "150")
public class InfoHandler implements ResponseHandler{
    @Override
    public void process(FTPSiteContext context,String request, String response) {
        if (response.contains("150 Here comes the directory listing.")){
            try {
                System.out.println(response);
                System.out.println(context.getFileSupporter().getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (response.contains("150 Opening BINARY mode data connection for")){
            try {
/*                FTPFileSocket socket=context.getFileSocket();
                socket.setFilePath(context.getLocalDirectory()+"\\"+fileName(context.getRemoteDirectory(),response));
                socket.getFileFromSocket();*/
                context.getFileSupporter().download(context.getLocalDirectory()+"\\"+fileName(context.getRemoteDirectory(),response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (response.contains("150 Ok to send data")){
                //FTPFileSocket socket=context.getFileSocket();
            try {
                context.getFileSupporter().upload(context.getLocalDirectory()+"\\"+getFileName(request));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String fileName(String pwd,String response){
        int start=response.indexOf(pwd);
        return response.substring(start+pwd.length(),response.length()-1);
    }

    private String getFileName(String request){
        return request.substring(request.indexOf(" ")+1,request.length());
    }
}
