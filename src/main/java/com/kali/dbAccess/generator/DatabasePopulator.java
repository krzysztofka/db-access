package com.kali.dbaccess.generator;

public interface DatabasePopulator {

    void populate(InMemoryGenerationContext context, int quantity);
}
