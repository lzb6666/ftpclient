package gui.action.util;

import gui.vo.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * create by zhong
 * gui.action.util
 * Date 2019/5/25
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
public class ListUtil {
    public static int AUTHORIRY_LENGTH = 10;
    public static int SIZE_LENGTH = 12;
    public static int NAME_LENGTH = 32;

    public static int MONTH_LENGTH = 4;
    public static int DATE_LENGTH = 4;
    public static int TIME_LENGTH = 8;

    public static List<FileInfo> getFileInfo(String str) {
        String[] a = str.split("\r\n");

        List<FileInfo> file_list = new ArrayList<>();
        for (String s : a) {
            if (s.equals("")) continue;
            int space_flag = 0;
            char[] chars = s.toCharArray();

            char[] authority = new char[AUTHORIRY_LENGTH];
            System.arraycopy(chars, 0, authority, 0, AUTHORIRY_LENGTH);

            char[] size = new char[SIZE_LENGTH];
            char[] name = new char[NAME_LENGTH];

            char[] month = new char[MONTH_LENGTH];
            char[] date = new char[DATE_LENGTH];
            char[] time = new char[TIME_LENGTH];

            int start = 0;
            for (int i = AUTHORIRY_LENGTH; i < chars.length; i++) {
                if ((int) chars[i] == 32)
                    continue;
                if ((int) chars[i - 1] == 32)
                    space_flag++;

                //标记一个单元（大小或日期或名称）的起始点


                //解析大小
                if (space_flag == 4 && (int) chars[i - 1] == 32)
                    start = i;
                if (space_flag == 4 && (int) chars[i + 1] == 32)
                    System.arraycopy(chars, start, size, 0, i - start + 1);

                //解析月份
                if (space_flag == 5 && (int) chars[i - 1] == 32)
                    start = i;
                if (space_flag == 5 && (int) chars[i + 1] == 32)
                    System.arraycopy(chars, start, month, 0, i - start + 1);

                //解析日期
                if (space_flag == 6 && (int) chars[i - 1] == 32)
                    start = i;
                if (space_flag == 6 && (int) chars[i + 1] == 32)
                    System.arraycopy(chars, start, date, 0, i - start + 1);

                //解析具体时间
                if (space_flag == 7 && (int) chars[i - 1] == 32)
                    start = i;
                if (space_flag == 7 && (int) chars[i + 1] == 32)
                    System.arraycopy(chars, start, time, 0, i - start + 1);

                //解析名称
                if (space_flag == 8 && (int) chars[i - 1] == 32)
                    start = i;
                if (space_flag == 8 && i == chars.length - 1)
                    System.arraycopy(chars, start, name, 0, i - start + 1);
            }

            String lastModify = deleteSpace(month) + " " + deleteSpace(date) + " " + deleteSpace(time);
            FileInfo fileInfo = new FileInfo(deleteSpace(name), deleteSpace(authority), lastModify, Long.parseLong(deleteSpace(size)));
            file_list.add(fileInfo);
        }

        return file_list;
    }

    public static String deleteSpace(char[] chars) {
        int len = 0;
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] != '\0')
                len++;
        }

        char[] chs = new char[len];
        System.arraycopy(chars, 0, chs, 0, len);

        return String.valueOf(chs);
    }
}
