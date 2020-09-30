CREATE TABLE `user_state`
(
    `user_id`  bigint(20)   NOT NULL,
    `state`    varchar(255) NOT NULL,
    `argument` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

ALTER TABLE user_state
    ADD CONSTRAINT fk_user_state_user_user_id FOREIGN KEY (user_id) REFERENCES user (id);
