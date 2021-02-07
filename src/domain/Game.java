package domain;

import constants.State;
import logic.Utilities;

import java.io.Serializable;

public class Game implements Serializable {
    private final State gameState;
    private final int[][] gridState;
    public static final int GRID_BOUNDARY = 9;

    public Game(State gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public State getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return Utilities.copyToNew(gridState);
    }
}
