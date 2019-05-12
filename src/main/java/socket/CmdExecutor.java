package socket;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public interface CmdExecutor {
    String exec(String cmd) throws IOException;
}
