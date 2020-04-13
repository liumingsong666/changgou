package com.changgou.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileBean {

    private byte[] content;
    //文件拓展名
    private String ext;
    private String name;
    //文件md5摘要
    private String md5;
    private String author;

    public FileBean(byte[] content, String ext, String name, String md5, String author) {
        this.content = content;
        this.ext = ext;
        this.name = name;
        this.md5 = md5;
        this.author = author;
    }

    public FileBean(byte[] content, String ext) {
        this.content = content;
        this.ext = ext;
    }

    public FileBean(byte[] content, String ext, String name) {
        this.content = content;
        this.ext = ext;
        this.name = name;
    }
}
