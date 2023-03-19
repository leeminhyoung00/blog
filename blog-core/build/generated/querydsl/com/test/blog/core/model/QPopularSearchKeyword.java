package com.test.blog.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPopularSearchKeyword is a Querydsl query type for PopularSearchKeyword
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPopularSearchKeyword extends EntityPathBase<PopularSearchKeyword> {

    private static final long serialVersionUID = 871954335L;

    public static final QPopularSearchKeyword popularSearchKeyword = new QPopularSearchKeyword("popularSearchKeyword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keyword = createString("keyword");

    public final NumberPath<Long> searchCount = createNumber("searchCount", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QPopularSearchKeyword(String variable) {
        super(PopularSearchKeyword.class, forVariable(variable));
    }

    public QPopularSearchKeyword(Path<? extends PopularSearchKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPopularSearchKeyword(PathMetadata metadata) {
        super(PopularSearchKeyword.class, metadata);
    }

}

