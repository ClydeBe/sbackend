package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Product;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class ProductAuditingEvaluator extends AuditingRuleEvaluator<Product> {

    @Override
    public boolean canAudit(Product entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getVendor().getRole());
    }

}
