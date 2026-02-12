package com.toyama.bookkeeping.tradejournal.controller;

import com.toyama.bookkeeping.tradejournal.entity.Trade;
import com.toyama.bookkeeping.tradejournal.service.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/trade-journal")
public class TradeJournalController {

    private final TradeService tradeService;

    // コンストラクタ注入
    public TradeJournalController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public String index(Model model) {
        // 本来はログインユーザーのIDを渡しますが、今は適当なUUIDでOK
        List<Trade> trades = tradeService.getAllTradesByUserId(UUID.randomUUID());
        model.addAttribute("trades", trades);

        return "tradejournal/index";
    }


}
