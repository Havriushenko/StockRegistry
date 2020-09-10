package smida.test_task.havriushenko.stockRegestry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smida.test_task.havriushenko.stockRegestry.dto.HistoryDto;
import smida.test_task.havriushenko.stockRegestry.services.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<HistoryDto> getHistory() {
        return historyService.getHistory();
    }
}
