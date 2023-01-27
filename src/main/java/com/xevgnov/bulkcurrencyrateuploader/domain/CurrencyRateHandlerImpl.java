package com.xevgnov.bulkcurrencyrateuploader.domain;


import com.xevgnov.bulkcurrencyrateuploader.dto.CurrencyRateDto;
import com.xevgnov.bulkcurrencyrateuploader.exception.NbpApiException;
import com.xevgnov.bulkcurrencyrateuploader.service.CurrencyRateGetter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class CurrencyRateHandlerImpl implements CurrencyRateHandler {

    private CurrencyRateGetter currencyRateGetter;

    public CurrencyRateHandlerImpl(CurrencyRateGetter currencyRateGetter) {
        this.currencyRateGetter = currencyRateGetter;
    }

    @Override
    public void handle() throws IOException, NbpApiException {

        Queue<String> input = getFileRows("src/main/resources/input.txt");
        String currency = input.poll();

        List<String> output = new ArrayList<>();
        String date = "";
        while (!input.isEmpty()) {
            String currentDate = input.poll();
            if (date.equals(currentDate)) {
                output.add(output.get(output.size() - 1));
            } else {
                CurrencyRateDto response = currencyRateGetter.getCurrencyRate(currentDate, currency);
                output.add(response.getRates().get(0).getMid());
            }
            date = currentDate;
        }
        writeToFile(output);
    }

    private Queue<String> getFileRows(String fileName) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(fileName));
        return new LinkedList<>(allLines);
    }

    private void writeToFile(List<String> data) {
        Path path = Paths.get("src/main/resources/output.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (String row : data) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
