package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.EmulatorProfile;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/EmulatorProfileAdapter.class */
public class EmulatorProfileAdapter extends AbstractC0228a<EmulatorProfile> {
    public EmulatorProfileAdapter() {
        super(EmulatorProfile.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public EmulatorProfile create() {
        return new EmulatorProfile();
    }
}
