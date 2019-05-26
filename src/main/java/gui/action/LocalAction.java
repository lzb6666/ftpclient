package gui.action;

import gui.vo.FileInfo;

import java.util.List;

/**
 * create by zhong
 * gui.action
 * Date 2019/5/23
 */
public interface LocalAction {
    List<FileInfo> list(String directory);
}
