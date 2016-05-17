package com.kali.dbaccess;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DatabaseManagerRunner implements CommandLineRunner {

    private static final String DMS_ARG = "DMS";

    @Override
    public void run(String... args) throws Exception {
        Optional<String> argExist = Arrays.stream(args).filter(DMS_ARG::equals).findAny();
        argExist.ifPresent(x -> DatabaseManagerSwing.main(
                new String[]{"--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""}));
    }
}
