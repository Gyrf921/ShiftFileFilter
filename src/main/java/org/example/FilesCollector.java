package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constrain.Regex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FilesCollector {

    private List<String> integers = new ArrayList<>();

    private List<String> floats = new ArrayList<>();

    private List<String> strings = new ArrayList<>();

    public FilesCollector(String fileIntegers, String fileFloats, String fileStrings) {
        try{
            integers.addAll(new BufferedReader(new FileReader(fileIntegers)).lines().toList());
            floats.addAll(new BufferedReader(new FileReader(fileFloats)).lines().toList());
            strings.addAll(new BufferedReader(new FileReader(fileStrings)).lines().toList());

        }catch (FileNotFoundException e){
            System.out.println("Files not found, the program will create new files.");
            new FilesCollector();
        }
    }
    public void addToCollector(String line){
        if (line != null){
            if (line.matches(Regex.INT_REGEX)) {
                integers.add(line);
            } else if (line.matches(Regex.FLOAT_REGEX)) {
                floats.add(line);
            } else {
                strings.add(line);
            }
        }
    }
}
