-- 既存テーブルの削除
--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS temporary_users;
--DROP TABLE IF EXISTS trades;
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


--売買記録
CREATE TABLE IF NOT EXISTS trades (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    pair VARCHAR(10) NOT NULL,
    side VARCHAR(4) NOT NULL,
    entry_price NUMERIC(20, 8) NOT NULL,
    exit_price NUMERIC(20, 8),
    stop_loss_price NUMERIC(20, 8),
    take_profit_price NUMERIC(20, 8),
    lot NUMERIC(10, 2) NOT NULL,
    profit_loss NUMERIC(20, 2),
    pips NUMERIC(10, 2),
    risk_reward_ratio NUMERIC(10, 2),
    entry_at TIMESTAMP NOT NULL,
    exit_at TIMESTAMP,
    strategy VARCHAR(50),
    time_frame VARCHAR(10),
    entry_reason TEXT,
    exit_reason TEXT,
    self_evaluation INTEGER CHECK (self_evaluation BETWEEN 1 AND 5),
    memo TEXT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 日本語名のコメント（DB管理ツールで見やすくなります）
COMMENT ON COLUMN trades.pair IS '通貨ペア';
COMMENT ON COLUMN trades.side IS '売買方向（BUY/SELL）';
COMMENT ON COLUMN trades.entry_price IS 'エントリー価格';
COMMENT ON COLUMN trades.stop_loss_price IS '損切設定価格';
COMMENT ON COLUMN trades.risk_reward_ratio IS 'リスクリワード比';
COMMENT ON COLUMN trades.entry_reason IS 'エントリー根拠';