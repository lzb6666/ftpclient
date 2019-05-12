package socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public class FileSocket extends Socket implements FileSupporter {
    private OutputStream outputStream;
    private InputStream inputStream;
    private boolean append=true;
    private long offset=0;
    private boolean pause=false;
    private static final Logger log= LoggerFactory.getLogger(FileSocket.class);

    public FileSocket(InetAddress address, int port) throws IOException {
        super(address,port);
        this.outputStream=getOutputStream();
        this.inputStream=getInputStream();
    }

    public FileSocket(String host,int port) throws IOException {
        super(host,port);
        this.outputStream=getOutputStream();
        this.inputStream=getInputStream();
    }

    @Override
    public void download(String localPath) throws IOException {
        File file=new File(localPath);
        FileOutputStream fileOutputStream=null;
        if (file.exists()&&append){
            fileOutputStream=new FileOutputStream(localPath,true);
        }else {
            fileOutputStream=new FileOutputStream(localPath);
        }

        byte[] temp=new byte[100];
        long size=0;
        while (inputStream.available()!=0){
            size+=inputStream.read(temp);
            fileOutputStream.write(temp);
            fileOutputStream.flush();
            if (pause){
                break;
            }
        }
        close();
    }

    @Override
    public void upload(String localPath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(localPath);
        if (fileInputStream.skip(offset)!=offset){
            log.error(localPath+"skipped "+offset);
        }
        byte[] temp=new byte[100];
        while (fileInputStream.read(temp)!=-1){
            outputStream.write(temp);
            outputStream.flush();
            if (pause){
                break;
            }
        }
        fileInputStream.close();
        close();
    }

    @Override
    public String getResponse() throws IOException {
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
        close();
        return sb.toString();
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    @Override
    public void pause() {
        this.pause=true;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
