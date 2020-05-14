package main;

import ftp.ApplicationContext;
import ftp.FTPSiteContext;
import ftp.ResponseDispatcher;
import socket.CmdExecutor;

import java.io.IOException;
import java.util.Scanner;

/**
 * create by zhong
 * main
 * Date 2019/5/3
 */
public class StdMain {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        //全局上下文，响应调度器
        ApplicationContext context = new ApplicationContext("ftp.handler");
        ResponseDispatcher dispatcher = new ResponseDispatcher();
        dispatcher.setApplicationContext(context);
        //站点上下文
        try {
            FTPSiteContext siteContext = new FTPSiteContext("193.112.44.141", (short) 21);
            siteContext.setAppContext(context);
            CmdExecutor executor = siteContext.getCmdExecutor();
            Scanner in = new Scanner(System.in);
            String cmd = null;
            while ((cmd = in.nextLine()) != null) {
                dispatcher.dispatcher(siteContext, cmd, executor.exec(cmd));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
