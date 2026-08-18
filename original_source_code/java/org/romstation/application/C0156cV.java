package org.romstation.application;

import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.romstation.application.network.C0216a;
import org.romstation.application.network.C0217b;
import org.romstation.application.network.C0219d;
import org.romstation.application.network.C0221f;
import org.romstation.application.network.C0222g;
import org.romstation.application.network.InvalidServerResponseException;
import org.romstation.application.network.NetworkOfflineException;
import org.romstation.application.network.ServerResponseException;
import org.romstation.application.task.C0249q;
import org.romstation.application.task.C0250r;
import org.romstation.application.task.C0251s;
import org.romstation.application.task.C0252t;
import org.romstation.application.task.C0256x;
import org.romstation.application.view.control.ApplicationAlert;
import org.romstation.application.view.control.ServerErrorAlert;

/* JADX INFO: renamed from: org.romstation.application.cV */
/* JADX INFO: compiled from: GameUploadManager.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/cV.class */
public class C0156cV {

    /* JADX INFO: renamed from: a */
    private static ObservableList<Dialog<?>> f349a = FXCollections.observableList(new LinkedList());

    /* JADX INFO: renamed from: a */
    public static synchronized ObservableList<Dialog<?>> m671a() {
        f349a.removeIf(dialog -> {
            return !dialog.isShowing();
        });
        return f349a;
    }

