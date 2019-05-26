package gui.vo;

import java.io.File;
import java.sql.Timestamp;

/**
 * create by zhong
 * gui
 * Date 2019/5/17
 */
public class FileInfo {
    private String name;
    private String authority;
    private String lastModify;
    private long size;

    public FileInfo() {
        this.name="";
        this.authority="";
        this.lastModify="";
        this.size=0;
    }


    public FileInfo(File file) {
        this.name=file.getName();
        this.lastModify=new Timestamp(file.lastModified()).toString();
        this.size=file.length();
        //TODO:file权限转rwx
        this.authority="rwx";
    }

    public FileInfo(String name, String authority, String lastModify, long size) {
        this.name = name;
        this.authority = authority;
        this.lastModify = lastModify;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return name+"\t"+authority+"\t"+lastModify+"\t"+size;
    }
}
