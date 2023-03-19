package com.test.blog.core.repository;

import static com.test.blog.core.model.QPopularSearchKeyword.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.blog.core.model.PopularSearchKeyword;
import com.test.blog.core.model.QPopularSearchKeyword;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PopularSearchKeywordCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PopularSearchKeyword findByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(popularSearchKeyword)
            .where(popularSearchKeyword.keyword.eq(keyword))
            .fetchOne();
    }

    public List<PopularSearchKeyword> findPopularKeywords() {
        return jpaQueryFactory.selectFrom(popularSearchKeyword)
            .orderBy(popularSearchKeyword.searchCount.desc())
            .limit(10)
            .fetch();
    }
}
