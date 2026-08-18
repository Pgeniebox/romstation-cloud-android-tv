package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.Locale;
import org.romstation.application.view.control.cell.entity.LocaleListCell;

/* JADX INFO: renamed from: org.romstation.application.aG */
/* JADX INFO: compiled from: LocaleChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aG.class */
public class C0034aG extends AbstractC0033aF<Locale> {
    public C0034aG(List<Locale> items) {
        super(items);
        setHeaderText(getResources().getString("localeChoiceDialog.header"));
        getDialogPane().getStyleClass().add("locale");
        m123b().setPromptText(getResources().getString("localeChoiceDialog.search.promptText"));
        m124c().setCellFactory(new LocaleListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<Locale> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().getDefaultString().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
