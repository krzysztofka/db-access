package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.BaseEntity;
import com.kali.dbaccess.generator.GenerationContext;

public interface EntityProvider<E extends BaseEntity> {

    E getEntity(GenerationContext context);
}
