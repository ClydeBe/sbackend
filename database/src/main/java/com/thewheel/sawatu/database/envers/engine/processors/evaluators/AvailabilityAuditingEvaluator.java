package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Availability;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class AvailabilityAuditingEvaluator extends AuditingRuleEvaluator<Availability> {

    @Override
    public boolean canAudit(Availability entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getUser().getRole());
    }

}
