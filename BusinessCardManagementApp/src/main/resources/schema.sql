DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS login_user;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS businesscard_list;

-- ロール
CREATE TABLE roles(
    id INTEGER PRIMARY KEY,    -- ロールのID
    name VARCHAR(32) NOT NULL  -- ロールの名前
);

-- ユーザー
CREATE TABLE login_user(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,         -- ユーザーのID
    name VARCHAR(128) NOT NULL,     -- ユーザーの表示名
    email VARCHAR(256) NOT NULL,    -- メールアドレス（ログイン時に利用）
    password VARCHAR(128) NOT NULL  -- ハッシュ化済みのパスワード
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ユーザーとロールの対応付け
CREATE TABLE user_role(
    user_id INTEGER,    -- ユーザーのID
    role_id INTEGER,    -- ロールのID
    number INTEGER AUTO_INCREMENT, --重複用
    CONSTRAINT pk_user_role PRIMARY KEY (number),
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES login_user(id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 名刺登録情報
CREATE TABLE businesscard_list(
	 id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- ロールのID
    user_email VARCHAR(256) NOT NULL,    -- メールアドレス（ログイン時に利用
    company VARCHAR(256) NOT NULL,    -- 社名
    business_name VARCHAR(128) NOT NULL,     -- 名刺の名前
    card_image VARCHAR(256),    -- カード画像情報
    bookmark BOOLEAN DEFAULT FALSE  -- ブックマーク
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;