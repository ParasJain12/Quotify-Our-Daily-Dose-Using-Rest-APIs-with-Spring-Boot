package com.quote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.quote.service.DailyQuoteManager;

@RestController
public class QuoteController {

    @Autowired
    private DailyQuoteManager dailyQuoteManager;

    private static final String ZENQUOTES_API_URL = "https://zenquotes.io/api/random";

    @GetMapping("/daily-quote")
    public String getDailyQuote() {
        String storedQuote = dailyQuoteManager.getDailyQuote();
        if (storedQuote == null) {
            String newQuote = fetchQuoteFromAPI();
            dailyQuoteManager.setDailyQuote(newQuote);
            return "Fetched and stored a new daily quote: " + newQuote;
        } else {
            return "Today's daily quote: " + storedQuote;
        }
    }

    private String fetchQuoteFromAPI() {
        // Fetch a new quote from the Zenquotes API
        RestTemplate restTemplate = new RestTemplate();
        String quoteResponse = restTemplate.getForObject(ZENQUOTES_API_URL, String.class);

        // Extract the quote from the JSON response
        String newQuote = extractQuoteFromApiResponse(quoteResponse);

        return newQuote != null ? newQuote : "Failed to fetch a new quote from the API.";
    }

    private String extractQuoteFromApiResponse(String apiResponse) {
        // Parse the JSON response from the Zenquotes API and extract the quote
        // This is a simple extraction; you may need a more sophisticated approach depending on the actual API response format
        // In a real-world scenario, consider using a JSON parsing library like Jackson or Gson
        int startIndex = apiResponse.indexOf("q\":") + 4;
        int endIndex = apiResponse.indexOf("\",", startIndex);

        return (startIndex > 4 && endIndex > 0) ? apiResponse.substring(startIndex, endIndex) : null;
    }
}
