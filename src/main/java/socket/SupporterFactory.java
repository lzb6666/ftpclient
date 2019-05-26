package socket;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/17
 */
public class SupporterFactory {
    public static AbstractFileSupporter getPausableSupport(String host,int port) throws IOException {
        return new PausableFileSupporter(host,port);
    }

    public static FileSupporter getFileSupport(){
        return null;
    }
}
