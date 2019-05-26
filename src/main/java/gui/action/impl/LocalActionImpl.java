package gui.action.impl;

import ftp.FTPSiteContext;
import gui.vo.FileInfo;
import gui.action.LocalAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * create by zhong
 * gui
 * Date 2019/5/23
 */
public class LocalActionImpl implements LocalAction{

    private FTPSiteContext context;

    private static final Logger log= LoggerFactory.getLogger(LocalActionImpl.class);
    @Override
    public List<FileInfo> list(String directory) {
        File file=new File(directory);
        if (!file.exists()){
            log.warn(directory+"不存在");
            return null;
        }
        if (!file.isDirectory()){
            log.warn(directory+"不是个目录");
            return null;
        }
        File[] files=file.listFiles();
        if (files==null){
            log.error(file.getAbsolutePath()+"list()出错");
            return null;
        }

        List<FileInfo> fileInfos=new ArrayList<>();
        for (File subFile:files
             ) {
            fileInfos.add(new FileInfo(subFile));
        }
        return fileInfos;
    }
}
