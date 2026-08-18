package org.romstation.application.view.controller.emulators;

import com.google.common.eventbus.Subscribe;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import org.romstation.application.C0024X;
import org.romstation.application.C0025Y;
import org.romstation.application.C0028aA;
import org.romstation.application.C0032aE;
import org.romstation.application.C0050aW;
import org.romstation.application.C0054aa;
import org.romstation.application.C0076av;
import org.romstation.application.C0081b;
import org.romstation.application.C0110bc;
import org.romstation.application.C0112be;
import org.romstation.application.C0152cR;
import org.romstation.application.C0157cW;
import org.romstation.application.C0165ce;
import org.romstation.application.C0167cg;
import org.romstation.application.C0168ch;
import org.romstation.application.C0169ci;
import org.romstation.application.C0171ck;
import org.romstation.application.C0172cl;
import org.romstation.application.C0173cm;
import org.romstation.application.C0174cn;
import org.romstation.application.C0175co;
import org.romstation.application.C0176cp;
import org.romstation.application.C0177cq;
import org.romstation.application.RomStation;
import org.romstation.application.database.entity.Emulator;
import org.romstation.application.database.entity.EmulatorProfile;
import org.romstation.application.database.entity.Link;
import org.romstation.application.network.C0217b;
import org.romstation.application.task.C0238f;
import org.romstation.application.task.C0258z;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.SearchField;
import org.romstation.application.view.controller.ApplicationView;
import org.romstation.application.view.controller.RomStationController;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/view/controller/emulators/EmulatorsController.class */
public class EmulatorsController {

    /* JADX INFO: renamed from: a */
    private Timeline f815a;

    @FXML
    private BorderPane root;

    @FXML
    private Label searchResultLabel;

    @FXML
    private SearchField nameSearchField;

