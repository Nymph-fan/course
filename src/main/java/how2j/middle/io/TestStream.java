package how2j.middle.io;

import org.testng.annotations.Test;

import java.io.*;
import java.util.Arrays;

/**
 * Created by joke
 * 2018/2/5
 * 字节流
 */
public class TestStream {

    @Test
    public void read(){

        File f=new File("d:/lol2.txt");
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(f);
            byte[] all=new byte[(int) f.length()];
            fis.read(all);
            for (byte b:all){
                System.out.println(b);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void write(){
        try {
            // 准备文件lol2.txt其中的内容是空的
            File f = new File("d:/lol2.txt");
            // 准备长度是2的字节数组，用88,89初始化，其对应的字符分别是X,Y
            byte data[] = { 88, 89 };

            // 创建基于文件的输出流
            FileOutputStream fos = new FileOutputStream(f);
            // 把数据写入到输出流
            fos.write(data);
            // 关闭输出流
            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void divide(){

        int eachSize = 100 * 1024; // 100k
        File srcFile = new File("d:/eclipse.exe");
        splitFile(srcFile, eachSize);

    }

    private static void splitFile(File srcFile, int eachSize) {

        if (0 == srcFile.length())
            throw new RuntimeException("文件长度为0，不可拆分");

        byte[] fileContent = new byte[(int) srcFile.length()];
        // 先把文件读取到数组中
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            fis.read(fileContent);
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 计算需要被划分成多少份子文件
        int fileNumber;
        // 文件是否能被整除得到的子文件个数是不一样的
        // (假设文件长度是25，每份的大小是5，那么就应该是5个)
        // (假设文件长度是26，每份的大小是5，那么就应该是6个)
        if (0 == fileContent.length % eachSize)
            fileNumber = (int) (fileContent.length / eachSize);
        else
            fileNumber = (int) (fileContent.length / eachSize) + 1;

        for (int i = 0; i < fileNumber; i++) {
            String eachFileName = srcFile.getName() + "-" + i;
            File eachFile = new File(srcFile.getParent(), eachFileName);
            byte[] eachContent;

            // 从源文件的内容里，复制部分数据到子文件
            // 除开最后一个文件，其他文件大小都是100k
            // 最后一个文件的大小是剩余的
            if (i != fileNumber - 1) // 不是最后一个
                eachContent = Arrays.copyOfRange(fileContent, eachSize * i, eachSize * (i + 1));
            else // 最后一个
                eachContent = Arrays.copyOfRange(fileContent, eachSize * i, fileContent.length);

            try {
                // 写出去
                FileOutputStream fos = new FileOutputStream(eachFile);
                fos.write(eachContent);
                // 记得关闭
                fos.close();
                System.out.printf("输出子文件%s，其大小是 %d字节%n", eachFile.getAbsoluteFile(), eachFile.length());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
