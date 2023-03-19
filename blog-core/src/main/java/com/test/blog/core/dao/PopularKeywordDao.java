package com.test.blog.core.dao;

import com.test.blog.core.model.PopularSearchKeyword;
import com.test.blog.core.repository.PopularSearchKeywordCustomRepository;
import com.test.blog.core.repository.PopularSearchKeywordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularKeywordDao {

    private final PopularSearchKeywordRepository repository;

    private final PopularSearchKeywordCustomRepository customRepository;

    public PopularSearchKeyword save(PopularSearchKeyword entity) {
        return repository.save(entity);
    }

    public PopularSearchKeyword findByKeyword(String keyword) {
        return customRepository.findByKeyword(keyword);
    }

    public List<PopularSearchKeyword> findPopularKeywords() {
        return customRepository.findPopularKeywords();
    }

}
