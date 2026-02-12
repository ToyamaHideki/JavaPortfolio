package com.toyama.bookkeeping.tradejournal.mapper;

import com.toyama.bookkeeping.tradejournal.entity.Trade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TradeMapper {
    /**
     * 指定したユーザーのトレード履歴をすべて取得する
     */
    @Select("SELECT * FROM trades WHERE user_id = #{userId, typeHandler=org.apache.ibatis.type.ObjectTypeHandler} ORDER BY entry_at DESC")
    List<Trade> findAllByUserId(@Param("userId") UUID userId);

    /**
     * トレード記録を新規登録する
     */
    @Insert("""
        INSERT INTO trades (
            id, user_id, pair, side, entry_price, exit_price, 
            stop_loss_price, take_profit_price, lot, profit_loss, 
            pips, risk_reward_ratio, entry_at, exit_at, strategy, 
            time_frame, entry_reason, exit_reason, self_evaluation, memo
        ) VALUES (
            #{id, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}, 
            #{userId, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}, 
            #{pair}, #{side}, #{entryPrice}, #{exitPrice}, 
            #{stopLossPrice}, #{takeProfitPrice}, #{lot}, #{profitLoss}, 
            #{pips}, #{riskRewardRatio}, #{entryAt}, #{exitAt}, #{strategy}, 
            #{timeFrame}, #{entryReason}, #{exitReason}, #{selfEvaluation}, #{memo}
        )
        """)
    void insert(Trade trade);


    /**
     * 特定のトレード詳細を取得する（後で編集や削除で使います）
     */
    @Select("SELECT * FROM trades WHERE id = #{id, typeHandler=org.apache.ibatis.type.ObjectTypeHandler}")
    Trade findById(@Param("id") UUID id);
}
