package ftp;

import socket.*;

import java.io.IOException;
import java.net.Socket;

/**
 * create by zhong
 * ftp
 * Date 2019/4/27
 */
public class FTPSiteContext {
    private String host;
    private int cmdPort;
    private int filePort;
    private String localDirectory;
    private String remoteDirectory;

    private FileSupporter fileSupporter;
    private CmdExecutor cmdExecutor;


    /*private FTPFileSocket fileSocket;
    private FTPCmdSocket cmdSocket;*/

    public FTPSiteContext(String host, int cmdPort) throws IOException {
        this.host = host;
        this.cmdPort = cmdPort;
        //this.cmdSocket=new FTPCmdSocket(new Socket(host,cmdPort));
        this.cmdExecutor= CmdExecutorFactory.getFTPCmdExecutor(host,cmdPort);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
        this.cmdExecutor= CmdExecutorFactory.getFTPCmdExecutor(host,cmdPort);
    }

    public int getCmdPort() {
        return cmdPort;
    }

    public void setCmdPort(short cmdPort) {
        this.cmdPort = cmdPort;
        this.cmdExecutor= CmdExecutorFactory.getFTPCmdExecutor(host,cmdPort);
    }

    public int getFilePort() {
        return filePort;
    }

    public void setFilePort(short filePort) {
        this.filePort = filePort;
        this.fileSupporter= FileSupporterFactory.getFTPFileSupport(host,filePort);
    }

    public FileSupporter getFileSupporter() {
        return fileSupporter;
    }

    public void setFileSupporter(FileSupporter fileSupporter) {
        this.fileSupporter = fileSupporter;
    }

    public CmdExecutor getCmdExecutor() {
        return cmdExecutor;
    }

    public void setCmdExecutor(CmdExecutor cmdExecutor) {
        this.cmdExecutor = cmdExecutor;
    }

    /*    public void setFilePort(short filePort) throws IOException {
        this.filePort = filePort;
        try {
            fileSocket=new FTPFileSocket(new Socket(host,filePort));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public FTPCmdSocket getCmdSocket() {
        return cmdSocket;
    }

    public FTPFileSocket getFileSocket() {

        return fileSocket;
    }*/

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public String getRemoteDirectory() {
        return remoteDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        this.remoteDirectory = remoteDirectory;
    }

    public void close(){
        if (cmdExecutor instanceof Socket){
            Socket socket=(Socket)cmdExecutor;
            try {
                socket.close();
            } catch (IOException e) {
                //TODO:log
                e.printStackTrace();
            }
        }
    }

}
