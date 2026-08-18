package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.l */
/* JADX INFO: compiled from: BooleanFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/l.class */
public class C0210l extends AbstractC0280w {
    public C0210l(String name, C0205h expression) {
        this(name, expression, EnumC0225q.IS_TRUE);
    }

    public C0210l(String name, C0205h expression, EnumC0225q operator) {
        super(name, expression, operator);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.IS_TRUE, EnumC0225q.IS_FALSE};
    }

    @Override // org.romstation.application.AbstractC0280w, org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        super.mo843a(context);
        Expression expression = m1659i().m827a(context);
        switch (m1662k()) {
            case IS_TRUE:
                return context.m640b().isTrue(expression);
            case IS_FALSE:
                return context.m640b().isFalse(expression);
            default:
                return null;
        }
    }
}
