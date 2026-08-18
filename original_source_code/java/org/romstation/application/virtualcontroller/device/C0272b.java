package org.romstation.application.virtualcontroller.device;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.DirectAndRawInputEnvironmentPlugin;
import net.java.games.input.OSXEnvironmentPlugin;
import org.romstation.application.C0004E;

/* JADX INFO: renamed from: org.romstation.application.virtualcontroller.device.b */
/* JADX INFO: compiled from: DirectInputDevice.java */
/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/virtualcontroller/device/b.class */
public class C0272b extends AbstractC0271a {

    /* JADX INFO: renamed from: a */
    private static final HashMap<String, Component.Identifier> f848a = new HashMap<>();

    /* JADX INFO: renamed from: b */
    private final int f849b;

    /* JADX INFO: renamed from: c */
    private final Controller f850c;

    /* JADX INFO: renamed from: d */
    private final String f851d;

    static {
        f848a.put("BUTTON.0", Component.Identifier.Button._0);
        f848a.put("BUTTON.1", Component.Identifier.Button._1);
        f848a.put("BUTTON.10", Component.Identifier.Button._10);
        f848a.put("BUTTON.11", Component.Identifier.Button._11);
        f848a.put("BUTTON.12", Component.Identifier.Button._12);
        f848a.put("BUTTON.13", Component.Identifier.Button._13);
        f848a.put("BUTTON.14", Component.Identifier.Button._14);
        f848a.put("BUTTON.15", Component.Identifier.Button._15);
        f848a.put("BUTTON.16", Component.Identifier.Button._16);
        f848a.put("BUTTON.17", Component.Identifier.Button._17);
        f848a.put("BUTTON.18", Component.Identifier.Button._18);
        f848a.put("BUTTON.19", Component.Identifier.Button._19);
        f848a.put("BUTTON.2", Component.Identifier.Button._2);
        f848a.put("BUTTON.20", Component.Identifier.Button._20);
        f848a.put("BUTTON.21", Component.Identifier.Button._21);
        f848a.put("BUTTON.22", Component.Identifier.Button._22);
        f848a.put("BUTTON.23", Component.Identifier.Button._23);
        f848a.put("BUTTON.24", Component.Identifier.Button._24);
        f848a.put("BUTTON.25", Component.Identifier.Button._25);
        f848a.put("BUTTON.26", Component.Identifier.Button._26);
        f848a.put("BUTTON.27", Component.Identifier.Button._27);
        f848a.put("BUTTON.28", Component.Identifier.Button._28);
        f848a.put("BUTTON.29", Component.Identifier.Button._29);
        f848a.put("BUTTON.3", Component.Identifier.Button._3);
        f848a.put("BUTTON.30", Component.Identifier.Button._30);
        f848a.put("BUTTON.31", Component.Identifier.Button._31);
        f848a.put("BUTTON.4", Component.Identifier.Button._4);
        f848a.put("BUTTON.5", Component.Identifier.Button._5);
        f848a.put("BUTTON.6", Component.Identifier.Button._6);
        f848a.put("BUTTON.7", Component.Identifier.Button._7);
        f848a.put("BUTTON.8", Component.Identifier.Button._8);
        f848a.put("BUTTON.9", Component.Identifier.Button._9);
        f848a.put("BUTTON.A", Component.Identifier.Button.A);
        f848a.put("BUTTON.B", Component.Identifier.Button.B);
        f848a.put("BUTTON.BACK", Component.Identifier.Button.BACK);
        f848a.put("BUTTON.BASE", Component.Identifier.Button.BASE);
        f848a.put("BUTTON.BASE2", Component.Identifier.Button.BASE2);
        f848a.put("BUTTON.BASE3", Component.Identifier.Button.BASE3);
        f848a.put("BUTTON.BASE4", Component.Identifier.Button.BASE4);
        f848a.put("BUTTON.BASE5", Component.Identifier.Button.BASE5);
        f848a.put("BUTTON.BASE6", Component.Identifier.Button.BASE6);
        f848a.put("BUTTON.C", Component.Identifier.Button.C);
        f848a.put("BUTTON.DEAD", Component.Identifier.Button.DEAD);
        f848a.put("BUTTON.EXTRA", Component.Identifier.Button.EXTRA);
        f848a.put("BUTTON.EXTRA_1", Component.Identifier.Button.EXTRA_1);
        f848a.put("BUTTON.EXTRA_10", Component.Identifier.Button.EXTRA_10);
        f848a.put("BUTTON.EXTRA_11", Component.Identifier.Button.EXTRA_11);
        f848a.put("BUTTON.EXTRA_12", Component.Identifier.Button.EXTRA_12);
        f848a.put("BUTTON.EXTRA_13", Component.Identifier.Button.EXTRA_13);
        f848a.put("BUTTON.EXTRA_14", Component.Identifier.Button.EXTRA_14);
        f848a.put("BUTTON.EXTRA_15", Component.Identifier.Button.EXTRA_15);
        f848a.put("BUTTON.EXTRA_16", Component.Identifier.Button.EXTRA_16);
        f848a.put("BUTTON.EXTRA_17", Component.Identifier.Button.EXTRA_17);
        f848a.put("BUTTON.EXTRA_18", Component.Identifier.Button.EXTRA_18);
        f848a.put("BUTTON.EXTRA_19", Component.Identifier.Button.EXTRA_19);
        f848a.put("BUTTON.EXTRA_2", Component.Identifier.Button.EXTRA_2);
        f848a.put("BUTTON.EXTRA_20", Component.Identifier.Button.EXTRA_20);
        f848a.put("BUTTON.EXTRA_21", Component.Identifier.Button.EXTRA_21);
        f848a.put("BUTTON.EXTRA_22", Component.Identifier.Button.EXTRA_22);
        f848a.put("BUTTON.EXTRA_23", Component.Identifier.Button.EXTRA_23);
        f848a.put("BUTTON.EXTRA_24", Component.Identifier.Button.EXTRA_24);
        f848a.put("BUTTON.EXTRA_25", Component.Identifier.Button.EXTRA_25);
        f848a.put("BUTTON.EXTRA_26", Component.Identifier.Button.EXTRA_26);
        f848a.put("BUTTON.EXTRA_27", Component.Identifier.Button.EXTRA_27);
        f848a.put("BUTTON.EXTRA_28", Component.Identifier.Button.EXTRA_28);
        f848a.put("BUTTON.EXTRA_29", Component.Identifier.Button.EXTRA_29);
        f848a.put("BUTTON.EXTRA_3", Component.Identifier.Button.EXTRA_3);
        f848a.put("BUTTON.EXTRA_30", Component.Identifier.Button.EXTRA_30);
        f848a.put("BUTTON.EXTRA_31", Component.Identifier.Button.EXTRA_31);
        f848a.put("BUTTON.EXTRA_32", Component.Identifier.Button.EXTRA_32);
        f848a.put("BUTTON.EXTRA_33", Component.Identifier.Button.EXTRA_33);
        f848a.put("BUTTON.EXTRA_34", Component.Identifier.Button.EXTRA_34);
        f848a.put("BUTTON.EXTRA_35", Component.Identifier.Button.EXTRA_35);
        f848a.put("BUTTON.EXTRA_36", Component.Identifier.Button.EXTRA_36);
        f848a.put("BUTTON.EXTRA_37", Component.Identifier.Button.EXTRA_37);
        f848a.put("BUTTON.EXTRA_38", Component.Identifier.Button.EXTRA_38);
        f848a.put("BUTTON.EXTRA_39", Component.Identifier.Button.EXTRA_39);
        f848a.put("BUTTON.EXTRA_4", Component.Identifier.Button.EXTRA_4);
        f848a.put("BUTTON.EXTRA_40", Component.Identifier.Button.EXTRA_40);
        f848a.put("BUTTON.EXTRA_5", Component.Identifier.Button.EXTRA_5);
        f848a.put("BUTTON.EXTRA_6", Component.Identifier.Button.EXTRA_6);
        f848a.put("BUTTON.EXTRA_7", Component.Identifier.Button.EXTRA_7);
        f848a.put("BUTTON.EXTRA_8", Component.Identifier.Button.EXTRA_8);
        f848a.put("BUTTON.EXTRA_9", Component.Identifier.Button.EXTRA_9);
        f848a.put("BUTTON.FORWARD", Component.Identifier.Button.FORWARD);
        f848a.put("BUTTON.LEFT", Component.Identifier.Button.LEFT);
        f848a.put("BUTTON.LEFT_THUMB", Component.Identifier.Button.LEFT_THUMB);
        f848a.put("BUTTON.LEFT_THUMB2", Component.Identifier.Button.LEFT_THUMB2);
        f848a.put("BUTTON.LEFT_THUMB3", Component.Identifier.Button.LEFT_THUMB3);
        f848a.put("BUTTON.MIDDLE", Component.Identifier.Button.MIDDLE);
        f848a.put("BUTTON.MODE", Component.Identifier.Button.MODE);
        f848a.put("BUTTON.PINKIE", Component.Identifier.Button.PINKIE);
        f848a.put("BUTTON.RIGHT", Component.Identifier.Button.RIGHT);
        f848a.put("BUTTON.RIGHT_THUMB", Component.Identifier.Button.RIGHT_THUMB);
        f848a.put("BUTTON.RIGHT_THUMB2", Component.Identifier.Button.RIGHT_THUMB2);
        f848a.put("BUTTON.RIGHT_THUMB3", Component.Identifier.Button.RIGHT_THUMB3);
        f848a.put("BUTTON.SELECT", Component.Identifier.Button.SELECT);
        f848a.put("BUTTON.SIDE", Component.Identifier.Button.SIDE);
        f848a.put("BUTTON.START", Component.Identifier.Button.START);
        f848a.put("BUTTON.STYLUS", Component.Identifier.Button.STYLUS);
        f848a.put("BUTTON.STYLUS2", Component.Identifier.Button.STYLUS2);
        f848a.put("BUTTON.THUMB", Component.Identifier.Button.THUMB);
        f848a.put("BUTTON.THUMB2", Component.Identifier.Button.THUMB2);
        f848a.put("BUTTON.TOOL_AIRBRUSH", Component.Identifier.Button.TOOL_AIRBRUSH);
        f848a.put("BUTTON.TOOL_BRUSH", Component.Identifier.Button.TOOL_BRUSH);
        f848a.put("BUTTON.TOOL_FINGER", Component.Identifier.Button.TOOL_FINGER);
        f848a.put("BUTTON.TOOL_LENS", Component.Identifier.Button.TOOL_LENS);
        f848a.put("BUTTON.TOOL_MOUSE", Component.Identifier.Button.TOOL_MOUSE);
        f848a.put("BUTTON.TOOL_PEN", Component.Identifier.Button.TOOL_PEN);
        f848a.put("BUTTON.TOOL_PENCIL", Component.Identifier.Button.TOOL_PENCIL);
        f848a.put("BUTTON.TOOL_RUBBER", Component.Identifier.Button.TOOL_RUBBER);
        f848a.put("BUTTON.TOP", Component.Identifier.Button.TOP);
        f848a.put("BUTTON.TOP2", Component.Identifier.Button.TOP2);
        f848a.put("BUTTON.TOUCH", Component.Identifier.Button.TOUCH);
        f848a.put("BUTTON.TRIGGER", Component.Identifier.Button.TRIGGER);
        f848a.put("BUTTON.UNKNOWN", Component.Identifier.Button.UNKNOWN);
        f848a.put("BUTTON.X", Component.Identifier.Button.X);
        f848a.put("BUTTON.Y", Component.Identifier.Button.Y);
        f848a.put("BUTTON.Z", Component.Identifier.Button.Z);
        f848a.put("AXIS.X", Component.Identifier.Axis.X);
        f848a.put("AXIS.X-", Component.Identifier.Axis.X);
        f848a.put("AXIS.X+", Component.Identifier.Axis.X);
        f848a.put("AXIS.Y", Component.Identifier.Axis.Y);
        f848a.put("AXIS.Y-", Component.Identifier.Axis.Y);
        f848a.put("AXIS.Y+", Component.Identifier.Axis.Y);
        f848a.put("AXIS.Z", Component.Identifier.Axis.Z);
        f848a.put("AXIS.Z-", Component.Identifier.Axis.Z);
        f848a.put("AXIS.Z+", Component.Identifier.Axis.Z);
        f848a.put("AXIS.RX", Component.Identifier.Axis.RX);
        f848a.put("AXIS.RX-", Component.Identifier.Axis.RX);
        f848a.put("AXIS.RX+", Component.Identifier.Axis.RX);
        f848a.put("AXIS.RY", Component.Identifier.Axis.RY);
        f848a.put("AXIS.RY-", Component.Identifier.Axis.RY);
        f848a.put("AXIS.RY+", Component.Identifier.Axis.RY);
        f848a.put("AXIS.RZ", Component.Identifier.Axis.RZ);
        f848a.put("AXIS.RZ-", Component.Identifier.Axis.RZ);
        f848a.put("AXIS.RZ+", Component.Identifier.Axis.RZ);
        f848a.put("AXIS.SLIDER", Component.Identifier.Axis.SLIDER);
        f848a.put("AXIS.SLIDER-", Component.Identifier.Axis.SLIDER);
        f848a.put("AXIS.SLIDER+", Component.Identifier.Axis.SLIDER);
        f848a.put("POV.UP", Component.Identifier.Axis.POV);
        f848a.put("POV.RIGHT", Component.Identifier.Axis.POV);
        f848a.put("POV.DOWN", Component.Identifier.Axis.POV);
        f848a.put("POV.LEFT", Component.Identifier.Axis.POV);
    }

