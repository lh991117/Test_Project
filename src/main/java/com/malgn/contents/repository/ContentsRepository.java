package com.malgn.contents.repository;

import com.malgn.contents.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
}
