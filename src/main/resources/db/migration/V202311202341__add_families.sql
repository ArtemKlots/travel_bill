CREATE TABLE `family`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `budget_resolver_id` bigint(20) NOT NULL,
    `created_at`         datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`         datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `family_members`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `family_id`  bigint(20) NOT NULL,
    `user_id`    bigint(20) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;


ALTER TABLE family
    ADD CONSTRAINT fk_family_user_budgetResolver FOREIGN KEY (budget_resolver_id) REFERENCES user (id);

ALTER TABLE family_members
    ADD CONSTRAINT fk_family_members_family_family_id FOREIGN KEY (family_id) REFERENCES family (id);

ALTER TABLE family_members
    ADD CONSTRAINT fk_family_members_user_user_id FOREIGN KEY (user_id) REFERENCES user (id);

