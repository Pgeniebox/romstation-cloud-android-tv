package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.GameProfile;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/GameProfileAdapter.class */
public class GameProfileAdapter extends AbstractC0228a<GameProfile> {
    public GameProfileAdapter() {
        super(GameProfile.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public GameProfile create() {
        return new GameProfile();
    }
}
