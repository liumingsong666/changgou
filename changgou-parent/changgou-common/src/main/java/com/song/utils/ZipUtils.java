/**
 * flyiu.com Inc.
 * Copyright (c) 2018-2019 All Rights Reserved.
 */
package com.song.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/** 
 * <p>
 * </p>
 *
 */
public class ZipUtils {

    public static String compress(String source) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            GZIPOutputStream gos = new GZIPOutputStream(bos);
            gos.write(source.getBytes("ISO-8859-1"));
            gos.close();
            return bos.toString("ISO-8859-1");
        } catch (IOException var3) {
            System.out.println(var3.getStackTrace().toString());
            return source;
        }
    }

    public static String uncompress(String source) {
        if (source != null && source.length() != 0) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ByteArrayInputStream bis = new ByteArrayInputStream(source.getBytes("ISO-8859-1"));
                GZIPInputStream gis = new GZIPInputStream(bis);
                byte[] data = new byte[1024];

                for (int len = gis.read(data); len != -1; len = gis.read(data)) {
                    bos.write(data, 0, len);
                }

                return bos.toString();
            } catch (IOException var6) {
                System.out.println(var6.getStackTrace().toString());
                return source;
            }
        } else {
            return source;
        }
    }

}
