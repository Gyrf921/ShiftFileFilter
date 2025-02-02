package org.example.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constant.Regex;

import java.util.*;

@Data
@NoArgsConstructor
public class FileDataCollector {

    private List<Long> integers = new ArrayList<>();

    private List<Double> floats = new ArrayList<>();

    private List<String> strings = new ArrayList<>();

    /**
     * Добавляет строку в соответствующий список (integers, floats или strings) в зависимости от её формата.
     * Если строка соответствует формату числа, она добавляется в соответствующий список.
     * В случае, если строка не может быть преобразована в число, выводится сообщение об ошибке.
     * @param line строка, которую нужно добавить в одну из коллекций
     */
    public void add(String line) {
        try {
            if (line.matches(Regex.INT_REGEX)) {
                integers.add(Long.valueOf(line));
            } else if (line.matches(Regex.FLOAT_REGEX)) {
                floats.add(Double.valueOf(line));
            } else {
                strings.add(line);
            }
        }catch (NumberFormatException e) {
            System.err.println(line + " is not a valid number. Skipping.");
        }
    }
}
