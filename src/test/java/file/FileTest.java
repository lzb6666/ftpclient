package file;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * create by zhong
 * file
 * Date 2019/5/11
 */
public class FileTest {

    @Test
    public void abortTest() throws IOException {
        FileOutputStream outputStream=new FileOutputStream("D:\\课程\\网络工程\\ftp\\test");
        byte[] bytes=new byte[100];
        int size=5000;
        while (size>0){
            size-=101;
            outputStream.write(63);
            outputStream.write(bytes);
            outputStream.flush();
        }
    }

    @Test
    public void fileByteTest(){
        File file=new File("D:\\课程\\网络工程\\ftp\\test");
        System.out.println(file.length());
    }

    @Test
    public void fileListTest(){
        File file=new File("D:\\课程\\网络工程");
        Timestamp timestamp=new Timestamp(file.lastModified());
        System.out.println(timestamp);
        Arrays.stream(file.list()).forEach(System.out::println);
    }


}
