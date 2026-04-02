package org.example.EventsCalendar.services;

import org.example.EventsCalendar.models.ExtractedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerService {

    private final ChatModel chatModel;

    public CrawlerService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public List<ExtractedEvent> crawlAndExtractEvents(String url) throws IOException {
        // 1. Use Jsoup to fetch and extract all text content from the URL
        Document doc = Jsoup.connect(url).get();
        String textContent = doc.text();

        // 2. Set up the BeanOutputConverter to map the AI response to a List of ExtractedEvent records
        BeanOutputConverter<List<ExtractedEvent>> converter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<ExtractedEvent>>() {}
        );

        // 3. Define the prompt template
        String promptText = """
                Find all event names and their dates from the following text. 
                Only include events occurring after {todayDate}. 
                Return the result as a JSON list of objects with fields: name, date (ISO format), and sourceUrl.
                The sourceUrl for all events should be {url}.
                
                Text to analyze:
                {text}
                
                {format}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        
        // 4. Create the prompt with variables
        Prompt prompt = promptTemplate.create(Map.of(
                "todayDate", LocalDate.now().toString(),
                "url", url,
                "text", textContent,
                "format", converter.getFormat()
        ));

        // 5. Call the Spring AI ChatModel
        String response = chatModel.call(prompt).getResult().getOutput().getText();

        // 6. Convert the JSON response back into Java objects
        return converter.convert(response);
    }
}
