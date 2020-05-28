INSERT INTO user_state (user_id, state)
SELECT id, 'START '
FROM `user`