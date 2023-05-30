INSERT INTO roles(id, name) VALUES(1, 'ROLE_GENERAL');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');

-- password = "general"
INSERT INTO login_user(id, name, email, password) VALUES(1, '一般太郎', 'general@example.com', '$2a$10$bK3t1700KSD2Kw0VlcjmZe7/MS0dWtpqsPCjmjAWOSEaGBoGiOD2K');
-- password = "admin"
INSERT INTO login_user(id, name, email, password) VALUES(2, '管理太郎', 'admin@example.com', '$2a$10$SJTWvNl16fCU7DaXtWC0DeN/A8IOakpCkWWNZ/FKRV2CHvWElQwMS');


INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 2);

INSERT INTO businesscard_list(id, user_email, company, business_name, card_image, bookmark) VALUES (1, 'admin@example.com', '株式会社タロウ', '山田 太郎', '/static/upload/15-05-20-1.jpg', '1');
INSERT INTO businesscard_list(id, user_email, company, business_name, card_image, bookmark) VALUES (2, 'general@example.com', 'ジロウ株式会社', '五十嵐 二郎', '/static/upload/angel-3261548_1280.jpg', '0');