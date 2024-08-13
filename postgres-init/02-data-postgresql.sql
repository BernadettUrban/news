-- Populate author table
INSERT INTO author (name) VALUES
('John Doe'),
('Jane Smith');

-- Populate news table
INSERT INTO news (title, content, author_id, created, modified) VALUES
('Breaking News 1', 'Content of breaking news 1', 1, '2024-05-14T12:00:00Z'::timestamptz, '2024-05-14T12:00:00Z'::timestamptz),
('Breaking News 2', 'Content of breaking news 2', 2, '2024-05-14T13:00:00Z'::timestamptz, '2024-05-14T13:00:00Z'::timestamptz);

-- Populate comment table
INSERT INTO comment (content, news_id, created, modified) VALUES
('Comment 1 for Breaking News 1', 1, '2024-05-14T12:30:00Z'::timestamptz, '2024-05-14T12:30:00Z'::timestamptz),
('Comment 2 for Breaking News 1', 1, '2024-05-14T12:45:00Z'::timestamptz, '2024-05-14T12:45:00Z'::timestamptz),
('Comment 1 for Breaking News 2', 2, '2024-05-14T13:15:00Z'::timestamptz, '2024-05-14T13:15:00Z'::timestamptz);

-- Populate tag table
INSERT INTO tag (name) VALUES
('Tag 1'),
('Tag 2');

-- Populate news_tag table
INSERT INTO news_tag (news_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 2);

-- Reset sequences for auto-increment
-- Assuming the id columns are of serial or identity type

-- For the author table
--SELECT setval(pg_get_serial_sequence('author', 'id'), COALESCE((SELECT MAX(id) FROM author), 1), false);

-- For the news table
--SELECT setval(pg_get_serial_sequence('news', 'id'), COALESCE((SELECT MAX(id) FROM news), 1), false);

-- For the comment table
--SELECT setval(pg_get_serial_sequence('comment', 'id'), COALESCE((SELECT MAX(id) FROM comment), 1), false);

-- For the tag table
--SELECT setval(pg_get_serial_sequence('tag', 'id'), COALESCE((SELECT MAX(id) FROM tag), 1), false);

-- For the news_tag table
--SELECT setval(pg_get_serial_sequence('news_tag', 'id'), COALESCE((SELECT MAX(id) FROM news_tag), 1), false);
