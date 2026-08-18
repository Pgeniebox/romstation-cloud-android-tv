package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.Emulator;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/EmulatorAdapter.class */
public class EmulatorAdapter extends AbstractC0228a<Emulator> {
    public EmulatorAdapter() {
        super(Emulator.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public Emulator create() {
        return new Emulator();
    }
}
