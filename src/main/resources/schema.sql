-- 既存テーブルの削除
--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS temporary_users;

--ユーザー情報
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE, -- ここに UNIQUE を追加
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_partner_linked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--ユーザーの新規追加
--　仮登録ユーザー情報
CREATE TABLE IF NOT EXISTS temporary_users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL, -- ハッシュ化されたパスワード
    email VARCHAR(255) NOT NULL,
    verification_code VARCHAR(6) NOT NULL,
    expiry_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    --メールアドレスと認証コードでアドレス重複を許容する。(認証ミスした時のリカバリーの為)
    CONSTRAINT uk_email_code UNIQUE (email, verification_code)
);

