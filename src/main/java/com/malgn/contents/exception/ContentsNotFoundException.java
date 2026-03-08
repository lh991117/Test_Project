package com.malgn.contents.exception;

public class ContentsNotFoundException extends RuntimeException{

    public ContentsNotFoundException(Long id) {
        super("콘텐츠를 찾을 수 없습니다. id= " + id);
    }
}
