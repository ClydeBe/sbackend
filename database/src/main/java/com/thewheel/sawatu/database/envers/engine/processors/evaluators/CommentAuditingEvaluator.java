package com.thewheel.sawatu.database.envers.engine.processors.evaluators;

import com.thewheel.sawatu.database.envers.engine.processors.AuditingRuleEvaluator;
import com.thewheel.sawatu.database.model.Comment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.thewheel.sawatu.Role.ADMIN;
import static com.thewheel.sawatu.Role.STAFF;

@Component
public class CommentAuditingEvaluator extends AuditingRuleEvaluator<Comment> {

    @Override
    public boolean canAudit(Comment entity) {
        return Arrays.asList(ADMIN, STAFF).contains(entity.getAuthor().getRole());
    }

}
