package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.n */
/* JADX INFO: compiled from: EmptyFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/n.class */
public class C0212n extends AbstractC0280w {
    public C0212n(String name, C0205h expression) {
        this(name, expression, EnumC0225q.IS_EMPTY);
    }

    public C0212n(String name, C0205h expression, EnumC0225q operator) {
        super(name, expression, operator);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.IS_EMPTY, EnumC0225q.IS_NOT_EMPTY};
    }

    @Override // org.romstation.application.AbstractC0280w, org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        super.mo843a(context);
        Expression expression = m1659i().m827a(context);
        switch (m1662k()) {
            case IS_EMPTY:
                return context.m640b().isEmpty(expression);
            case IS_NOT_EMPTY:
                return context.m640b().isNotEmpty(expression);
            default:
                return null;
        }
    }
}
