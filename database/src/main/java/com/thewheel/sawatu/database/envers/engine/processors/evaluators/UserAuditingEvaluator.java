package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class UserAuditingEvaluator extends AuditingRuleEvaluator<User> {

    @Override
    public boolean canAudit(User entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getRole());
    }

}
