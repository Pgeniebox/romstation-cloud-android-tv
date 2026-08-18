package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.System;
import org.romstation.application.view.control.cell.entity.SystemListCell;

/* JADX INFO: renamed from: org.romstation.application.aJ */
/* JADX INFO: compiled from: SystemChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aJ.class */
public class C0037aJ extends AbstractC0033aF<System> {
    public C0037aJ(List<System> items) {
        super(items);
        setHeaderText(getResources().getString("systemChoiceDialog.header"));
        getDialogPane().getStyleClass().add("system");
        m123b().setPromptText(getResources().getString("systemChoiceDialog.search.promptText"));
        m124c().setCellFactory(new SystemListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<System> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
