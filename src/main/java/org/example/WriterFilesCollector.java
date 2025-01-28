package org.example;

import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Data
public class WriterFilesCollector {

    BufferedWriter integerWriter;
    BufferedWriter floatWriter;
    BufferedWriter stringWriter;

    public WriterFilesCollector(String fileIntegers, String fileFloats, String fileStrings) {
        try {
            integerWriter = new BufferedWriter(new FileWriter(fileIntegers));
            floatWriter = new BufferedWriter(new FileWriter(fileFloats));
            stringWriter = new BufferedWriter(new FileWriter(fileStrings));

        }catch (IOException e) {
            System.out.println("предложенные вами имена недоступны, используются базовые имена");
            new WriterFilesCollector();
        }
    }

    public WriterFilesCollector(){
        try {
            integerWriter = new BufferedWriter(new FileWriter("integers.txt"));
            floatWriter = new BufferedWriter(new FileWriter("floats.txt"));
            stringWriter = new BufferedWriter(new FileWriter("strings.txt"));
        }catch (IOException e) {
            System.out.println("Базовые имена недоступны, нет возможности создать результирующий файл");
        }
    }

    public void close() {
        try {
            integerWriter.close();
            floatWriter.close();
            stringWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
