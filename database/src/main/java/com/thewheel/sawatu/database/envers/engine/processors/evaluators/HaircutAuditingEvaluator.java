package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Haircut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class HaircutAuditingEvaluator extends AuditingRuleEvaluator<Haircut> {

    @Override
    public boolean canAudit(Haircut entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getVendor().getRole());
    }

}
