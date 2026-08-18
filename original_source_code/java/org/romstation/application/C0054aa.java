package org.romstation.application;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.concurrent.Task;
import org.ini4j.Wini;

/* JADX INFO: renamed from: org.romstation.application.aa */
/* JADX INFO: compiled from: EmulatorScannerTask.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/aa.class */
public class C0054aa extends Task<List<C0024X>> {

    /* JADX INFO: renamed from: a */
    private final Path f85a;

    public C0054aa(Path root) {
        this.f85a = root;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public List<C0024X> call() throws Exception {
        List<C0026Z> profiles = m158b();
        List<C0024X> emulators = m160b(m159a(profiles));
        m161a(emulators, profiles);
        return emulators;
    }

    /* JADX INFO: renamed from: b */
    private List<C0026Z> m158b() throws IOException {
        return (List) Files.find(this.f85a.resolve("Emulation"), 3, (path, basicFileAttributes) -> {
            return path.endsWith("romstation_parameters.ini");
        }, new FileVisitOption[0]).map(path2 -> {
            try {
                C0026Z profile = new C0026Z();
                Wini ini = new Wini(path2.toFile());
                profile.m80a(path2.getParent().getFileName().toString());
                profile.m82b(path2.getParent().getParent().getFileName().toString());
                String executable = ini.get(ini.getConfig().getGlobalSectionName(), "executable").replaceAll("%.+%", "");
                Path executablePath = Paths.get(executable, new String[0]).isAbsolute() ? Paths.get(executable, new String[0]) : this.f85a.resolve(executable);
                profile.m85c(executablePath.toString());
                profile.m87d(ini.get(ini.getConfig().getGlobalSectionName(), "parameters"));
                return profile;
            } catch (IOException exception) {
                RomStation.m42b().log(Level.SEVERE, exception.getMessage(), (Throwable) exception);
                return null;
            }
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).filter(emulatorProfile -> {
            return Files.isRegularFile(Paths.get(emulatorProfile.m84d(), new String[0]), new LinkOption[0]);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: a */
    private List<C0024X> m159a(List<C0026Z> profiles) throws IOException {
        return (List) Files.find(this.f85a.resolve("Emulation"), 3, (path, basicFileAttributes) -> {
            return path.endsWith("romstation_parameters.ini");
        }, new FileVisitOption[0]).filter(path2 -> {
            return profiles.stream().anyMatch(emulatorProfile -> {
                return Paths.get(emulatorProfile.m84d(), new String[0]).startsWith(path2.getParent());
            });
        }).map(path3 -> {
            return new C0024X(path3.getParent().getFileName().toString(), path3.getParent());
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: b */
    private List<C0024X> m160b(List<C0024X> emulators) {
        return (List) emulators.stream().filter(emulator -> {
            return Files.exists(emulator.getDirectory(), new LinkOption[0]);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: a */
    private void m161a(List<C0024X> emulators, List<C0026Z> profiles) {
        emulators.forEach(emulator -> {
            emulator.getProfiles().addAll((Collection) profiles.stream().filter(emulatorProfile -> {
                return Paths.get(emulatorProfile.m84d(), new String[0]).startsWith(emulator.getDirectory());
            }).peek(emulatorProfile2 -> {
                emulatorProfile2.m89a(emulator);
            }).collect(Collectors.toList()));
        });
    }
}
