package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepository {

    private final JdbcTemplate template;

    public List<Product> findByQuery(String query, int page, int size) {
        return template.query(
                "SELECT id, characteristics, description, image, label, price, " + "usage, vendor_id FROM " +
                "t_product where text_search @@ " + "plainto_tsquery(?) " +
                "ORDER BY ts_rank(text_search, plainto_tsquery(?)) ASC " +
                "LIMIT ? OFFSET ? ",
                (rs, rownum) -> Product.builder()
                        .id(rs.getLong("id"))
                        .price(rs.getFloat("price"))
                        .label(rs.getString("label"))
                        .image(rs.getString("image"))
                        .usage(rs.getString("usage"))
                        .description(rs.getString("description"))
                        .characteristics(rs.getString("characteristics"))
                        .vendorName(rs.getString("vendor_id"))
                        .build(), query, query, size, page * size);
    }

    public List<Product> findByQuery(int min, int max, int page, int size) {
        return template.query(
                "SELECT id, characteristics, description, image, label, price, " + "usage, vendor_id FROM " +
                "product where price between ? and ?" +
                "LIMIT ? OFFSET ?",
                (rs, rownum) -> Product.builder()
                        .id(rs.getLong("id"))
                        .price(rs.getFloat("price"))
                        .label(rs.getString("label"))
                        .image(rs.getString("image"))
                        .usage(rs.getString("usage"))
                        .description(rs.getString("description"))
                        .characteristics(rs.getString("characteristics"))
                        .vendorName(rs.getString("vendor_id"))
                        .build(), min, max, size, page * size);
    }

    public List<Product> findByQuery(String query, int min, int max, int page, int size) {
        return template.query(
                "SELECT id, characteristics, description, image, label, price, " + "usage, vendor_id FROM " +
                "product where text_search @@ " + "plainto_tsquery(?) and price between ? and ? " +
                "ORDER BY ts_rank(text_search, plainto_tsquery(?)) ASC  LIMIT ? OFFSET ?",
                (rs, rownum) -> Product.builder()
                        .id(rs.getLong("id"))
                        .price(rs.getFloat("price"))
                        .label(rs.getString("label"))
                        .image(rs.getString("image"))
                        .usage(rs.getString("usage"))
                        .description(rs.getString("description"))
                        .characteristics(rs.getString("characteristics"))
                        .vendorName(rs.getString("vendor_id"))
                        .build(), query, min, max, query, size, page * size);
    }
}