package gui.vo;

import ftp.FTPSiteContext;
import socket.AbstractFileSupporter;

import java.io.IOException;

/**
 * create by zhong
 * gui.vo
 * Date 2019/5/24
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
public class TransferTask {
    private String abPath;
    private long totalSize;
    private long curSize;
    private AbstractFileSupporter fileSupporter;

    private boolean isDownload;
    private boolean isUpload;

    public TransferTask(String abPath) {
        this.abPath = abPath;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public AbstractFileSupporter getFileSupporter() {
        return fileSupporter;
    }

    public void setFileSupporter(AbstractFileSupporter fileSupporter) {
        this.fileSupporter = fileSupporter;
    }

    public void pause(){
        fileSupporter.pause();
    }

    public long getCurSize() {
        return curSize=fileSupporter.getSize();
    }

    public void setCurSize(long curSize) {
        this.curSize = curSize;
    }

    public void continueTask(){

    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public String getAbPath() {
        return abPath;
    }

    public void setAbPath(String abPath) {
        this.abPath = abPath;
    }

    public String getFileName(){
        String[] files=abPath.split("/");
        return files[files.length-1];
    }
}
