package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.u */
/* JADX INFO: compiled from: NullFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/u.class */
public class C0259u extends AbstractC0280w {
    public C0259u(String name, C0205h expression) {
        this(name, expression, EnumC0225q.IS_NULL);
    }

    public C0259u(String name, C0205h expression, EnumC0225q operator) {
        super(name, expression, operator);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.IS_NULL, EnumC0225q.IS_NOT_NULL};
    }

    @Override // org.romstation.application.AbstractC0280w, org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        super.mo843a(context);
        Expression expression = m1659i().m827a(context);
        switch (m1662k()) {
            case IS_NULL:
                return context.m640b().isNull(expression);
            case IS_NOT_NULL:
                return context.m640b().isNotNull(expression);
            default:
                return null;
        }
    }
}