    /* JADX WARN: Type inference failed for: r0v63, types: [java.lang.Runnable, org.romstation.application.task.t] */
    /* JADX INFO: renamed from: b */
    public static C0096bO m672b() {
        C0251s context;
        if (m675c()) {
            m677d();
            return null;
        }
        if (!C0058ae.m195a().m200e()) {
            m674e();
            return null;
        }
        C0097bP gameUploadFileChooserDialog = new C0097bP();
        Optional<Path> optionalPath = gameUploadFileChooserDialog.showAndWait();
        if (optionalPath.isPresent()) {
            Path path = optionalPath.get();
            Task c0256x = new C0256x(path, "MD5");
            Thread thread = new Thread((Runnable) c0256x);
            C0076av<String> taskDialog = new C0076av<>(c0256x);
            thread.start();
            Optional<String> optionalChecksum = taskDialog.showAndWait();
            if (optionalChecksum.isPresent()) {
                String checksum = optionalChecksum.get();
                try {
                    C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/init.php");
                    builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                    C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("checksum", checksum);
                    C0216a request = new C0216a(builder.m973b());
                    C0219d serverResponse = request.m959a(post);
                    C0098bQ gameUploadFormDialog = new C0098bQ(serverResponse.m967b().getAsJsonObject("upload"), path);
                    Optional<JsonObject> optionalForm = gameUploadFormDialog.showAndWait();
                    if (optionalForm.isPresent()) {
                        JsonObject form = optionalForm.get();
                        if (serverResponse.m967b().getAsJsonObject("upload").get("resume") == null) {
                            context = new C0251s(checksum, form);
                        } else {
                            int id = serverResponse.m967b().getAsJsonObject("upload").getAsJsonObject("resume").get("upload_id").getAsInt();
                            context = new C0251s(id, checksum, form);
                        }
                        ?? c0252t = new C0252t(context);
                        Thread gameUploadTaskThread = new Thread((Runnable) c0252t);
                        C0096bO gameUploadDialog = new C0096bO(c0252t);
                        gameUploadDialog.initModality(Modality.NONE);
                        gameUploadTaskThread.start();
                        gameUploadDialog.show();
                        f349a.add(gameUploadDialog);
                        return gameUploadDialog;
                    }
                    return null;
                } catch (MalformedURLException | InvalidServerResponseException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                    return null;
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                    return null;
                } catch (ServerResponseException exception3) {
                    RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                    ServerErrorAlert alert = new ServerErrorAlert(exception3);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v65, types: [java.lang.Runnable, org.romstation.application.task.r] */
    /* JADX INFO: renamed from: a */
    public static C0094bM m673a(int gameID, int systemID) {
        C0249q context;
        if (m675c()) {
            m677d();
            return null;
        }
        if (!C0058ae.m195a().m200e()) {
            m674e();
            return null;
        }
        C0097bP gameUploadFileChooserDialog = new C0097bP();
        Optional<Path> optionalPath = gameUploadFileChooserDialog.showAndWait();
        if (optionalPath.isPresent()) {
            Path path = optionalPath.get();
            Task c0256x = new C0256x(path, "MD5");
            Thread thread = new Thread((Runnable) c0256x);
            C0076av<String> taskDialog = new C0076av<>(c0256x);
            thread.start();
            Optional<String> optionalChecksum = taskDialog.showAndWait();
            if (optionalChecksum.isPresent()) {
                String checksum = optionalChecksum.get();
                try {
                    C0221f builder = new C0221f(C0217b.m961b() + "/romstation/scripts/game/share/init.php");
                    builder.m972a().m974a("v", Integer.valueOf(RomStation.f15a));
                    C0222g post = new C0222g().m974a("auth", C0060ag.m228a().m236f()).m974a("game_id", Integer.valueOf(gameID)).m974a("console_id", Integer.valueOf(systemID)).m974a("checksum", checksum);
                    C0216a request = new C0216a(builder.m973b());
                    C0219d serverResponse = request.m959a(post);
                    C0095bN gameFileUploadFormDialog = new C0095bN(systemID, serverResponse.m967b().getAsJsonObject("upload"), path);
                    Optional<JsonObject> optionalForm = gameFileUploadFormDialog.showAndWait();
                    if (optionalForm.isPresent()) {
                        JsonObject form = optionalForm.get();
                        if (serverResponse.m967b().getAsJsonObject("upload").get("resume") == null) {
                            context = new C0249q(gameID, systemID, checksum, form);
                        } else {
                            int id = serverResponse.m967b().getAsJsonObject("upload").getAsJsonObject("resume").get("upload_id").getAsInt();
                            context = new C0249q(id, gameID, systemID, checksum, form);
                        }
                        ?? c0250r = new C0250r(context);
                        Thread gameFileUploadTaskThread = new Thread((Runnable) c0250r);
                        C0094bM gameFileUploadDialog = new C0094bM(c0250r);
                        gameFileUploadDialog.initModality(Modality.NONE);
                        gameFileUploadTaskThread.start();
                        gameFileUploadDialog.show();
                        f349a.add(gameFileUploadDialog);
                        return gameFileUploadDialog;
                    }
                    return null;
                } catch (MalformedURLException | InvalidServerResponseException exception) {
                    RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                    return null;
                } catch (NetworkOfflineException exception2) {
                    RomStation.m42b().log(Level.WARNING, exception2.getMessage(), (Throwable) exception2);
                    return null;
                } catch (ServerResponseException exception3) {
                    RomStation.m42b().log(Level.SEVERE, exception3.getMessage(), (Throwable) exception3);
                    ServerErrorAlert alert = new ServerErrorAlert(exception3);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: renamed from: e */
    private static void m674e() {
        ResourceBundle resources = RomStation.m44d();
        new ApplicationAlert(resources.getString("connectionRequiredAlert.header"), resources.getString("connectionRequiredAlert.content"), Alert.AlertType.INFORMATION).showAndWait();
    }

    /* JADX INFO: renamed from: c */
    public static boolean m675c() {
        return !m671a().isEmpty();
    }

    /* JADX INFO: renamed from: a */
    private static void m676a(Dialog<?> dialog) {
        Stage stage = dialog.getDialogPane().getScene().getWindow();
        stage.setIconified(false);
        stage.toFront();
    }

    /* JADX INFO: renamed from: d */
    public static void m677d() {
        m671a().forEach(C0156cV::m676a);
    }
}
