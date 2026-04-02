package org.example.EventsCalendar.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class CrawlerService {

    private final AiClient aiClient;

    public CrawlerService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public String crawlAndProcess(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String textContent = doc.body().text();

        // Get today's date
        java.time.LocalDate today = java.time.LocalDate.now();
        String dateString = today.format(java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy"));

        String promptText = String.format(
            "Find all event names and their dates from the following text. Only include events occurring after %s. Return the result as a JSON list of objects with fields: name, date (ISO format), and sourceUrl.",
            dateString, url
        );

        return aiClient.generate(new Prompt(promptText)).getGeneration().getText();
    }
}