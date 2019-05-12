package socket;

import java.io.*;
import java.net.Socket;

/**
 * create by zhong
 * socket
 * Date 2019/4/27
 */
@Deprecated
public class FTPFileSocket {
    private Socket socket;
    private String filePath;
    private OutputStream outputStream;
    private InputStream inputStream;
    public FTPFileSocket(Socket socket) throws IOException {
        this.socket=socket;
        this.outputStream=socket.getOutputStream();
        this.inputStream=socket.getInputStream();
    }

    public FTPFileSocket(Socket socket,String filePath) throws IOException {
        this(socket);
        this.filePath=filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File getFileFromSocket() throws IOException {
        FileOutputStream stream=new FileOutputStream(filePath);
        byte[] temp=new byte[100];
        long size=0;
        while (inputStream.available()!=0){
             size+=inputStream.read(temp);
             stream.write(temp);
        }
        return new File(filePath);
    }

    public String getResponseFromSocket() throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuilder sb=new StringBuilder();
        try {
            sb.append(reader.readLine());
            while (reader.ready()){
                sb.append(reader.readLine());
                sb.append("\r\n");
            }
        }catch (IOException e){
            throw e;
        }
        return sb.toString();
    }

    public void uploadFile(String filePath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(filePath);
        byte[] temp=new byte[100];
        while (fileInputStream.read(temp)!=-1){
            outputStream.write(temp);
        }
        outputStream.flush();
        fileInputStream.close();
    }

    public void close() throws IOException {
        socket.close();
    }

    public boolean isClosed(){
        return socket.isClosed();
    }

    public void connect(){

    }
}
