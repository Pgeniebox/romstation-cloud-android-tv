package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.Series;
import org.romstation.application.view.control.cell.entity.SeriesListCell;

/* JADX INFO: renamed from: org.romstation.application.aI */
/* JADX INFO: compiled from: SeriesChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aI.class */
public class C0036aI extends AbstractC0033aF<Series> {
    public C0036aI(List<Series> items) {
        super(items);
        setHeaderText(getResources().getString("seriesChoiceDialog.header"));
        getDialogPane().getStyleClass().add("series");
        m123b().setPromptText(getResources().getString("seriesChoiceDialog.search.promptText"));
        m124c().setCellFactory(new SeriesListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<Series> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
