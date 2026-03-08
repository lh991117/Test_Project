package com.malgn.contents.service;

import com.malgn.contents.domain.Contents;
import com.malgn.contents.dto.ContentsRequest;
import com.malgn.contents.dto.ContentsResponse;
import com.malgn.contents.exception.ContentsAccessDeniedException;
import com.malgn.contents.exception.ContentsNotFoundException;
import com.malgn.contents.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentsService {

    private final ContentsRepository contentsRepository;

    @Transactional
    public ContentsResponse create(ContentsRequest contentsRequest, String loginId) {
        Contents contents = Contents.builder()
                .title(contentsRequest.getTitle())
                .description(contentsRequest.getDescription())
                .viewCount(0L)
                .createdDate(LocalDateTime.now())
                .createdBy(loginId)
                .build();

        Contents saved = contentsRepository.save(contents);
        return ContentsResponse.from(saved);
    }

    public Page<ContentsResponse> getContents(Pageable pageable){
        return contentsRepository.findAll(pageable)
                .map(ContentsResponse::from);
    }

    @Transactional
    public ContentsResponse getContent(Long id) {
        Contents contents = contentsRepository.findById(id).
                orElseThrow(() -> new ContentsNotFoundException(id));

        contents.increaseViewCount();

        return ContentsResponse.from(contents);
    }

    @Transactional
    public ContentsResponse update(Long id,
                                   ContentsRequest contentsRequest,
                                   String loginId,
                                   Collection<? extends GrantedAuthority> authorities
    ) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new ContentsNotFoundException(id));

        validateCanModifyOrDelete(contents, loginId, authorities);

        contents.update(
                contentsRequest.getTitle(),
                contentsRequest.getDescription(),
                loginId
        );

        return ContentsResponse.from(contents);
    }

    @Transactional
    public void delete(
            Long id,
            String loginId,
            Collection<? extends GrantedAuthority> authorities
    ) {
        Contents contents = contentsRepository.findById(id)
                .orElseThrow(() -> new ContentsNotFoundException(id));

        validateCanModifyOrDelete(contents, loginId, authorities);

        contentsRepository.delete(contents);
    }

    private void validateCanModifyOrDelete(
            Contents contents,
            String loginId,
            Collection<? extends GrantedAuthority> authorities
    ){
        boolean isOwner = contents.getCreatedBy().equals(loginId);
        boolean isAdmin = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ADMIN"));

        if(!isOwner && !isAdmin){
            throw new ContentsAccessDeniedException();
        }
    }
}
