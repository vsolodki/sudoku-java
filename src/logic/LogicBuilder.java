package logic;

import domain.Game;
import storage.IStorageable;
import domain.IUserContractable;
import storage.LocalStorage;

import java.io.IOException;

public class LogicBuilder {
    public static void build(IUserContractable.View userInterface) throws IOException {
        Game initialState;
        IStorageable storage = new LocalStorage();
        try{
            initialState = storage.getGameData();
        } catch (IOException e){
            initialState = Logic.getNewGame();
            storage.updateGameData(initialState);
        }
        IUserContractable.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
