package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Post;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class PostAuditingEvaluator extends AuditingRuleEvaluator<Post> {

    @Override
    public boolean canAudit(Post entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getAuthor().getRole());
    }

}
