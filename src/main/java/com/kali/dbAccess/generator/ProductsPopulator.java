package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Product;
import com.kali.dbAccess.generator.providers.EntityProvider;
import com.kali.dbAccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbAccess.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "productsPopulator")
public class ProductsPopulator extends SimpleDataPopulatorTemplate<Product> implements DatabasePopulator {

    @Autowired
    private ProductRepository repository;

    @Autowired
    @Qualifier("randomProductProvider")
    private EntityProvider<Product> entityProvider;

    public void persist(Product product, DataGenerationContext context) {
        context.addProduct(repository.save(product));
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
}
