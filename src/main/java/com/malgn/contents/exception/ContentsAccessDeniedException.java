package com.malgn.contents.exception;

public class ContentsAccessDeniedException extends RuntimeException {

    public ContentsAccessDeniedException() {
        super("해당 콘텐츠를 수정 또는 삭제할 권한이 없습니다.");
    }
}
