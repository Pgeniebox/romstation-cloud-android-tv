package org.romstation.application.virtualcontroller.device;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/* JADX INFO: renamed from: org.romstation.application.virtualcontroller.device.d */
/* JADX INFO: compiled from: KeyboardMouseDevice.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/virtualcontroller/device/d.class */
public class C0274d extends AbstractC0271a {

    /* JADX INFO: renamed from: a */
    private static C0274d f857a;

    /* JADX INFO: renamed from: b */
    private static final HashMap<String, KeyCode> f858b = new HashMap<>();

    /* JADX INFO: renamed from: c */
    private static final HashMap<String, MouseButton> f859c = new HashMap<>();

    /* JADX INFO: renamed from: d */
    private C0273c f860d;

    static {
        f858b.put("KEY.ENTER", KeyCode.ENTER);
        f858b.put("KEY.BACK_SPACE", KeyCode.BACK_SPACE);
        f858b.put("KEY.TAB", KeyCode.TAB);
        f858b.put("KEY.CANCEL", KeyCode.CANCEL);
        f858b.put("KEY.CLEAR", KeyCode.CLEAR);
        f858b.put("KEY.SHIFT", KeyCode.SHIFT);
        f858b.put("KEY.CONTROL", KeyCode.CONTROL);
        f858b.put("KEY.ALT", KeyCode.ALT);
        f858b.put("KEY.PAUSE", KeyCode.PAUSE);
        f858b.put("KEY.CAPS", KeyCode.CAPS);
        f858b.put("KEY.ESCAPE", KeyCode.ESCAPE);
        f858b.put("KEY.SPACE", KeyCode.SPACE);
        f858b.put("KEY.PAGE_UP", KeyCode.PAGE_UP);
        f858b.put("KEY.PAGE_DOWN", KeyCode.PAGE_DOWN);
        f858b.put("KEY.END", KeyCode.END);
        f858b.put("KEY.HOME", KeyCode.HOME);
        f858b.put("KEY.LEFT", KeyCode.LEFT);
        f858b.put("KEY.UP", KeyCode.UP);
        f858b.put("KEY.RIGHT", KeyCode.RIGHT);
        f858b.put("KEY.DOWN", KeyCode.DOWN);
        f858b.put("KEY.COMMA", KeyCode.COMMA);
        f858b.put("KEY.MINUS", KeyCode.MINUS);
        f858b.put("KEY.PERIOD", KeyCode.PERIOD);
        f858b.put("KEY.SLASH", KeyCode.SLASH);
        f858b.put("KEY.DIGIT0", KeyCode.DIGIT0);
        f858b.put("KEY.DIGIT1", KeyCode.DIGIT1);
        f858b.put("KEY.DIGIT2", KeyCode.DIGIT2);
        f858b.put("KEY.DIGIT3", KeyCode.DIGIT3);
        f858b.put("KEY.DIGIT4", KeyCode.DIGIT4);
        f858b.put("KEY.DIGIT5", KeyCode.DIGIT5);
        f858b.put("KEY.DIGIT6", KeyCode.DIGIT6);
        f858b.put("KEY.DIGIT7", KeyCode.DIGIT7);
        f858b.put("KEY.DIGIT8", KeyCode.DIGIT8);
        f858b.put("KEY.DIGIT9", KeyCode.DIGIT9);
        f858b.put("KEY.SEMICOLON", KeyCode.SEMICOLON);
        f858b.put("KEY.EQUALS", KeyCode.EQUALS);
        f858b.put("KEY.A", KeyCode.A);
        f858b.put("KEY.B", KeyCode.B);
        f858b.put("KEY.C", KeyCode.C);
        f858b.put("KEY.D", KeyCode.D);
        f858b.put("KEY.E", KeyCode.E);
        f858b.put("KEY.F", KeyCode.F);
        f858b.put("KEY.G", KeyCode.G);
        f858b.put("KEY.H", KeyCode.H);
        f858b.put("KEY.I", KeyCode.I);
        f858b.put("KEY.J", KeyCode.J);
        f858b.put("KEY.K", KeyCode.K);
        f858b.put("KEY.L", KeyCode.L);
        f858b.put("KEY.M", KeyCode.M);
        f858b.put("KEY.N", KeyCode.N);
        f858b.put("KEY.O", KeyCode.O);
        f858b.put("KEY.P", KeyCode.P);
        f858b.put("KEY.Q", KeyCode.Q);
        f858b.put("KEY.R", KeyCode.R);
        f858b.put("KEY.S", KeyCode.S);
        f858b.put("KEY.T", KeyCode.T);
        f858b.put("KEY.U", KeyCode.U);
        f858b.put("KEY.V", KeyCode.V);
        f858b.put("KEY.W", KeyCode.W);
        f858b.put("KEY.X", KeyCode.X);
        f858b.put("KEY.Y", KeyCode.Y);
        f858b.put("KEY.Z", KeyCode.Z);
        f858b.put("KEY.OPEN_BRACKET", KeyCode.OPEN_BRACKET);
        f858b.put("KEY.BACK_SLASH", KeyCode.BACK_SLASH);
        f858b.put("KEY.CLOSE_BRACKET", KeyCode.CLOSE_BRACKET);
        f858b.put("KEY.NUMPAD0", KeyCode.NUMPAD0);
        f858b.put("KEY.NUMPAD1", KeyCode.NUMPAD1);
        f858b.put("KEY.NUMPAD2", KeyCode.NUMPAD2);
        f858b.put("KEY.NUMPAD3", KeyCode.NUMPAD3);
        f858b.put("KEY.NUMPAD4", KeyCode.NUMPAD4);
        f858b.put("KEY.NUMPAD5", KeyCode.NUMPAD5);
        f858b.put("KEY.NUMPAD6", KeyCode.NUMPAD6);
        f858b.put("KEY.NUMPAD7", KeyCode.NUMPAD7);
        f858b.put("KEY.NUMPAD8", KeyCode.NUMPAD8);
        f858b.put("KEY.NUMPAD9", KeyCode.NUMPAD9);
        f858b.put("KEY.MULTIPLY", KeyCode.MULTIPLY);
        f858b.put("KEY.ADD", KeyCode.ADD);
        f858b.put("KEY.SEPARATOR", KeyCode.SEPARATOR);
        f858b.put("KEY.SUBTRACT", KeyCode.SUBTRACT);
        f858b.put("KEY.DECIMAL", KeyCode.DECIMAL);
        f858b.put("KEY.DIVIDE", KeyCode.DIVIDE);
        f858b.put("KEY.DELETE", KeyCode.DELETE);
        f858b.put("KEY.NUM_LOCK", KeyCode.NUM_LOCK);
        f858b.put("KEY.SCROLL_LOCK", KeyCode.SCROLL_LOCK);
        f858b.put("KEY.F1", KeyCode.F1);
        f858b.put("KEY.F2", KeyCode.F2);
        f858b.put("KEY.F3", KeyCode.F3);
        f858b.put("KEY.F4", KeyCode.F4);
        f858b.put("KEY.F5", KeyCode.F5);
        f858b.put("KEY.F6", KeyCode.F6);
        f858b.put("KEY.F7", KeyCode.F7);
        f858b.put("KEY.F8", KeyCode.F8);
        f858b.put("KEY.F9", KeyCode.F9);
        f858b.put("KEY.F10", KeyCode.F10);
        f858b.put("KEY.F11", KeyCode.F11);
        f858b.put("KEY.F12", KeyCode.F12);
        f858b.put("KEY.F13", KeyCode.F13);
        f858b.put("KEY.F14", KeyCode.F14);
        f858b.put("KEY.F15", KeyCode.F15);
        f858b.put("KEY.F16", KeyCode.F16);
        f858b.put("KEY.F17", KeyCode.F17);
        f858b.put("KEY.F18", KeyCode.F18);
        f858b.put("KEY.F19", KeyCode.F19);
        f858b.put("KEY.F20", KeyCode.F20);
        f858b.put("KEY.F21", KeyCode.F21);
        f858b.put("KEY.F22", KeyCode.F22);
        f858b.put("KEY.F23", KeyCode.F23);
        f858b.put("KEY.F24", KeyCode.F24);
        f858b.put("KEY.PRINTSCREEN", KeyCode.PRINTSCREEN);
        f858b.put("KEY.INSERT", KeyCode.INSERT);
        f858b.put("KEY.HELP", KeyCode.HELP);
        f858b.put("KEY.META", KeyCode.META);
        f858b.put("KEY.BACK_QUOTE", KeyCode.BACK_QUOTE);
        f858b.put("KEY.QUOTE", KeyCode.QUOTE);
        f858b.put("KEY.KP_UP", KeyCode.KP_UP);
        f858b.put("KEY.KP_DOWN", KeyCode.KP_DOWN);
        f858b.put("KEY.KP_LEFT", KeyCode.KP_LEFT);
        f858b.put("KEY.KP_RIGHT", KeyCode.KP_RIGHT);
        f858b.put("KEY.DEAD_GRAVE", KeyCode.DEAD_GRAVE);
        f858b.put("KEY.DEAD_ACUTE", KeyCode.DEAD_ACUTE);
        f858b.put("KEY.DEAD_CIRCUMFLEX", KeyCode.DEAD_CIRCUMFLEX);
        f858b.put("KEY.DEAD_TILDE", KeyCode.DEAD_TILDE);
        f858b.put("KEY.DEAD_MACRON", KeyCode.DEAD_MACRON);
        f858b.put("KEY.DEAD_BREVE", KeyCode.DEAD_BREVE);
        f858b.put("KEY.DEAD_ABOVEDOT", KeyCode.DEAD_ABOVEDOT);
        f858b.put("KEY.DEAD_DIAERESIS", KeyCode.DEAD_DIAERESIS);
        f858b.put("KEY.DEAD_ABOVERING", KeyCode.DEAD_ABOVERING);
        f858b.put("KEY.DEAD_DOUBLEACUTE", KeyCode.DEAD_DOUBLEACUTE);
        f858b.put("KEY.DEAD_CARON", KeyCode.DEAD_CARON);
        f858b.put("KEY.DEAD_CEDILLA", KeyCode.DEAD_CEDILLA);
        f858b.put("KEY.DEAD_OGONEK", KeyCode.DEAD_OGONEK);
        f858b.put("KEY.DEAD_IOTA", KeyCode.DEAD_IOTA);
        f858b.put("KEY.DEAD_VOICED_SOUND", KeyCode.DEAD_VOICED_SOUND);
        f858b.put("KEY.DEAD_SEMIVOICED_SOUND", KeyCode.DEAD_SEMIVOICED_SOUND);
        f858b.put("KEY.AMPERSAND", KeyCode.AMPERSAND);
        f858b.put("KEY.ASTERISK", KeyCode.ASTERISK);
        f858b.put("KEY.QUOTEDBL", KeyCode.QUOTEDBL);
        f858b.put("KEY.LESS", KeyCode.LESS);
        f858b.put("KEY.GREATER", KeyCode.GREATER);
        f858b.put("KEY.BRACELEFT", KeyCode.BRACELEFT);
        f858b.put("KEY.BRACERIGHT", KeyCode.BRACERIGHT);
        f858b.put("KEY.AT", KeyCode.AT);
        f858b.put("KEY.COLON", KeyCode.COLON);
        f858b.put("KEY.CIRCUMFLEX", KeyCode.CIRCUMFLEX);
        f858b.put("KEY.DOLLAR", KeyCode.DOLLAR);
        f858b.put("KEY.EURO_SIGN", KeyCode.EURO_SIGN);
        f858b.put("KEY.EXCLAMATION_MARK", KeyCode.EXCLAMATION_MARK);
        f858b.put("KEY.INVERTED_EXCLAMATION_MARK", KeyCode.INVERTED_EXCLAMATION_MARK);
        f858b.put("KEY.LEFT_PARENTHESIS", KeyCode.LEFT_PARENTHESIS);
        f858b.put("KEY.NUMBER_SIGN", KeyCode.NUMBER_SIGN);
        f858b.put("KEY.PLUS", KeyCode.PLUS);
        f858b.put("KEY.RIGHT_PARENTHESIS", KeyCode.RIGHT_PARENTHESIS);
        f858b.put("KEY.UNDERSCORE", KeyCode.UNDERSCORE);
        f858b.put("KEY.WINDOWS", KeyCode.WINDOWS);
        f858b.put("KEY.CONTEXT_MENU", KeyCode.CONTEXT_MENU);
        f858b.put("KEY.FINAL", KeyCode.FINAL);
        f858b.put("KEY.CONVERT", KeyCode.CONVERT);
        f858b.put("KEY.NONCONVERT", KeyCode.NONCONVERT);
        f858b.put("KEY.ACCEPT", KeyCode.ACCEPT);
        f858b.put("KEY.MODECHANGE", KeyCode.MODECHANGE);
        f858b.put("KEY.KANA", KeyCode.KANA);
        f858b.put("KEY.KANJI", KeyCode.KANJI);
        f858b.put("KEY.ALPHANUMERIC", KeyCode.ALPHANUMERIC);
        f858b.put("KEY.KATAKANA", KeyCode.KATAKANA);
        f858b.put("KEY.HIRAGANA", KeyCode.HIRAGANA);
        f858b.put("KEY.FULL_WIDTH", KeyCode.FULL_WIDTH);
        f858b.put("KEY.HALF_WIDTH", KeyCode.HALF_WIDTH);
        f858b.put("KEY.ROMAN_CHARACTERS", KeyCode.ROMAN_CHARACTERS);
        f858b.put("KEY.ALL_CANDIDATES", KeyCode.ALL_CANDIDATES);
        f858b.put("KEY.PREVIOUS_CANDIDATE", KeyCode.PREVIOUS_CANDIDATE);
        f858b.put("KEY.CODE_INPUT", KeyCode.CODE_INPUT);
        f858b.put("KEY.JAPANESE_KATAKANA", KeyCode.JAPANESE_KATAKANA);
        f858b.put("KEY.JAPANESE_HIRAGANA", KeyCode.JAPANESE_HIRAGANA);
        f858b.put("KEY.JAPANESE_ROMAN", KeyCode.JAPANESE_ROMAN);
        f858b.put("KEY.KANA_LOCK", KeyCode.KANA_LOCK);
        f858b.put("KEY.INPUT_METHOD_ON_OFF", KeyCode.INPUT_METHOD_ON_OFF);
        f858b.put("KEY.CUT", KeyCode.CUT);
        f858b.put("KEY.COPY", KeyCode.COPY);
        f858b.put("KEY.PASTE", KeyCode.PASTE);
        f858b.put("KEY.UNDO", KeyCode.UNDO);
        f858b.put("KEY.AGAIN", KeyCode.AGAIN);
        f858b.put("KEY.FIND", KeyCode.FIND);
        f858b.put("KEY.PROPS", KeyCode.PROPS);
        f858b.put("KEY.STOP", KeyCode.STOP);
        f858b.put("KEY.COMPOSE", KeyCode.COMPOSE);
        f858b.put("KEY.ALT_GRAPH", KeyCode.ALT_GRAPH);
        f858b.put("KEY.BEGIN", KeyCode.BEGIN);
        f858b.put("KEY.UNDEFINED", KeyCode.UNDEFINED);
        f858b.put("KEY.SOFTKEY_0", KeyCode.SOFTKEY_0);
        f858b.put("KEY.SOFTKEY_1", KeyCode.SOFTKEY_1);
        f858b.put("KEY.SOFTKEY_2", KeyCode.SOFTKEY_2);
        f858b.put("KEY.SOFTKEY_3", KeyCode.SOFTKEY_3);
        f858b.put("KEY.SOFTKEY_4", KeyCode.SOFTKEY_4);
        f858b.put("KEY.SOFTKEY_5", KeyCode.SOFTKEY_5);
        f858b.put("KEY.SOFTKEY_6", KeyCode.SOFTKEY_6);
        f858b.put("KEY.SOFTKEY_7", KeyCode.SOFTKEY_7);
        f858b.put("KEY.SOFTKEY_8", KeyCode.SOFTKEY_8);
        f858b.put("KEY.SOFTKEY_9", KeyCode.SOFTKEY_9);
        f858b.put("KEY.GAME_A", KeyCode.GAME_A);
        f858b.put("KEY.GAME_B", KeyCode.GAME_B);
        f858b.put("KEY.GAME_C", KeyCode.GAME_C);
        f858b.put("KEY.GAME_D", KeyCode.GAME_D);
        f858b.put("KEY.STAR", KeyCode.STAR);
        f858b.put("KEY.POUND", KeyCode.POUND);
        f858b.put("KEY.POWER", KeyCode.POWER);
        f858b.put("KEY.INFO", KeyCode.INFO);
        f858b.put("KEY.COLORED_KEY_0", KeyCode.COLORED_KEY_0);
        f858b.put("KEY.COLORED_KEY_1", KeyCode.COLORED_KEY_1);
        f858b.put("KEY.COLORED_KEY_2", KeyCode.COLORED_KEY_2);
        f858b.put("KEY.COLORED_KEY_3", KeyCode.COLORED_KEY_3);
        f858b.put("KEY.EJECT_TOGGLE", KeyCode.EJECT_TOGGLE);
        f858b.put("KEY.PLAY", KeyCode.PLAY);
        f858b.put("KEY.RECORD", KeyCode.RECORD);
        f858b.put("KEY.FAST_FWD", KeyCode.FAST_FWD);
        f858b.put("KEY.REWIND", KeyCode.REWIND);
        f858b.put("KEY.TRACK_PREV", KeyCode.TRACK_PREV);
        f858b.put("KEY.TRACK_NEXT", KeyCode.TRACK_NEXT);
        f858b.put("KEY.CHANNEL_UP", KeyCode.CHANNEL_UP);
        f858b.put("KEY.CHANNEL_DOWN", KeyCode.CHANNEL_DOWN);
        f858b.put("KEY.VOLUME_UP", KeyCode.VOLUME_UP);
        f858b.put("KEY.VOLUME_DOWN", KeyCode.VOLUME_DOWN);
        f858b.put("KEY.MUTE", KeyCode.MUTE);
        f858b.put("KEY.COMMAND", KeyCode.COMMAND);
        f858b.put("KEY.SHORTCUT", KeyCode.SHORTCUT);
        f859c.put("MOUSE.PRIMARY", MouseButton.PRIMARY);
        f859c.put("MOUSE.SECONDARY", MouseButton.SECONDARY);
        f859c.put("MOUSE.MIDDLE", MouseButton.MIDDLE);
    }

