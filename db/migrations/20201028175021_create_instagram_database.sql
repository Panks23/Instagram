-- migrate:up
create table if not exists contact (
	id bigint AUTO_INCREMENT PRIMARY KEY,
    primary_contact VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    type VARCHAR(10),
    rpx_reference_id VARCHAR(20) UNIQUE NOT NULL ,
    active boolean,
	created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    INDEX(primary_contact)
);


-- migrate:down

