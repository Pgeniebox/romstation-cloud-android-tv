package org.romstation.application;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/* JADX INFO: renamed from: org.romstation.application.x */
/* JADX INFO: compiled from: StringFilter.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/x.class */
public class C0281x extends AbstractC0211m<String> {
    public C0281x(String name, C0205h expression, EnumC0225q operator, C0204g<String> data) {
        super(name, expression, operator, data);
    }

    /* JADX INFO: renamed from: m */
    private String m1667m() {
        return m845c().m820b().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_").toUpperCase();
    }

    @Override // org.romstation.application.AbstractC0280w
    /* JADX INFO: renamed from: a */
    public EnumC0225q[] mo842a() {
        return new EnumC0225q[]{EnumC0225q.LIKE, EnumC0225q.NOT_LIKE, EnumC0225q.MATCH, EnumC0225q.NOT_MATCH, EnumC0225q.START_WITH, EnumC0225q.END_WITH};
    }

    @Override // org.romstation.application.AbstractC0280w, org.romstation.application.AbstractC0224p
    /* JADX INFO: renamed from: a */
    public Predicate mo843a(AbstractC0134c context) {
        if (m845c().m820b() == null || m845c().m820b().isEmpty()) {
            return null;
        }
        super.mo843a(context);
        Expression expression = m1659i().m827a(context);
        switch (m1662k()) {
            case LIKE:
                return context.m640b().like(expression, m1667m(), '\\');
            case NOT_LIKE:
                return context.m640b().notLike(expression, m1667m(), '\\');
            case MATCH:
                return context.m640b().like(expression, "%" + m1667m() + "%", '\\');
            case NOT_MATCH:
                return context.m640b().notLike(expression, "%" + m1667m() + "%", '\\');
            case START_WITH:
                return context.m640b().like(expression, m1667m() + "%", '\\');
            case END_WITH:
                return context.m640b().like(expression, "%" + m1667m(), '\\');
            default:
                return null;
        }
    }
}
