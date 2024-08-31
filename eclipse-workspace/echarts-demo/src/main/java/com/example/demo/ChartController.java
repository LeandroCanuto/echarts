package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("chartData", getChartData());
        return "index";
    }

    private String getChartData() {
        return "{"
                + "\"title\": { \"text\": \"ECharts Demo\" },"
                + "\"tooltip\": {},"
                + "\"xAxis\": { \"data\": [\"A\", \"B\", \"C\", \"D\", \"E\", \"F\"] },"
                + "\"yAxis\": {},"
                + "\"series\": [{ \"type\": \"bar\", \"data\": [5, 20, 36, 10, 10, 20] }]"
                + "}";
    }
}
