package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbaccess.repository.ProductRepository;

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

    protected void persist(Product product) {
        repository.save(product);
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
