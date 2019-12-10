UPDATE event
SET is_opened = false;
UPDATE event
SET closed_at = NOW();
