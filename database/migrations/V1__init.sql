CREATE TABLE IF NOT EXISTS simulator_rules (
    id UUID PRIMARY KEY,
    protocol VARCHAR(50) NOT NULL,
    matcher_content TEXT NOT NULL,
    response_template TEXT NOT NULL,
    latency_ms INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transaction_logs (
    id UUID PRIMARY KEY,
    correlation_id VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    protocol VARCHAR(50),
    message_type VARCHAR(50),
    request_payload TEXT,
    response_payload TEXT,
    latency_ms INT
);

-- Index for filtering
CREATE INDEX IF NOT EXISTS idx_txn_logs_timestamp ON transaction_logs (timestamp);
CREATE INDEX IF NOT EXISTS idx_txn_logs_protocol ON transaction_logs (protocol);
CREATE INDEX IF NOT EXISTS idx_txn_logs_msg_type ON transaction_logs (message_type);
