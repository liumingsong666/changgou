package com.changgou.file;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FastDfsUtil {

    private static StorageClient storageClient;

    static {
        String path = null;
        try {
            path = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer connection = trackerClient.getConnection();
            storageClient= new StorageClient(connection, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String[] upload(FileBean fileBean) throws IOException, MyException {
        String[] strings = storageClient.upload_file(fileBean.getContent(), fileBean.getExt(), null);
        return strings;
    }

    public static String downLoad(String group,String filepath) throws IOException, MyException {
        byte[] bytes = storageClient.download_file(group, filepath);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        FileOutputStream fileOutputStream = new FileOutputStream("D:/a.jpg");
        byte[] buffer = new byte[1024];
        while(byteArrayInputStream.read(buffer)!=-1){
            fileOutputStream.write(buffer);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        byteArrayInputStream.close();
        return "文件下载成功";
    }

    public static String deleteFile(String group,String filepath) throws IOException, MyException {
        storageClient.delete_file(group,filepath);
        return "删除成功";
    }
}
