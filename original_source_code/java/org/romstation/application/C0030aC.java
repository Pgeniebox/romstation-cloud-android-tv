package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.Genre;
import org.romstation.application.view.control.cell.entity.GenreListCell;

/* JADX INFO: renamed from: org.romstation.application.aC */
/* JADX INFO: compiled from: GenreChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aC.class */
public class C0030aC extends AbstractC0033aF<Genre> {
    public C0030aC(List<Genre> items) {
        super(items);
        setHeaderText(getResources().getString("genreChoiceDialog.header"));
        getDialogPane().getStyleClass().add("genre");
        m123b().setPromptText(getResources().getString("genreChoiceDialog.search.promptText"));
        m124c().setCellFactory(new GenreListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<Genre> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().getDefaultString().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
