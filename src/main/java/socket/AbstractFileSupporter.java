package socket;

import java.io.IOException;

/**
 * create by zhong
 * socket
 * Date 2019/5/17
 */
public abstract class AbstractFileSupporter implements FileSupporter {
    protected boolean append = true;
    protected long offset = 0;
    protected long size = 0;

    protected long totalSize = 0;

    @Override
    public abstract void download(String localPath) throws IOException;

    @Override
    public abstract void upload(String localPath) throws IOException;

    @Override
    public abstract String getResponse() throws IOException;

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public abstract void pause();

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.size = offset;
        this.offset = offset;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
