package cash.db.dao;

import cash.entity.Product;

public interface  ProductDao extends BaseDao<Product> {
    Product findProductByCode(String code);
    Product findProductByName(String code);

}
