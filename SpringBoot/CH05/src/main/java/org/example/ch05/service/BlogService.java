package org.example.ch05.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.ch05.domain.Article;
import org.example.ch05.dto.AddArticleRequest;
import org.example.ch05.dto.ArticleResponse;
import org.example.ch05.dto.UpdateArticleRequest;
import org.example.ch05.repository.ArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final ArticleRepository articleRepository;

    // 저장
    public Article save(AddArticleRequest request) {
         return articleRepository.save(request.toEntity());
    }

    // 전체 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // 세부
    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
    }

    // 삭제
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest articleRequest) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        article.update(articleRequest.getTitle(), articleRequest.getContent());
        return article;
    }

}
