CREATE TABLE `sb_bill`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `created_at` datetime   DEFAULT NULL,
    `title`      varchar(255) NOT NULL,
    `currency`   varchar(255) NOT NULL,
    `updated_at` datetime   DEFAULT NULL,
    `owner_id`   bigint(20)   NOT NULL,
    `is_opened`  TINYINT(1) DEFAULT 1,
    `closed_at`  DATETIME   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `sb_bill_user`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `bill_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

ALTER TABLE sb_bill
    ADD CONSTRAINT fk_sb_bill_user_owner_id FOREIGN KEY (owner_id) REFERENCES user (id);
ALTER TABLE sb_bill_user
    ADD CONSTRAINT fk_sb_bill_user_user_user_id FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE sb_bill_user
    ADD CONSTRAINT fk_sb_bill_user_bill_bill_id FOREIGN KEY (bill_id) REFERENCES sb_bill (id);

