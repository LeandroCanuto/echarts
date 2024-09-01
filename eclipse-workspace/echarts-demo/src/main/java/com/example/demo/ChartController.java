package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ChartController {

    @Autowired
    private ContaCartaoService contaCartaoService;

    @GetMapping("/")
    public String index(Model model) {
        // Obtém os dados do serviço
        Map<YearMonth, Long> data = contaCartaoService.getContratacoesPorMes();

        if (data == null || data.isEmpty()) {
            model.addAttribute("chartData", "{}"); // Passa um JSON vazio se não houver dados
            return "index";
        }

        // Ordenar os dados por YearMonth
        Map<YearMonth, Long> sortedData = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // Mantém a ordem de inserção
                ));

        // Converte YearMonth para String no formato "yyyy-MM"
        Map<String, Long> formattedData = sortedData.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        Map.Entry::getValue
                ));

        // Converte os dados formatados para JSON
        String jsonData = convertToJson(formattedData);
        model.addAttribute("chartData", jsonData); // Passa os dados JSON para o modelo

        return "index"; // Nome da view (template Thymeleaf)
    }

    private String convertToJson(Map<String, Long> data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}"; // Retorna um JSON vazio em caso de erro
        }
    }
}
