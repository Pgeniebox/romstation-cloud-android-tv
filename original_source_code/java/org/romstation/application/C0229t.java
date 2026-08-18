package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.romstation.application.database.entity.AbstractC0189a;

/* JADX INFO: renamed from: org.romstation.application.t */
/* JADX INFO: compiled from: MemberFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/t.class */
public class C0229t<T extends AbstractC0189a> extends AbstractC0211m<T> {
    public C0229t(String name, C0205h expression, EnumC0225q operator, C0204g<T> data) {
        super(name, expression, operator, data);
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.IS_MEMBER, EnumC0225q.IS_NOT_MEMBER};
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
            case IS_MEMBER:
                return context.m640b().isMember(m845c().m820b(), expression);
            case IS_NOT_MEMBER:
                return context.m640b().isNotMember(m845c().m820b(), expression);
            default:
                return null;
        }
    }
}
