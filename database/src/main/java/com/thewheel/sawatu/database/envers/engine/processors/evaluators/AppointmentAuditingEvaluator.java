package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Appointment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class AppointmentAuditingEvaluator extends AuditingRuleEvaluator<Appointment> {

    @Override
    public boolean canAudit(Appointment entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getClient().getRole());
    }

}