    public C0272b(Controller controller) {
        this(0, controller);
    }

    public C0272b(int index, Controller controller) {
        this.f849b = index;
        this.f850c = controller;
        CRC32 crc32 = new CRC32();
        for (Component component : controller.getComponents()) {
            crc32.update(component.getIdentifier().getName().getBytes());
        }
        this.f851d = String.format("%08x", Long.valueOf(crc32.getValue()));
    }

    /* JADX INFO: renamed from: e */
    public int m1614e() {
        return this.f849b;
    }

    /* JADX INFO: renamed from: f */
    public Controller m1615f() {
        return this.f850c;
    }

    /* JADX INFO: renamed from: g */
    public String m1616g() {
        return this.f851d;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: a */
    public String mo1608a() {
        return this.f850c.getName();
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: b */
    public List<String> mo1609b() {
        LinkedList<String> commands = new LinkedList<>(f848a.keySet());
        commands.sort((v0, v1) -> {
            return v0.compareTo(v1);
        });
        return commands;
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: c */
    public boolean mo1610c() {
        return this.f850c.poll();
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: d */
    public void mo1611d() {
    }

    @Override // org.romstation.application.virtualcontroller.device.AbstractC0271a
    /* JADX INFO: renamed from: a */
    protected float mo1613a(String command) {
        try {
            Component component = this.f850c.getComponent(m1622c(command));
            if (component != null) {
                float data = component.getPollData();
                if (component.getIdentifier() instanceof Component.Identifier.Button) {
                    return data;
                }
                if (component.getIdentifier() instanceof Component.Identifier.Axis) {
                    if (component.getIdentifier() == Component.Identifier.Axis.POV) {
                        switch (command) {
                            case "POV.UP":
                                return (data == 0.125f || data == 0.25f || data == 0.375f) ? 1.0f : 0.0f;
                            case "POV.RIGHT":
                                return (data == 0.375f || data == 0.5f || data == 0.625f) ? 1.0f : 0.0f;
                            case "POV.DOWN":
                                return (data == 0.625f || data == 0.75f || data == 0.875f) ? 1.0f : 0.0f;
                            case "POV.LEFT":
                                return (data == 0.875f || data == 1.0f || data == 0.125f) ? 1.0f : 0.0f;
                            default:
                                return 0.0f;
                        }
                    }
                    if (command.endsWith("-")) {
                        if (data < 0.0f) {
                            return -data;
                        }
                        return 0.0f;
                    }
                    if (!command.endsWith("+")) {
                        return (1.0f + data) / 2.0f;
                    }
                    if (data > 0.0f) {
                        return data;
                    }
                    return 0.0f;
                }
                return 0.0f;
            }
            return 0.0f;
        } catch (IdentifierNotFoundException e) {
            return 0.0f;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        C0272b device = (C0272b) object;
        return this.f850c.getName().equals(device.f850c.getName()) && this.f849b == device.m1614e();
    }

    public int hashCode() {
        return this.f850c.hashCode();
    }

    /* JADX INFO: renamed from: j */
    private static Controller[] m1617j() {
        switch (C0004E.m10c()) {
            case WINDOWS:
                return new DirectAndRawInputEnvironmentPlugin().getControllers();
            case MAC_OS:
                return new OSXEnvironmentPlugin().getControllers();
            default:
                return new Controller[0];
        }
    }

    /* JADX INFO: renamed from: h */
    public static List<C0272b> m1618h() {
        LinkedList<C0272b> devices = new LinkedList<>();
        for (Controller controller : m1617j()) {
            if (controller.getType() == Controller.Type.GAMEPAD || controller.getType() == Controller.Type.STICK || controller.getType() == Controller.Type.FINGERSTICK || controller.getType() == Controller.Type.WHEEL) {
                long index = devices.stream().filter(device -> {
                    return device.mo1608a().equals(controller.getName());
                }).count();
                devices.add(new C0272b((int) index, controller));
            }
        }
        return devices;
    }

    /* JADX INFO: renamed from: i */
    public static List<C0272b> m1619i() {
        LinkedList<C0272b> devices = new LinkedList<>();
        for (Controller controller : m1617j()) {
            if (controller.getType() == Controller.Type.GAMEPAD || controller.getType() == Controller.Type.STICK || controller.getType() == Controller.Type.FINGERSTICK || controller.getType() == Controller.Type.WHEEL || controller.getType() == Controller.Type.UNKNOWN) {
                long index = devices.stream().filter(device -> {
                    return device.mo1608a().equals(controller.getName());
                }).count();
                devices.add(new C0272b((int) index, controller));
            }
        }
        return devices;
    }

    /* JADX INFO: renamed from: b */
    public static C0272b m1620b(String name) throws DeviceNotFoundException {
        return m1621a(0, name);
    }

    /* JADX INFO: renamed from: a */
    public static C0272b m1621a(int index, String name) throws DeviceNotFoundException {
        int deviceIndex = 0;
        for (Controller controller : m1617j()) {
            if (controller.getName().equals(name)) {
                if (deviceIndex == index) {
                    return new C0272b(0, controller);
                }
                deviceIndex++;
            }
        }
        throw new DeviceNotFoundException(String.format("device %s not found", name));
    }

    /* JADX INFO: renamed from: c */
    private static Component.Identifier m1622c(String command) throws IdentifierNotFoundException {
        if (f848a.containsKey(command)) {
            return f848a.get(command);
        }
        throw new IdentifierNotFoundException(String.format("unable to map command %s to identifier", command));
    }

    /* JADX INFO: renamed from: a */
    public static String m1623a(Component.Identifier identifier) throws IdentifierNotFoundException {
        return m1624a(identifier, 0.0f);
    }

    /* JADX INFO: renamed from: a */
    public static String m1624a(Component.Identifier identifier, float value) throws IdentifierNotFoundException {
        for (Map.Entry<String, Component.Identifier> entry : f848a.entrySet()) {
            if (entry.getValue() == identifier) {
                if (identifier instanceof Component.Identifier.Button) {
                    return entry.getKey();
                }
                if (!(identifier instanceof Component.Identifier.Axis)) {
                    break;
                }
                if (identifier == Component.Identifier.Axis.POV) {
                    switch ((int) (value * 8.0f)) {
                        case 2:
                            return "POV.UP";
                        case 4:
                            return "POV.RIGHT";
                        case 6:
                            return "POV.DOWN";
                        case 8:
                            return "POV.LEFT";
                    }
                }
                String code = entry.getKey().replaceFirst("[+-]$", "");
                if (value > 0.0f) {
                    return String.format("%s+", code);
                }
                if (value < 0.0f) {
                    return String.format("%s-", code);
                }
                return code;
            }
        }
        throw new IdentifierNotFoundException(String.format("unable to map identifier %s to command", identifier.getName()));
    }
}
