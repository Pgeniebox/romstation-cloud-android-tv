package org.romstation.application.api;

import com.teamdev.jxbrowser.js.JsAccessible;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.romstation.application.task.C0258z;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/api/System.class */
@JsAccessible
public class System {
    public static Map<String, Function<C0258z, String>> parseEvent = new HashMap();
    public static final Set<Consumer<C0258z>> onInit = new HashSet();

    public void registerCommand(String param, Function<C0258z, String> event) {
        parseEvent.put(param, event);
    }

    public void onInit(Consumer<C0258z> consumer) {
        onInit.add(consumer);
    }
}
