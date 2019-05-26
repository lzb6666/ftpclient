package gui;

import ftp.ApplicationContext;
import gui.frame.MainFrame;

import java.io.IOException;

/**
 * create by zhong
 * gui
 * Date 2019/5/23
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContext("ftp.handler");
        }catch (Exception e){
            e.printStackTrace();
        }

        MainFrame mainFrame=new MainFrame();
        /*} catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/

    }
}
