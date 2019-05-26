package ftp;

import ftp.handler.DefaultResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private boolean lastSuccessful=true;
    private boolean allSuccessful=true;

    private ApplicationContext appContext;
    private static final ResponseHandler defaultResponseHandler=new DefaultResponseHandler();

    private static final Logger log= LoggerFactory.getLogger(FTPSiteContext.class);

    public FTPSiteContext(String host, int cmdPort){
        this.host = host;
        this.cmdPort = cmdPort;
    }

    public void dispatcher(String request) throws IOException {
        String response=cmdExecutor.exec(request);
        String code=response.substring(0,3);
        ResponseHandler handler;
        if (appContext==null){
            log.error("app has not inited");
        }
        if (appContext.getHandlers().keySet().contains(code)){
            handler=(ResponseHandler) appContext.getHandlers().get(code);
        }else {
            handler=defaultResponseHandler;
        }
        //log.debug(request+":"+response);
        handler.process(this,request,response);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) throws IOException {
        this.host = host;
        this.cmdExecutor= null;
    }

    public int getCmdPort() {
        return cmdPort;
    }

    public void setCmdPort(int cmdPort) throws IOException {
        this.cmdPort = cmdPort;
        this.cmdExecutor= null;
    }

    public int getFilePort() {
        return filePort;
    }

    public void setFilePort(int filePort) throws IOException {
        this.filePort = filePort;
        this.fileSupporter= SupporterFactory.getPausableSupport(host,filePort);
    }

    public FileSupporter getFileSupporter() {
        return fileSupporter;
    }

    public void setFileSupporter(FileSupporter fileSupporter) {
        this.fileSupporter = fileSupporter;
    }

    public CmdExecutor getCmdExecutor() throws IOException {
        if (cmdExecutor==null){
            this.cmdExecutor= ExecutorFactory.getSimpleExector(host, cmdPort);
        }
        return cmdExecutor;
    }

    public void setCmdExecutor(CmdExecutor cmdExecutor) {
        this.cmdExecutor = cmdExecutor;
    }

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

    public boolean isLastSuccessful() {
        boolean temp=lastSuccessful;
        lastSuccessful=true;
        return temp;
    }

    public void setLastSuccessful(boolean lastSuccessful) {
        allSuccessful=allSuccessful&lastSuccessful;
        this.lastSuccessful = lastSuccessful;
    }

    /**
     * only return once;
     * @return 自上次查询allSuccessful起，是否全部操作成功，仅返回一次，返回后将置为true
     */
    public boolean isAllSuccessful() {
        boolean temp=allSuccessful;
        allSuccessful=true;
        return temp;
    }

    public void setAllSuccessful(boolean allSuccessful) {
        this.allSuccessful = allSuccessful;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }
}
