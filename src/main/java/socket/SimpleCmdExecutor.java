package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * create by zhong
 * socket
 * Date 2019/5/17
 */
public class SimpleCmdExecutor implements CmdExecutor {
    private Writer write;
    private BufferedReader reader;
    private Socket socket;

    private static final Logger log= LoggerFactory.getLogger(SimpleCmdExecutor.class);

    public SimpleCmdExecutor(String host,int port) throws IOException {
        socket=new Socket(host, port);
        write=new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
    }

    @Override
    public String exec(String cmd) throws IOException {
        if (cmd==null){
            return reader.readLine();
        }
        write.write(cmd);
        write.write("\r\n");
        write.flush();
        StringBuilder sb=new StringBuilder();
        try {
            //命令返回都只有一行
            sb.append(reader.readLine());
        }catch (IOException e){
            log.error(cmd);
            log.error(e.getMessage());
            throw e;
        }
        //TODO:log
        String temp=sb.toString();
        log.debug(cmd+":"+temp);
        return temp;
    }
}
