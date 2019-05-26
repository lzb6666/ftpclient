package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/17
 */
public class ExecutorFactory {
    private static final Logger log= LoggerFactory.getLogger(ExecutorFactory.class);


    public static CmdExecutor getSimpleExector(String host,int port) throws IOException {
        return new SimpleCmdExecutor(host,port);
    }
}
