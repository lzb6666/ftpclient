package socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * create by zhong
 * socket
 * Date 2019/4/25
 */
@Deprecated
public class FTPCmdSocket {

    private Socket socket;
    private Writer write;
    private BufferedReader reader;

    private static final Logger logger= LoggerFactory.getLogger(FTPCmdSocket.class);
    public FTPCmdSocket(Socket socket) throws IOException {
        this.socket= socket;
        write=new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
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
            logger.debug(cmd);
            throw e;
        }
        return sb.toString();
    }

/*    public String getStatusCode(String cmd) throws IOException {
        String response=exec(cmd);
        StringBuilder sb=new StringBuilder();
        int i=0;
        while (i<response.length()&&Character.isDigit(response.charAt(i))){
            sb.append(response.charAt(i++));
        }
        return sb.toString();
    }*/

    public void close() throws IOException {
        socket.close();
    }
}
