package socket;

import java.io.File;
import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/3
 */
public interface FileSupporter {
    void download(String localPath)throws IOException;
    void upload(String localPath)throws IOException;
    String getResponse()throws IOException;
    void setOffset(long offset);
    void setAppend(boolean append);
    void pause();
}
