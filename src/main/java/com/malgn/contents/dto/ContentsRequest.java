package com.malgn.contents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ContentsRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String description;
}
