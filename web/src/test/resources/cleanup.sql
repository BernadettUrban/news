
-- Cleanup script
-- Clear dependent tables first to avoid foreign key constraints issues
DELETE FROM comment;
DELETE FROM newstag;
DELETE FROM news;
DELETE FROM author;
DELETE FROM tag;
