-- migrate:up
CREATE TABLE `budget_bracket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bracket` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(36) NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_by` varchar(36) NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
-- migrate:down