    @FXML
    private EmulatorsFiltersController filtersController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize() {
        RomStationController.f786a.register(this);
        this.nameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.f815a.playFromStart();
        });
        this.f815a = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(250.0d), event -> {
            this.filtersController.m1495a(this.nameSearchField.getText());
        }, new KeyValue[0])});
    }

    /* JADX INFO: renamed from: a */
    private void m1472a(Emulator emulator) {
        List<EmulatorProfile> emulatorProfiles = (List) emulator.getFiles().stream().flatMap(emulatorFile -> {
            return emulatorFile.getProfiles().stream();
        }).filter(profile -> {
            return profile.getSystems().isEmpty();
        }).sorted(Comparator.comparing((v0) -> {
            return v0.getName();
        }, Comparator.nullsFirst((v0, v1) -> {
            return v0.compareToIgnoreCase(v1);
        }))).collect(Collectors.toList());
        switch (emulatorProfiles.size()) {
            case 0:
                break;
            case 1:
                C0157cW.m681a(new C0258z(emulatorProfiles.get(0), new String[0]));
                break;
            default:
                C0028aA dialog = new C0028aA(emulatorProfiles);
                dialog.showAndWait().ifPresent(emulatorProfile -> {
                    C0157cW.m681a(new C0258z(emulatorProfile, new String[0]));
                });
                break;
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1473a(C0167cg event) {
        this.root.setVisible(event.m730a() == ApplicationView.EMULATORS);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1474a(C0165ce event) {
        Path root = Paths.get("", new String[0]).toAbsolutePath().getParent();
        if (Files.exists(root.resolve("database.sqlite"), new LinkOption[0]) && Files.notExists(root.resolve("imported"), new LinkOption[0]) && Files.notExists(root.resolve("emulators_imported"), new LinkOption[0])) {
            try {
                try {
                    C0054aa emulatorScannerTask = new C0054aa(root);
                    emulatorScannerTask.run();
                    List<C0024X> emulators = (List) emulatorScannerTask.get();
                    if (!emulators.isEmpty()) {
                        Task c0025y = new C0025Y(emulators);
                        Thread thread = new Thread((Runnable) c0025y);
                        C0076av<List<Emulator>> taskDialog = new C0076av<>(c0025y);
                        thread.start();
                        taskDialog.showAndWait();
                    }
                    try {
                        Files.createFile(root.resolve("emulators_imported"), new FileAttribute[0]);
                    } catch (IOException exception) {
                        RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                    }
                } catch (Throwable th) {
                    try {
                        Files.createFile(root.resolve("emulators_imported"), new FileAttribute[0]);
                    } catch (IOException exception2) {
                        RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                    }
                    throw th;
                }
            } catch (InterruptedException | ExecutionException exception3) {
                RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                try {
                    Files.createFile(root.resolve("emulators_imported"), new FileAttribute[0]);
                } catch (IOException exception4) {
                    RomStation.m42b().log(Level.WARNING, exception4.getMessage(), (Throwable) exception4);
                }
            }
        }
        this.filtersController.m1497b();
        this.filtersController.m1494a();
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1475a(C0177cq event) {
        EntityManager entityManager = C0081b.m309c();
        long count = ((Long) entityManager.createNamedQuery(Emulator.f412a, Long.class).getSingleResult()).longValue();
        entityManager.close();
        String message = MessageFormat.format(this.resources.getString("emulators.search.result"), Integer.valueOf(event.m737a().size()), Long.valueOf(count));
        this.searchResultLabel.setText(message);
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1476a(C0173cm event) {
        m1472a(event.m734a());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1477a(C0171ck event) {
        Alert alert = new ApplicationAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(this.resources.getString("emulator.delete.alert.header"));
        alert.setContentText(this.resources.getString("emulator.delete.alert.content"));
        Optional<ButtonType> alertResult = alert.showAndWait();
        if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
            Task c0238f = new C0238f(event.m732a());
            Thread thread = new Thread((Runnable) c0238f);
            C0076av<List<Emulator>> dialog = new C0076av<>(c0238f, "delete");
            thread.start();
            Optional<List<Emulator>> dialogResult = dialog.showAndWait();
            dialogResult.ifPresent(items -> {
                if (!items.isEmpty()) {
                    RomStationController.f786a.post(new C0169ci());
                }
            });
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1478a(C0172cl event) {
        C0050aW dialog = new C0050aW(event.m733a());
        Optional<Emulator> result = dialog.showAndWait();
        if (result.isPresent()) {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.merge(result.get());
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0169ci());
            return;
        }
        RomStationController.f786a.post(new C0176cp());
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1479a(C0174cn event) {
        Emulator emulator = event.m735a();
        if (emulator.getDirectory() != null) {
            try {
                Desktop.getDesktop().open(new File(emulator.getDirectory()));
            } catch (Exception exception) {
                RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
            }
        }
    }

    @Subscribe
    /* JADX INFO: renamed from: a */
    private void m1480a(C0175co event) {
        Emulator emulator = event.m736a();
        C0032aE dialog = new C0032aE(emulator.getLinks());
        Optional<Link> optional = dialog.showAndWait();
        optional.ifPresent(link -> {
            if (link.isExternal()) {
                try {
                    Desktop.getDesktop().browse(new URI(link.getLocation()));
                    return;
                } catch (Exception exception) {
                    RomStation.m42b().log(Level.WARNING, exception.getMessage(), (Throwable) exception);
                    return;
                }
            }
            RomStationController.f786a.post(new C0152cR(link.getLocation(), true));
            RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
        });
    }

    @FXML
    private void create() {
        C0050aW dialog = new C0050aW(new Emulator());
        Optional<Emulator> optional = dialog.showAndWait();
        optional.ifPresent(entity -> {
            EntityManager entityManager = C0081b.m309c();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
            RomStationController.f786a.post(new C0169ci());
        });
    }

    @FXML
    private void download() {
        RomStationController.f786a.post(new C0152cR(C0217b.m961b() + "/emulators", true));
        RomStationController.f786a.post(new C0168ch(ApplicationView.BROWSER));
    }

    @FXML
    private void importLegacy() {
        C0112be romstationDirectoryChooserDialog = new C0112be();
        romstationDirectoryChooserDialog.showAndWait().ifPresent(path -> {
            try {
                C0054aa emulatorScannerTask = new C0054aa(path);
                emulatorScannerTask.run();
                List<C0024X> emulators = (List) emulatorScannerTask.get();
                C0110bc emulatorImporterDialog = new C0110bc(emulators);
                emulatorImporterDialog.showAndWait().ifPresent(items -> {
                    Task c0025y = new C0025Y(items);
                    Thread thread = new Thread((Runnable) c0025y);
                    C0076av<List<Emulator>> taskDialog = new C0076av<>(c0025y);
                    thread.start();
                    Optional<List<Emulator>> dialogResult = taskDialog.showAndWait();
                    dialogResult.ifPresent(importedGames -> {
                        if (!importedGames.isEmpty()) {
                            RomStationController.f786a.post(new C0169ci());
                        }
                    });
                });
            } catch (Exception exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    public Node m1481a() {
        return this.root;
    }
}
