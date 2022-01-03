package com.thewheel.sawatu.database.envers.engine.processors;

public abstract class AuditingRuleEvaluator<T> {

    public abstract boolean canAudit(T entity);

}
