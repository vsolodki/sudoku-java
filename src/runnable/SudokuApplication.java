package runnable;

import domain.IUserContractable;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.LogicBuilder;
import ui.UserInterface;

import java.io.IOException;

public class SudokuApplication extends Application {
    private IUserContractable.View uiImpl;

    @Override
    public void start(Stage primaryStage) throws Exception{
        uiImpl = new UserInterface(primaryStage);
        try{
            LogicBuilder.build(uiImpl);
        } catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
