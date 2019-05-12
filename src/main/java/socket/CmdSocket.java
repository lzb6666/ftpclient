package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public class CmdSocket extends Socket implements CmdExecutor {
    private Writer write;
    private BufferedReader reader;


    public CmdSocket(){
        super();
    }

    public CmdSocket(String host, int port) throws IOException {
        super(host, port);
    }

    public CmdSocket(InetAddress address, int port) throws IOException {
        super(address,port);
        write=new OutputStreamWriter(getOutputStream(),"UTF-8");
        reader=new BufferedReader(new InputStreamReader(getInputStream(),"UTF-8"));
        //读取欢迎信息
        System.out.println(reader.readLine());
    }

    public String exec(String cmd) throws IOException {
        write.write(cmd);
        write.write("\r\n");
        write.flush();
        StringBuilder sb=new StringBuilder();
        try {
            //命令返回都只有一行
            sb.append(reader.readLine());
        }catch (IOException e){
            throw e;
        }
        return sb.toString();
    }
}
