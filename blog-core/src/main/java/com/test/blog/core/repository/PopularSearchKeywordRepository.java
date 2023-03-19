package com.test.blog.core.repository;

import com.test.blog.core.model.PopularSearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularSearchKeywordRepository extends JpaRepository<PopularSearchKeyword, Long> {

}
