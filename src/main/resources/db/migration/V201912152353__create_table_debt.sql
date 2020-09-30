CREATE TABLE `debt`
(
    `id`         bigint(20)    NOT NULL AUTO_INCREMENT,
    `amount`     double(10, 2) NOT NULL,
    `currency`   varchar(255)  NOT NULL,
    `comment`    varchar(255) DEFAULT NULL,
    `debtor_id`  bigint(20)    NOT NULL,
    `payer_id`   bigint(20)    NOT NULL,
    `created_at` datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;