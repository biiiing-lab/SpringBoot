package org.example.ch05.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ch05.domain.Article;

import java.time.LocalDateTime;

@AllArgsConstructor // 클래스가 가지고 있는 모든 필드에 생성자를 추가
@NoArgsConstructor // 기본 생성자
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
