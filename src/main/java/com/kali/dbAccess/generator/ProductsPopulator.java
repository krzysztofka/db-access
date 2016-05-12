package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbaccess.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "productsPopulator")
public class ProductsPopulator extends SimpleDataPopulatorTemplate<Product> implements DatabasePopulator {

    private static final Logger logger = LoggerFactory.getLogger(CustomersPopulator.class);

    @Autowired
    private ProductRepository repository;

    @Autowired
    @Qualifier("randomProductProvider")
    private EntityProvider<Product> entityProvider;

    public void persist(Product product, InMemoryGenerationContext context) {
        context.newProduct(repository.save(product));
    }

    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityProvider<Product> getEntityProvider() {
        return entityProvider;
    }

    public void setEntityProvider(EntityProvider<Product> entityProvider) {
        this.entityProvider = entityProvider;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
