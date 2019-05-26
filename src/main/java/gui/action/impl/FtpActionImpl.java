package gui.action.impl;

import ftp.FTPSiteContext;
import gui.action.FtpAction;
import gui.action.util.ListUtil;
import gui.vo.FileInfo;
import gui.vo.TransferTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socket.AbstractFileSupporter;
import socket.CmdExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by zhong
 * gui.action.impl
 * Date 2019/5/23
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
public class FtpActionImpl implements FtpAction {
    private static final Logger log= LoggerFactory.getLogger(FtpActionImpl.class);
    private FTPSiteContext context;
    @Override
    public boolean anonymityLogin(String host, int port) {
        if (!initConnection(host,port)){
            return false;
        }
        try {
            context.dispatcher("USER anonymous");
            return context.isLastSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean login(String host, int port, String user, String pwd) {
        if (!initConnection(host,port)){
            return false;
        }
        try {
            context.dispatcher("USER "+user);
            context.dispatcher("PASS "+pwd);
            return context.isAllSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<FileInfo> list() {
        if (context==null){
            log.error("FtpSiteContext=null");
            return null;
        }
        try {
            context.dispatcher("PWD");
            context.dispatcher("TYPE I");
            context.dispatcher("PASV");
            if (!context.isAllSuccessful()){
                log.error("PASV PWD TYPE I 出错");
                return null;
            }
            CmdExecutor executor=context.getCmdExecutor();
            String response=executor.exec("LIST -l");
            if (!response.contains("150")){
                log.error(response);
            }
            String infos=context.getFileSupporter().getResponse();
            if (!executor.exec(null).startsWith("226")){
                log.warn("list没有收到226 send ok");
            }
            List<FileInfo> fileInfoList=ListUtil.getFileInfo(infos);
            FileInfo fileInfo=new FileInfo();
            fileInfo.setName("..");
            fileInfo.setSize(4096);
            fileInfoList.add(fileInfo);
            return fileInfoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TransferTask download(String filePath) {
        TransferTask transferTask=new TransferTask(filePath);
        try {
            context.dispatcher("PASV");
            context.dispatcher("SIZE "+filePath);
            AbstractFileSupporter supporter= (AbstractFileSupporter) context.getFileSupporter();
            transferTask.setTotalSize(supporter.getTotalSize());
            transferTask.setFileSupporter(supporter);
            transferTask.setDownload(true);
            context.dispatcher("RETR "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transferTask;
    }

    @Override
    public FTPSiteContext getContext() {
        if (context==null){
            context=new FTPSiteContext("",-1);
        }
        return context;
    }

    @Override
    public TransferTask upload(String filePath) {
        TransferTask transferTask=new TransferTask(filePath);
        try {
            transferTask.setTotalSize(new File(context.getLocalDirectory()+"\\"+filePath).length());
            context.dispatcher("PASV");
            transferTask.setFileSupporter((AbstractFileSupporter) context.getFileSupporter());
            transferTask.setUpload(true);
            context.dispatcher("STOR "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transferTask;
    }

    @Override
    public boolean changeWorkDir(String dir) {
        try {
            context.dispatcher("CWD "+dir);
            return context.isLastSuccessful();
        } catch (IOException e) {
            log.error("打开"+dir+"出错");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TransferTask continueTask(TransferTask task) {
        if (task.isDownload()){
            try {
                context.dispatcher("PASV");
                context.dispatcher("REST "+task.getCurSize());
                //context.dispatcher("SIZE "+task.getAbPath());
                AbstractFileSupporter supporter= (AbstractFileSupporter) context.getFileSupporter();
                supporter.setAppend(true);
                supporter.setOffset(task.getCurSize());
                supporter.setTotalSize(task.getTotalSize());
                task.setFileSupporter(supporter);
                context.dispatcher("RETR "+task.getAbPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (task.isUpload()){
            try {
                context.dispatcher("PASV");
                AbstractFileSupporter supporter= (AbstractFileSupporter) context.getFileSupporter();
                supporter.setOffset(task.getCurSize());
                task.setFileSupporter(supporter);
                context.dispatcher("APPE "+task.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    @Override
    public void pause() {
        AbstractFileSupporter supporter= (AbstractFileSupporter) context.getFileSupporter();
        supporter.pause();
        try {
            //426接受服务器输出错误的信息
            context.getCmdExecutor().exec(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean initConnection(String host, int port){
        try {
            if (context==null){
                context=new FTPSiteContext(host,port);
            }else {
                context.setHost(host);
                context.setCmdPort(port);
            }
            String welcome= context.getCmdExecutor().exec(null);
            System.out.println(welcome);
        } catch (IOException e) {
            log.error("连接失败"+host+":"+port);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<FileInfo> getInfoFromResponse(String response){
        String[] strs=response.split("\r\n");
        List<FileInfo> infos=new ArrayList<>(strs.length);
        for (String str:strs
             ) {

        }
        return infos;
    }

    private FileInfo getInfo(String line){
        int length=line.length();
        for (int i=0;i<length;i++){
            //TODO:fileinfo
        }
        return null;
    }

    private long getSize(String response){
        String[] size=response.split(" ");
        return Long.valueOf(size[1]);
    }

    private String getParentDir(String path){
        char[] chs=path.toCharArray();
        for (int i=chs.length-1;i>=0;i--){
            if (chs[i]=='/'){
                if (i==chs.length-1)continue;
                return path.substring(0,i);
            }
        }
        return null;
    }
}
