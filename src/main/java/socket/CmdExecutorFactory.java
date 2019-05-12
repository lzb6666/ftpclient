package socket;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public class CmdExecutorFactory {
    public static CmdExecutor getFTPCmdExecutor(String host, int port){
        try {
            return new CmdSocket(host,port);
        } catch (IOException e) {
            //TODO:log
            e.printStackTrace();
            return null;
        }
    }
}
