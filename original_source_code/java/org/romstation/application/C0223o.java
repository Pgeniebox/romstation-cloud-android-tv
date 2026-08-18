package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.romstation.application.database.entity.AbstractC0189a;

/* JADX INFO: renamed from: org.romstation.application.o */
/* JADX INFO: compiled from: EntityFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/o.class */
public class C0223o<T extends AbstractC0189a> extends AbstractC0211m<T> {
    public C0223o(String name, C0205h expression, EnumC0225q operator, C0204g<T> data) {
        super(name, expression, operator, data);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.EQUAL, EnumC0225q.NOT_EQUAL};
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
            default:
                return null;
        }
    }
}
