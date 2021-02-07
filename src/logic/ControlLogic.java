package logic;

import constants.Messages;
import constants.State;
import domain.Game;
import storage.IStorageable;
import domain.IUserContractable;

import java.io.IOException;

public class ControlLogic implements IUserContractable.EventListener {

    private IStorageable storage;
    private IUserContractable.View view;

    public ControlLogic(IStorageable storage, IUserContractable.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onInput(int x, int y, int input) {
        try{
            Game gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;
            gameData = new Game(Logic.checkForCompletion(newGridState), newGridState);
            storage.updateGameData(gameData);
            view.updateSquare(x,y,input);
            if (gameData.getGameState() == State.COMPLETE){
                view.showDialog(Messages.COMPLETE);
            }
        }catch (IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onClick() {
        try{
            storage.updateGameData(Logic.getNewGame());
            view.updateBoard(storage.getGameData());
        }catch (IOException e){
            view.showError(Messages.ERROR);
        }
    }
}
