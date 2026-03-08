package com.malgn.contents.controller;

import com.malgn.contents.dto.ContentsRequest;
import com.malgn.contents.dto.ContentsResponse;
import com.malgn.contents.service.ContentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContentsResponse create(
            @Valid @RequestBody ContentsRequest contentsRequest,
            Authentication authentication
    ){
        return contentsService.create(contentsRequest, authentication.getName());
    }

    @GetMapping
    public Page<ContentsResponse> getContents(Pageable pageable) {
        return contentsService.getContents(pageable);
    }

    @GetMapping("/{id}")
    public ContentsResponse getContent(@PathVariable Long id) {
        return contentsService.getContent(id);
    }

    @PutMapping("/{id}")
    public ContentsResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ContentsRequest request,
            Authentication authentication
    ){
        return contentsService.update(
                id,
                request,
                authentication.getName(),
                authentication.getAuthorities()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        contentsService.delete(
                id,
                authentication.getName(),
                authentication.getAuthorities()
        );
    }
}
