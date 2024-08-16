-- Clear existing data in dependent tables
DELETE FROM news_tag;
DELETE FROM comment;
DELETE FROM news;

-- Then clear data in the author table
DELETE FROM author;
DELETE FROM tag;

-- Restart the auto-increment sequences
ALTER TABLE author ALTER COLUMN id RESTART WITH 1;
ALTER TABLE news ALTER COLUMN id RESTART WITH 1;
ALTER TABLE comment ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tag ALTER COLUMN id RESTART WITH 1;
ALTER TABLE news_tag ALTER COLUMN id RESTART WITH 1;

-- Populate author table
INSERT INTO author (name) VALUES
('John Doe'),
('Jane Smith');


-- Populate news table
INSERT INTO news (title, content, author_id, created, modified) VALUES
('Breaking News 1', 'Content of breaking news 1', 1, '2024-05-14T12:00:00Z', '2024-05-14T12:00:00Z'),
('Breaking News 2', 'Content of breaking news 2', 2, '2024-05-14T13:00:00Z', '2024-05-14T13:00:00Z');

-- Populate comment table
INSERT INTO comment (content, news_id, created, modified) VALUES
('Comment 1 for Breaking News 1', 1, '2024-05-14T12:30:00Z', '2024-05-14T12:30:00Z'),
('Comment 2 for Breaking News 1', 1, '2024-05-14T12:45:00Z', '2024-05-14T12:45:00Z'),
('Comment 1 for Breaking News 2', 2, '2024-05-14T13:15:00Z', '2024-05-14T13:15:00Z');

-- Populate tag table
INSERT INTO tag (name) VALUES
('Tag 1'),
('Tag 2');

-- Populate news_tag table
INSERT INTO news_tag (news_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 2);


---ALTER TABLE author ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM author) + 1;
---ALTER TABLE news ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM news) + 1;
---ALTER TABLE comment ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM comment) + 1;
---ALTER TABLE tag ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM tag) + 1;
---ALTER TABLE newstag ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM newstag) + 1;