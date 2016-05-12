package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.generator.InMemoryGenerationContext;

public interface EntityProvider<E extends Entity> {

    E getEntity(InMemoryGenerationContext context);
}
