package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.generator.GenerationContext;

public interface EntityProvider<E extends Entity> {

    E getEntity(GenerationContext context);
}
