package com.toyama.bookkeeping.tradejournal.service;

import com.toyama.bookkeeping.tradejournal.entity.Trade;

import java.util.List;
import java.util.UUID;

public interface TradeService {

    List<Trade> getAllTradesByUserId(UUID userId);

}
