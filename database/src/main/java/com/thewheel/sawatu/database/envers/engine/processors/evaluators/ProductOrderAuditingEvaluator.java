package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.ProductOrder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class ProductOrderAuditingEvaluator extends AuditingRuleEvaluator<ProductOrder> {

    @Override
    public boolean canAudit(ProductOrder entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getUser().getRole());
    }

}
