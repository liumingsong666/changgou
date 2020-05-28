package com.song.idutil;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 11:12
 * @description:
 */

public class IdGeneratorException extends RuntimeException {

    public IdGeneratorException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
