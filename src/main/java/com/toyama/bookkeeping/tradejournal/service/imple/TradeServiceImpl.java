package com.toyama.bookkeeping.tradejournal.service.imple;

import com.toyama.bookkeeping.tradejournal.entity.Trade;
import com.toyama.bookkeeping.tradejournal.service.TradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TradeServiceImpl implements TradeService {

    @Override
    public List<Trade> getAllTradesByUserId(UUID userId) {
        List<Trade> dummyList = new ArrayList<>();

        Trade t1 = new Trade();
        t1.setId(UUID.randomUUID());
        t1.setPair("USD/JPY");
        t1.setSide("BUY");
        t1.setEntryPrice(new BigDecimal("150.500"));
        t1.setExitPrice(new BigDecimal("151.200"));
        t1.setPips(new BigDecimal("70.0"));
        t1.setProfitLoss(new BigDecimal("10500"));
        t1.setEntryAt(LocalDateTime.now());

        dummyList.add(t1);
        return dummyList;
    }
}