package org.romstation.application.view.control.cell;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/DateTableCell.class */
public class DateTableCell<S> extends TableCell<S, Long> {

    /* JADX INFO: renamed from: a */
    private final StringProperty f739a;

    public DateTableCell(@NamedArg("pattern") String pattern) {
        this.f739a = new SimpleStringProperty(pattern);
        getStyleClass().add("date");
    }

    /* JADX INFO: renamed from: a */
    public StringProperty m1177a() {
        return this.f739a;
    }

    /* JADX INFO: renamed from: b */
    public String m1178b() {
        return (String) this.f739a.get();
    }

    /* JADX INFO: renamed from: a */
    public void m1179a(String pattern) {
        this.f739a.set(pattern);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Type inference failed for: r0v11, types: [java.time.LocalDateTime] */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void updateItem(Long item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else if (item.longValue() == 0) {
            setText(null);
        } else {
            setText(Instant.ofEpochSecond(item.longValue()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern((String) this.f739a.get())));
        }
    }

    /* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/control/cell/DateTableCell$Factory.class */
    public static class Factory<S> implements Callback<TableColumn<S, Long>, DateTableCell> {

        /* JADX INFO: renamed from: a */
        private final StringProperty f740a;

        public Factory(@NamedArg("pattern") String pattern) {
            this.f740a = new SimpleStringProperty(pattern);
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public DateTableCell call(TableColumn<S, Long> param) {
            return new DateTableCell((String) this.f740a.get());
        }
    }
}
