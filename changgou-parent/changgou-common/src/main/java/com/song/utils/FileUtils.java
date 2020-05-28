package com.song.utils;

import java.io.File;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 15:54
 * @description:
 */

public class FileUtils {
    public FileUtils() {
    }

    public static void deleteFile(File target) {
        if (target != null && target.exists()) {
            if (target.isFile()) {
                target.delete();
            } else {
                File[] listFiles = target.listFiles();
                File[] var2 = listFiles;
                int var3 = listFiles.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    File file = var2[var4];
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        deleteFile(file);
                    }
                }

                target.delete();
            }

        }
    }

    public static void deleteFile(String target) {
        if (target != null) {
            deleteFile(new File(target));
        }
    }

    public static void main(String[] args) {
        String path = "C:/Users/Thinkpad/AppData/Local/Temp/1413962856215";
        deleteFile(path);
    }
}
