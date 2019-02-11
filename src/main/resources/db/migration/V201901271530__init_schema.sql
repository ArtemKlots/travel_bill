CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double (10, 2) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `telegram_chat_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `event_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `telegram_id` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `current_event_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE bill ADD CONSTRAINT fk_bill_event_event_id FOREIGN KEY (event_id) REFERENCES event(id);
ALTER TABLE bill ADD CONSTRAINT fk_bill_user_user_id FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE event ADD CONSTRAINT fk_event_user_owner_id FOREIGN KEY (owner_id) REFERENCES user(id);
ALTER TABLE event_user ADD CONSTRAINT fk_event_user_user_user_id FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE event_user ADD CONSTRAINT fk_event_user_event_event_id FOREIGN KEY (event_id) REFERENCES event(id);
ALTER TABLE user ADD CONSTRAINT fk_user_event_current_event_id FOREIGN KEY (current_event_id) REFERENCES event(id);
