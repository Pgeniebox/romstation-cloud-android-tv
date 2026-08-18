package org.romstation.application.view.controller.library.grid;

import com.google.common.eventbus.Subscribe;
import java.util.Comparator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.GridView;
import org.romstation.application.C0139cE;
import org.romstation.application.C0166cf;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Game;
import org.romstation.application.view.controller.RomStationController;
import org.romstation.application.view.controller.library.LibraryController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/library/grid/LibraryGridViewController.class */
public class LibraryGridViewController {

    @FXML
    private ComboBox<LibraryGridViewSortField> sortFieldComboBox;

    @FXML
    private ToggleGroup sortToggleGroup;

    @FXML
    private ToggleButton sortAscToggleButton;

    @FXML
    private ToggleButton sortDescToggleButton;

    @FXML
    private Slider zoomSlider;

    @FXML
    private GridView<Game> gridView;

    /* JADX INFO: renamed from: a */
    private final double f826a = 256.0d;

    /* JADX INFO: renamed from: b */
    private final double f827b = 256.0d;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.sortFieldComboBox.getItems().addAll(LibraryGridViewSortField.values());
        this.sortFieldComboBox.getSelectionModel().select(LibraryGridViewSortField.valueOf(RomStation.m43c().getProperty("library.gridView.sort.field")));
        this.sortFieldComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            m1584c();
        });
        switch (LibraryGridViewSortOrder.valueOf(RomStation.m43c().getProperty("library.gridView.sort.order"))) {
            case ASCENDING:
                this.sortToggleGroup.selectToggle(this.sortAscToggleButton);
                break;
            case DESCENDING:
                this.sortToggleGroup.selectToggle(this.sortDescToggleButton);
                break;
        }
        this.sortToggleGroup.selectedToggleProperty().addListener((observable2, oldValue2, newValue2) -> {
            if (newValue2 == null) {
                this.sortToggleGroup.selectToggle(oldValue2);
            } else {
                m1584c();
            }
        });
        this.zoomSlider.setValue(Double.parseDouble(RomStation.m43c().getProperty("library.gridView.zoom")));
        this.gridView.cellWidthProperty().bind(this.zoomSlider.valueProperty().multiply(256.0d));
        this.gridView.cellHeightProperty().bind(this.zoomSlider.valueProperty().multiply(256.0d));
        this.gridView.setCellFactory(new LibraryGridViewGameGridCell.Factory(LibraryController.m1509a()));
        m1584c();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1579a(C0139cE event) {
        List<Game> games = event.m646a();
        games.sort(m1582b());
        this.gridView.getItems().setAll(games);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1580a(C0166cf event) {
        RomStation.m43c().setProperty("library.gridView.sort.field", ((LibraryGridViewSortField) this.sortFieldComboBox.getValue()).name());
        RomStation.m43c().setProperty("library.gridView.sort.order", m1581a().name());
        RomStation.m43c().setProperty("library.gridView.zoom", String.valueOf(this.zoomSlider.getValue()));
    }

    /* JADX INFO: renamed from: a */
    private LibraryGridViewSortOrder m1581a() {
        return this.sortAscToggleButton.isSelected() ? LibraryGridViewSortOrder.ASCENDING : LibraryGridViewSortOrder.DESCENDING;
    }

    /* JADX INFO: renamed from: b */
    private Comparator<Game> m1582b() {
        return m1583a((LibraryGridViewSortField) this.sortFieldComboBox.getValue(), m1581a());
    }

    /* JADX INFO: renamed from: a */
    private Comparator<Game> m1583a(LibraryGridViewSortField field, LibraryGridViewSortOrder order) {
        Comparator<Game> comparator;
        switch (field) {
            case ID:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getId();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareTo(v1);
                }));
                break;
            case TITLE:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getTitle();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareToIgnoreCase(v1);
                }));
                break;
            case SYSTEM:
                comparator = Comparator.comparing(game -> {
                    if (game.getSystem() != null) {
                        return game.getSystem().getName();
                    }
                    return null;
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareToIgnoreCase(v1);
                }));
                break;
            case PLAYERS:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getPlayers();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareTo(v1);
                }));
                break;
            case YEAR:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getYear();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareTo(v1);
                }));
                break;
            case DEVELOPER:
                comparator = Comparator.comparing(game2 -> {
                    if (game2.getDeveloper() != null) {
                        return game2.getDeveloper().getName();
                    }
                    return null;
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareToIgnoreCase(v1);
                }));
                break;
            case PUBLISHER:
                comparator = Comparator.comparing(game3 -> {
                    if (game3.getPublisher() != null) {
                        return game3.getPublisher().getName();
                    }
                    return null;
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareToIgnoreCase(v1);
                }));
                break;
            case FILES:
                comparator = Comparator.comparingInt(o -> {
                    return o.getFiles().size();
                });
                break;
            case LAST_USE:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getLastUse();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareTo(v1);
                }));
                break;
            case PLAYED:
                comparator = Comparator.comparing((v0) -> {
                    return v0.getPlayed();
                }, Comparator.nullsFirst((v0, v1) -> {
                    return v0.compareTo(v1);
                }));
                break;
            default:
                return null;
        }
        return order == LibraryGridViewSortOrder.ASCENDING ? comparator : comparator.reversed();
    }

    /* JADX INFO: renamed from: c */
    private void m1584c() {
        this.gridView.getItems().sort(m1582b());
    }
}
