package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.domain.ProductSize;
import com.kali.dbaccess.repository.ProductRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository(("jdbcProductRepository"))
public class JdbcProductRepository extends SimpleJdbcRepository<Product> implements ProductRepository {

    private static final String TABLE_NAME = "PRODUCTS";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String PRICE = "price";

    private static final String DESCRIPTION = "description";

    private static final String SIZE = "size";

    private static final Set<String> ALL_COLUMNS = ImmutableSet.of(ID, NAME, PRICE, DESCRIPTION, SIZE);

    private RowMapper<Product> mapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getLong(ID));
        product.setPrice(rs.getInt(PRICE));
        product.setSize(ProductSize.valueOf(rs.getString(SIZE)));
        product.setDescription(rs.getString(DESCRIPTION));
        product.setName(rs.getString(NAME));
        return product;
    };

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Product product) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.<String, Object>builder()
                .put(NAME, product.getName())
                .put(PRICE, product.getPrice())
                .put(SIZE, product.getSize());
        if (product.getDescription() != null) {
            builder.put(DESCRIPTION, product.getDescription());
        }
        return builder.build();
    }

    @Override
    @Transactional
    public void update(Product product) {
        this.update(product, new String[] {DESCRIPTION},
                Lists.newArrayList(product.getDescription()));
    }

    @Override
    public List<Product> getBestsellerProducts(int limit) {
        String sqlColumns = toColumnStrings("p", ALL_COLUMNS);
        String sql = new StringBuilder("select ")
                .append(sqlColumns)
                .append(" from products p join order_items i on p.id = i.product_id group by ")
                .append(sqlColumns).append(" order by sum(i.quantity) desc limit ?")
                .toString();
        return jdbcTemplate.query(sql, new Object[]{limit}, mapper);
    }

    @Override
    protected RowMapper<Product> rowMapper() {
        return mapper;
    }
}
