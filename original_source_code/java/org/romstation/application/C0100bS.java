package org.romstation.application;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import org.romstation.application.view.control.ApplicationFXMLDialog;
import org.romstation.application.virtualcontroller.device.AbstractC0271a;
import org.romstation.application.virtualcontroller.device.C0272b;
import org.romstation.application.virtualcontroller.device.C0274d;
import org.romstation.application.virtualcontroller.device.IdentifierNotFoundException;

/* JADX INFO: renamed from: org.romstation.application.bS */
/* JADX INFO: compiled from: BindInputDialog.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/bS.class */
public class C0100bS extends ApplicationFXMLDialog<C0159cY> implements Initializable {

    /* JADX INFO: renamed from: a */
    private final AbstractC0271a f251a;

    /* JADX INFO: renamed from: b */
    private final AbstractC0199dk f252b;

    /* JADX INFO: renamed from: d */
    private AnimationTimer f254d;

    /* JADX INFO: renamed from: e */
    private Event f255e;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label inputLabel;

    /* JADX INFO: renamed from: c */
    private final LinkedHashSet<String> f253c = new LinkedHashSet<>();

    /* JADX INFO: renamed from: f */
    private final LinkedHashMap<Component.Identifier, Float[]> f256f = new LinkedHashMap<>();

    public C0100bS(AbstractC0271a device, AbstractC0199dk input) {
        this.f251a = device;
        this.f252b = input;
        load(getClass().getResource("/fxml/dialog/virtualcontroller/bindInputDialog.fxml"));
        setOnCloseRequest(this::m524a);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inputLabel.setText(String.format(getResources().getString("bindInputDialog.input"), this.f252b.m806d()));
        this.dialogPane.lookupButton(ButtonType.CLOSE).setFocusTraversable(false);
        if (this.f251a instanceof C0274d) {
            this.dialogPane.sceneProperty().addListener((observableValue, previousScene, currentScene) -> {
                if (currentScene != null) {
                    currentScene.setOnKeyPressed(this::m517a);
                    currentScene.setOnKeyReleased(this::m518b);
                    currentScene.setOnMousePressed(this::m519a);
                    currentScene.setOnMouseReleased(this::m520b);
                }
            });
        } else if (this.f251a instanceof C0272b) {
            this.f255e = new Event();
            while (((C0272b) this.f251a).m1615f().getEventQueue().getNextEvent(this.f255e)) {
            }
            this.f254d = new AnimationTimer() { // from class: org.romstation.application.bS.1
                public void handle(long now) {
                    C0100bS.this.m522a(now);
                }
            };
            this.f254d.start();
        }
    }

    /* JADX INFO: renamed from: a */
    private void m517a(KeyEvent event) {
        try {
            this.f253c.add(C0274d.m1641a(event.getCode()));
        } catch (IdentifierNotFoundException exception) {
            RomStation.m42b().log(Level.WARNING, "bind input failed", (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m518b(KeyEvent event) {
        try {
            String binding = C0274d.m1641a(event.getCode());
            this.f253c.remove(binding);
            this.f253c.add(binding);
            setResult(new C0159cY(this.f253c));
        } catch (IdentifierNotFoundException exception) {
            RomStation.m42b().log(Level.WARNING, "bind input failed", (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m519a(MouseEvent event) {
        try {
            this.f253c.add(C0274d.m1642a(event.getButton()));
        } catch (IdentifierNotFoundException exception) {
            RomStation.m42b().log(Level.WARNING, "bind input failed", (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m520b(MouseEvent event) {
        try {
            String binding = C0274d.m1642a(event.getButton());
            this.f253c.remove(binding);
            this.f253c.add(binding);
            setResult(new C0159cY(this.f253c));
        } catch (IdentifierNotFoundException exception) {
            RomStation.m42b().log(Level.WARNING, "bind input failed", (Throwable) exception);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m521a(Component.Identifier identifier) {
        Float[] values = this.f256f.remove(identifier);
        if (values != null) {
            try {
                for (Map.Entry<Component.Identifier, Float[]> entry : this.f256f.entrySet()) {
                    if (entry.getValue()[1].floatValue() != 0.0f || entry.getValue()[2].floatValue() != 0.0f) {
                        this.f253c.add(C0272b.m1624a(entry.getKey(), entry.getValue()[1].floatValue() + entry.getValue()[2].floatValue()));
                    }
                }
                this.f253c.add(C0272b.m1624a(identifier, values[1].floatValue() + values[2].floatValue()));
                setResult(new C0159cY(this.f253c));
            } catch (IdentifierNotFoundException exception) {
                RomStation.m42b().log(Level.WARNING, "bind input failed", (Throwable) exception);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m522a(long now) {
        Controller controller = ((C0272b) this.f251a).m1615f();
        if (controller.poll()) {
            while (controller.getEventQueue().getNextEvent(this.f255e)) {
                Component.Identifier identifier = this.f255e.getComponent().getIdentifier();
                float value = this.f255e.getValue();
                Float[] values = this.f256f.computeIfAbsent(identifier, key -> {
                    return new Float[]{Float.valueOf(value), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                });
                if (!(identifier instanceof Component.Identifier.Axis) || identifier == Component.Identifier.Axis.POV) {
                    if (value == 0.0f) {
                        m521a(identifier);
                        return;
                    } else {
                        values[2] = Float.valueOf(value);
                        this.f256f.put(identifier, values);
                    }
                } else if (values[1].floatValue() == 0.0f && value <= -0.75f) {
                    values[1] = Float.valueOf(-1.0f);
                    this.f256f.put(identifier, values);
                } else if (values[2].floatValue() == 0.0f && value >= 0.75f) {
                    values[2] = Float.valueOf(1.0f);
                    this.f256f.put(identifier, values);
                } else if (values[1].floatValue() != 0.0f || values[2].floatValue() != 0.0f) {
                    if ((values[0].floatValue() <= -0.75d && value <= values[0].floatValue()) || ((values[0].floatValue() >= 0.75d && value >= values[0].floatValue()) || (Math.abs(values[0].floatValue()) <= 0.25d && Math.abs(value) <= 0.25d))) {
                        m521a(identifier);
                        return;
                    }
                }
            }
        }
    }

    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    protected Object controllerFactory(Class<?> classType) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.romstation.application.view.control.ApplicationFXMLDialog
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public C0159cY resultConverter(ButtonType buttonType) {
        return (C0159cY) getResult();
    }

    /* JADX INFO: renamed from: a */
    private void m524a(DialogEvent event) {
        if (this.f254d != null) {
            this.f254d.stop();
        }
    }
}
