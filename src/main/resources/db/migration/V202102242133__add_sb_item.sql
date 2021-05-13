CREATE TABLE `sb_item`
(
    `id`         bigint(20)    NOT NULL AUTO_INCREMENT,
    `title`      varchar(255)  NOT NULL,
    `amount`     double(10, 2) NOT NULL,
    `price`      double(10, 2) NOT NULL,
    `created_at` datetime DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `bill_id`    bigint(20)    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

ALTER TABLE sb_item
    ADD CONSTRAINT fk_sb_item_sb_bill_bill_id FOREIGN KEY (bill_id) REFERENCES sb_bill (id);

