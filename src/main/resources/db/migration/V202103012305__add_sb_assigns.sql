CREATE TABLE `sb_assigns`
(
    `id`         bigint(20)    NOT NULL AUTO_INCREMENT,
    `amount`     double(10, 2) NOT NULL,
    `created_at` datetime DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `item_id`    bigint(20)    NOT NULL,
    `user_id`    bigint(20)    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

ALTER TABLE sb_assigns
    ADD CONSTRAINT fk_sb_assigns_sb_item_item_id FOREIGN KEY (item_id) REFERENCES sb_item (id);

ALTER TABLE sb_assigns
    ADD CONSTRAINT fk_sb_assigns_user_user_id FOREIGN KEY (user_id) REFERENCES user (id);
