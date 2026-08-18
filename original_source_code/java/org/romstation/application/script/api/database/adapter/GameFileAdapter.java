package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.GameFile;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/GameFileAdapter.class */
public class GameFileAdapter extends AbstractC0228a<GameFile> {
    public GameFileAdapter() {
        super(GameFile.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public GameFile create() {
        return new GameFile();
    }
}
