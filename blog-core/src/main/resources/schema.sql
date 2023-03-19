CREATE TABLE IF NOT EXISTS popular_search_keyword (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    keyword VARCHAR(255) NOT NULL UNIQUE,
    search_count BIGINT NOT NULL DEFAULT 0,
    version INT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_search_keyword ON popular_search_keyword (keyword);
