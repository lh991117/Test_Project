package com.malgn.contents.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "contents")
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    private LocalDateTime createdDate;

    @Column(nullable = false, length = 50)
    private String createdBy;

    private LocalDateTime lastModifiedDate;

    @Column(length = 50)
    private String lastModifiedBy;

    public void update(String title, String description, String lastModifiedBy) {
        this.title = title;
        this.description = description;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
