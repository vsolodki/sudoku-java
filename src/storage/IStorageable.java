package storage;

import domain.Game;

import java.io.IOException;

public interface IStorageable {
    void updateGameData(Game game) throws IOException;
    Game getGameData() throws IOException;
}
