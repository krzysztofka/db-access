package com.kali.dbAccess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGeneratorRunner implements CommandLineRunner {

    public void run(String... strings) throws Exception {
        System.out.println("data generator run run");
    }
}
