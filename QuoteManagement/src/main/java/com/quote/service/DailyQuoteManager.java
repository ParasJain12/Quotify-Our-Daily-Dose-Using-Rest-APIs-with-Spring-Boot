package com.quote.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import org.springframework.stereotype.Service;

@Service
public class DailyQuoteManager {

    private static final String QUOTE_FILE_PATH = "daily_quote.txt";

    public String getDailyQuote() {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUOTE_FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(";");
                LocalDate storedDate = LocalDate.parse(parts[0]);
                if (storedDate.isEqual(LocalDate.now())) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDailyQuote(String quote) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUOTE_FILE_PATH))) {
            String dataToWrite = LocalDate.now() + ";" + quote;
            writer.write(dataToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}