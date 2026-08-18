package org.romstation.application.virtualcontroller.device;

import java.util.HashSet;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/* JADX INFO: renamed from: org.romstation.application.virtualcontroller.device.c */
/* JADX INFO: compiled from: FXInputListener.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/virtualcontroller/device/c.class */
public class C0273c {

    /* JADX INFO: renamed from: a */
    private final HashSet<KeyCode> f853a;

    /* JADX INFO: renamed from: b */
    private final HashSet<MouseButton> f854b;

    /* JADX INFO: renamed from: c */
    private double f855c;

    /* JADX INFO: renamed from: d */
    private double f856d;

    public C0273c(Node inputNode) {
        this(inputNode, inputNode);
    }

    public C0273c(Node keyboardInputNode, Node mouseInputNode) {
        this.f853a = new HashSet<>();
        this.f854b = new HashSet<>();
        keyboardInputNode.addEventFilter(KeyEvent.KEY_PRESSED, this::m1627a);
        keyboardInputNode.addEventFilter(KeyEvent.KEY_RELEASED, this::m1628b);
        mouseInputNode.addEventFilter(MouseEvent.MOUSE_PRESSED, this::m1630a);
        mouseInputNode.addEventFilter(MouseEvent.MOUSE_RELEASED, this::m1631b);
        mouseInputNode.addEventFilter(MouseEvent.MOUSE_MOVED, this::m1632c);
        mouseInputNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::m1632c);
    }

    /* JADX INFO: renamed from: a */
    private void m1627a(KeyEvent event) {
        this.f853a.add(event.getCode());
    }

    /* JADX INFO: renamed from: b */
    private void m1628b(KeyEvent event) {
        this.f853a.remove(event.getCode());
    }

    /* JADX INFO: renamed from: a */
    public boolean m1629a(KeyCode keyCode) {
        return this.f853a.contains(keyCode);
    }

    /* JADX INFO: renamed from: a */
    private void m1630a(MouseEvent event) {
        this.f854b.add(event.getButton());
    }

    /* JADX INFO: renamed from: b */
    private void m1631b(MouseEvent event) {
        this.f854b.remove(event.getButton());
    }

    /* JADX INFO: renamed from: c */
    private void m1632c(MouseEvent event) {
        this.f855c = event.getX() / ((Node) event.getSource()).getBoundsInParent().getWidth();
        this.f856d = event.getY() / ((Node) event.getSource()).getBoundsInParent().getHeight();
    }

    /* JADX INFO: renamed from: a */
    public boolean m1633a(MouseButton mouseButton) {
        return this.f854b.contains(mouseButton);
    }

    /* JADX INFO: renamed from: a */
    public double m1634a() {
        return this.f855c;
    }

    /* JADX INFO: renamed from: b */
    public double m1635b() {
        return this.f856d;
    }
}
