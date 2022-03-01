package cash.db.dao;

import cash.entity.Product;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {

    List<Product> findAllWithRestrict(int offset, int noOfRecords, int id_lang);

    List<Product> viewAllWithSorting(int offset, int recordsOnPage, String sortingType, int id_lang);

    int getTotalAmountRecords();

    List<Product> findAllByLang(int id_lang);

    boolean deleteProductByCode(String code);

    boolean updateAmount(Product product, Double amount);

    Product findProductByCode(String code);

    Product findProductByCodeLang(String code, int id_lang);

    Product findProductByNameLang(String name, int id_lang);

    int getId_lang(String lang);

}
