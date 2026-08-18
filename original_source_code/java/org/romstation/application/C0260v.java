package org.romstation.application;

import java.lang.Number;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.v */
/* JADX INFO: compiled from: NumberFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/v.class */
public class C0260v<T extends Number> extends AbstractC0211m<T> {
    public C0260v(String name, C0205h expression, EnumC0225q operator, C0204g<T> data) {
        super(name, expression, operator, data);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.EQUAL, EnumC0225q.NOT_EQUAL, EnumC0225q.GREATER, EnumC0225q.GREATER_OR_EQUAL, EnumC0225q.LESSER, EnumC0225q.LESSER_OR_EQUAL};
    }

    @Override // org.romstation.application.AbstractC0280w, org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        if (m845c().m820b() == null) {
            return null;
        }
        super.mo843a(context);
        Expression expression = m1659i().m827a(context);
        switch (m1662k()) {
            case EQUAL:
                return context.m640b().equal(expression, m845c().m820b());
            case NOT_EQUAL:
                return context.m640b().notEqual(expression, m845c().m820b());
            case GREATER:
                return context.m640b().gt(expression, m845c().m820b());
            case GREATER_OR_EQUAL:
                return context.m640b().ge(expression, m845c().m820b());
            case LESSER:
                return context.m640b().lt(expression, m845c().m820b());
            case LESSER_OR_EQUAL:
                return context.m640b().le(expression, m845c().m820b());
            default:
                return null;
        }
    }
}
