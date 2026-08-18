package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.Language;
import org.romstation.application.view.control.cell.entity.LanguageListCell;

/* JADX INFO: renamed from: org.romstation.application.aD */
/* JADX INFO: compiled from: LanguageChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aD.class */
public class C0031aD extends AbstractC0033aF<Language> {
    public C0031aD(List<Language> items) {
        super(items);
        setHeaderText(getResources().getString("languageChoiceDialog.header"));
        getDialogPane().getStyleClass().add("language");
        m123b().setPromptText(getResources().getString("languageChoiceDialog.search.promptText"));
        m124c().setCellFactory(new LanguageListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<Language> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().getDefaultString().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
