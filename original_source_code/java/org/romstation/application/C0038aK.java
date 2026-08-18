package org.romstation.application;

import java.util.List;
import java.util.stream.Collectors;
import org.romstation.application.database.entity.Tag;
import org.romstation.application.view.control.cell.entity.TagListCell;

/* JADX INFO: renamed from: org.romstation.application.aK */
/* JADX INFO: compiled from: TagChoiceDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aK.class */
public class C0038aK extends AbstractC0033aF<Tag> {
    public C0038aK(List<Tag> items) {
        super(items);
        setHeaderText(getResources().getString("tagChoiceDialog.header"));
        getDialogPane().getStyleClass().add("tag");
        m123b().setPromptText(getResources().getString("tagChoiceDialog.search.promptText"));
        m124c().setCellFactory(new TagListCell.Factory());
    }

    @Override // org.romstation.application.AbstractC0033aF
    /* JADX INFO: renamed from: a */
    List<Tag> mo118a(String string) {
        if (string == null) {
            return m122a();
        }
        return (List) m122a().stream().filter(item -> {
            return item.getName().getDefaultString().toUpperCase().contains(string.toUpperCase());
        }).collect(Collectors.toList());
    }
}