    private C0274d() {
    }

    /* JADX INFO: renamed from: e */
    public C0273c m1636e() {
        return this.f860d;
    }

    /* JADX INFO: renamed from: a */
    public void m1637a(C0273c inputListener) {
        this.f860d = inputListener;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: a */
    public String mo1608a() {
        return "Keyboard Mouse";
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: b */
    public List<String> mo1609b() {
        LinkedList<String> commands = new LinkedList<>();
        commands.addAll(f858b.keySet());
        commands.addAll(f859c.keySet());
        Collections.addAll(commands, "MOUSE.X", "MOUSE.X-", "MOUSE.X+", "MOUSE.Y", "MOUSE.Y-", "MOUSE.Y+");
        commands.sort((v0, v1) -> {
            return v0.compareTo(v1);
        });
        return commands;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: c */
    public boolean mo1610c() {
        return true;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: d */
    public void mo1611d() {
        this.f860d = null;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: a */
    protected float mo1613a(String command) {
        if (this.f860d == null) {
            return 0.0f;
        }
        try {
            switch (command) {
                case "MOUSE.X":
                    return (float) this.f860d.m1634a();
                case "MOUSE.X-":
                    return (float) (((1.0d - Math.min(0.5d, this.f860d.m1634a())) - 0.5d) / 0.5d);
                case "MOUSE.X+":
                    return (float) ((Math.max(0.5d, this.f860d.m1634a()) - 0.5d) / 0.5d);
                case "MOUSE.Y":
                    return (float) this.f860d.m1635b();
                case "MOUSE.Y-":
                    return (float) (((1.0d - Math.min(0.5d, this.f860d.m1635b())) - 0.5d) / 0.5d);
                case "MOUSE.Y+":
                    return (float) ((Math.max(0.5d, this.f860d.m1635b()) - 0.5d) / 0.5d);
                case "MOUSE.PRIMARY":
                case "MOUSE.SECONDARY":
                case "MOUSE.MIDDLE":
                    return this.f860d.m1633a(m1640c(command)) ? 1.0f : 0.0f;
                default:
                    return this.f860d.m1629a(m1639b(command)) ? 1.0f : 0.0f;
            }
        } catch (IdentifierNotFoundException e) {
            return 0.0f;
        }
    }

    /* JADX INFO: renamed from: f */
    public static C0274d m1638f() {
        if (f857a == null) {
            f857a = new C0274d();
        }
        return f857a;
    }

    /* JADX INFO: renamed from: b */
    private static KeyCode m1639b(String command) throws IdentifierNotFoundException {
        if (f858b.containsKey(command)) {
            return f858b.get(command);
        }
        throw new IdentifierNotFoundException(String.format("unable to map command %s to key code", command));
    }

    /* JADX INFO: renamed from: c */
    private static MouseButton m1640c(String command) throws IdentifierNotFoundException {
        if (f859c.containsKey(command)) {
            return f859c.get(command);
        }
        throw new IdentifierNotFoundException(String.format("unable to map command %s to mouse button", command));
    }

    /* JADX INFO: renamed from: a */
    public static String m1641a(KeyCode keyCode) throws IdentifierNotFoundException {
        for (Map.Entry<String, KeyCode> entry : f858b.entrySet()) {
            if (entry.getValue() == keyCode) {
                return entry.getKey();
            }
        }
        throw new IdentifierNotFoundException(String.format("unable to map key code %s to command", keyCode.getName()));
    }

    /* JADX INFO: renamed from: a */
    public static String m1642a(MouseButton mouseButton) throws IdentifierNotFoundException {
        for (Map.Entry<String, MouseButton> entry : f859c.entrySet()) {
            if (entry.getValue() == mouseButton) {
                return entry.getKey();
            }
        }
        throw new IdentifierNotFoundException(String.format("unable to map mouse button %s to command", mouseButton.name()));
    }
}
