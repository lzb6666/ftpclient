package gui.action;

import ftp.FTPSiteContext;
import gui.vo.FileInfo;
import gui.vo.TransferTask;

import java.util.List;

/**
 * create by zhong
 * gui.action
 * Date 2019/5/17
 */
public interface FtpAction {

    boolean anonymityLogin(String host, int port);

    boolean login(String host, int port, String user, String pwd);

    List<FileInfo> list();

    TransferTask download(String fileName);

    FTPSiteContext getContext();

    TransferTask upload(String fileName);

    boolean changeWorkDir(String dir);

    TransferTask continueTask(TransferTask task);

    void pause();
}
