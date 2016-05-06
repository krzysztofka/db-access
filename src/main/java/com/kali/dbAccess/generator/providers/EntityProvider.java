package com.kali.dbAccess.generator.providers;

import com.kali.dbAccess.domain.Entity;
import com.kali.dbAccess.generator.DataGenerationContext;

public interface EntityProvider<E extends Entity> {

    E getEntity(DataGenerationContext context);
}
