package com.toyama.bookkeeping.tradejournal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Trade {
    private UUID id;
    private UUID userId;
    private String pair;          // 通貨ペア (USD/JPYなど)
    private String side;          // 売買 (BUY/SELL)
    private BigDecimal entryPrice;
    private BigDecimal exitPrice;
    private BigDecimal stopLossPrice;
    private BigDecimal takeProfitPrice;
    private BigDecimal lot;
    private BigDecimal profitLoss; // 損益金額
    private BigDecimal pips;       // 獲得pips
    private BigDecimal riskRewardRatio;
    private LocalDateTime entryAt;
    private LocalDateTime exitAt;
    private String strategy;       // 手法名
    private String timeFrame;      // 執行足 (5M, 1Hなど)
    private String entryReason;
    private String exitReason;
    private Integer selfEvaluation; // 1-5の自己評価
    private String memo;

}
