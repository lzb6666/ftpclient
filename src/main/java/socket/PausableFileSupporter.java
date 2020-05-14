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
public class PausableFileSupporter extends AbstractFileSupporter {
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    private boolean append = true;
    private boolean pause = false;


    private static final Logger log = LoggerFactory.getLogger(PausableFileSupporter.class);

    public PausableFileSupporter(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
    }

    @Override
    public void download(String localPath) throws IOException {
        File file = new File(localPath);
        FileOutputStream fileOutputStream = null;
        if (file.exists() && append) {
            fileOutputStream = new FileOutputStream(localPath, true);
        } else {
            fileOutputStream = new FileOutputStream(localPath);
        }

        byte[] temp = new byte[1024];
        int readByte = 0;
        //while (inputStream.available()!=0){
        while (size < totalSize) {
            readByte = inputStream.read(temp);
            if (readByte == -1) continue;
            fileOutputStream.write(temp, 0, readByte);
            fileOutputStream.flush();
            size += readByte;
            if (pause) {
                break;
            }
        }
        fileOutputStream.close();
        close();
    }

    @Override
    public void upload(String localPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(localPath);
        totalSize = new File(localPath).length();
        if (fileInputStream.skip(offset) != offset) {
            log.error(localPath + "skipped " + offset);
        }
        byte[] temp = new byte[1024];
        int tempSize = 0;
        while ((tempSize = fileInputStream.read(temp)) != -1) {
            outputStream.write(temp, 0, tempSize);
            outputStream.flush();
            size += tempSize;
            if (pause) {
                break;
            }
        }
        fileInputStream.close();
        close();
    }

    @Override
    public String getResponse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        try {
            String temp = reader.readLine();
            if (temp == null || temp.equals("")) return null;
            sb.append(temp);
            sb.append("\r\n");
            while (reader.ready()) {
                sb.append(reader.readLine());
                sb.append("\r\n");
            }
        } catch (IOException e) {
            throw e;
        }
        close();
        return sb.toString();
    }

    @Override
    public void pause() {
        this.pause = true;
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
