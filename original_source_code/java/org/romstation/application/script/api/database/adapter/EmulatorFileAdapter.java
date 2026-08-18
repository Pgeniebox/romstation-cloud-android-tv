package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.EmulatorFile;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/EmulatorFileAdapter.class */
public class EmulatorFileAdapter extends AbstractC0228a<EmulatorFile> {
    public EmulatorFileAdapter() {
        super(EmulatorFile.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public EmulatorFile create() {
        return new EmulatorFile();
    }
}
