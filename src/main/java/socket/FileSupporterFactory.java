package socket;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public class FileSupporterFactory {
    public static FileSupporter getFTPFileSupport(String host, int port){
        try {
            return new FileSocket(host,port);
        } catch (IOException e) {
            //TODO:log
            e.printStackTrace();
        }
        return null;
    }
}
