-- Populate author table
INSERT INTO author (id, name) VALUES
(1, 'John Doe'),
(2, 'Jane Smith');

-- Populate news table
INSERT INTO news (id, title, content, author_id, created, modified) VALUES
(1, 'Breaking News 1', 'Content of breaking news 1', 1, '2024-05-14T12:00:00Z', '2024-05-14T12:00:00Z'),
(2, 'Breaking News 2', 'Content of breaking news 2', 2, '2024-05-14T13:00:00Z', '2024-05-14T13:00:00Z');

-- Populate comment table
INSERT INTO comment (id, content, news_id, created, modified) VALUES
(1, 'Comment 1 for Breaking News 1', 1, '2024-05-14T12:30:00Z', '2024-05-14T12:30:00Z'),
(2, 'Comment 2 for Breaking News 1', 1, '2024-05-14T12:45:00Z', '2024-05-14T12:45:00Z'),
(3, 'Comment 1 for Breaking News 2', 2, '2024-05-14T13:15:00Z', '2024-05-14T13:15:00Z');

-- Populate tag table
INSERT INTO tag (id, name) VALUES
(1, 'Tag 1'),
(2, 'Tag 2');

-- Populate news_tag table
INSERT INTO newstag (id, news_id, tag_id) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 2);

-- Restart sequences
DO $$
BEGIN
    PERFORM setval(pg_get_serial_sequence('author', 'id'), coalesce(max(id), 0) + 1, false) FROM author;
    PERFORM setval(pg_get_serial_sequence('news', 'id'), coalesce(max(id), 0) + 1, false) FROM news;
    PERFORM setval(pg_get_serial_sequence('comment', 'id'), coalesce(max(id), 0) + 1, false) FROM comment;
    PERFORM setval(pg_get_serial_sequence('tag', 'id'), coalesce(max(id), 0) + 1, false) FROM tag;
    PERFORM setval(pg_get_serial_sequence('newstag', 'id'), coalesce(max(id), 0) + 1, false) FROM newstag;
END $$;
