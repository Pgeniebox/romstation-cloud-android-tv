package org.romstation.application.script.api.database.adapter;

import org.romstation.application.database.entity.Game;

/* JADX INFO: loaded from: RomStation.jar:org/romstation/application/script/api/database/adapter/GameAdapter.class */
public class GameAdapter extends AbstractC0228a<Game> {
    public GameAdapter() {
        super(Game.class);
    }

    @Override // org.romstation.application.script.api.database.adapter.AbstractC0228a
    public Game create() {
        return new Game();
    }
}
