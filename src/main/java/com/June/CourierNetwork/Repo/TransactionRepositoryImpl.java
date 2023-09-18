package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.TransactionDetailsMapper;
import com.June.CourierNetwork.Model.TransactionDetails;
import com.June.CourierNetwork.Model.TransactionDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String sqlJoinClause = "SELECT " +
            "u.first_name AS customerFirstName, " +
            "u.last_name AS customerLastName, " +
            "u.id, " +
            "cu.customer_number, " +
            "td.was_delivered, " +
            "td.total_spent, " +
            "td.created_on, " +
            "td.payment_type, " +
            "GROUP_CONCAT(cpd.description SEPARATOR '~') AS descriptions, " +
            "GROUP_CONCAT(DISTINCT cpd.tracking_number SEPARATOR '~') AS trackingNumbers " +
            "FROM transaction_details AS td " +
            "INNER JOIN user AS u ON td.customer_id = u.id " +
            "INNER JOIN customer_user cu ON u.id = cu.user_id " +
            "INNER JOIN transaction_products AS tp ON td.id = tp.transaction_id " +
            "LEFT JOIN customer_product_details AS cpd ON tp.product_id = cpd.id";


    @Override
    public Long save(TransactionDetailsRequest transactionDetailsRequest) {
        val sql = "INSERT INTO JuneCourierNetwork.transaction_details " +
                "(was_delivered, created_on, total_spent, payment_type, customer_id, courier_id) " +
                "VALUES(:wasDelivered, :createdOn, :totalSpent, :paymentType, :customerId, :courierId);";

        val params = new MapSqlParameterSource();
        params.addValue("wasDelivered", transactionDetailsRequest.getWasDelivered());
        params.addValue("createdOn", transactionDetailsRequest.getCreatedOn());
        params.addValue("totalSpent", transactionDetailsRequest.getTotalSpent());
        params.addValue("paymentType", transactionDetailsRequest.getPaymentType().name());
        params.addValue("customerId", transactionDetailsRequest.getCustomerId());
        params.addValue("courierId", transactionDetailsRequest.getCourierId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public void matchProductsToTransaction(Long transactionId, List<Long> productIds) {
        val sql = "INSERT INTO JuneCourierNetwork.transaction_products " +
                "(transaction_id, product_id) " +
                "VALUES (:transactionId, :productId)";

        for (Long productId : productIds) {
            val params = new MapSqlParameterSource();
            params.addValue("transactionId", transactionId);
            params.addValue("productId", productId);

            jdbcTemplate.update(sql, params);
        }
    }

    @Override
    public TransactionDetails getTransactionDetailsByCustomerNumber(String customerNumber) {
        val sql = sqlJoinClause + " WHERE cu.customer_number = :customerNumber " +
                "GROUP BY td.id ORDER BY td.created_on DESC";

        val params = new MapSqlParameterSource();

        params.addValue("customerNumber", customerNumber);

        return jdbcTemplate.queryForObject(sql, params, new TransactionDetailsMapper());
    }

    @Override
    public List<TransactionDetails> getAllTransactionDetails() {
        val sql = sqlJoinClause + " GROUP BY td.id ORDER BY td.created_on DESC";

        return jdbcTemplate.query(sql, new TransactionDetailsMapper());
    }
}
