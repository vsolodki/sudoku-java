package storage;

import domain.Game;

import java.io.*;

public class LocalStorage implements IStorageable {

    private static File GAME_DATA = new File(System.getProperty("user.home"),
            "gamedata.txt");

    @Override
    public void updateGameData(Game game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e){
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public Game getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try{
            Game state = (Game) objectInputStream.readObject();
            objectInputStream.close();
            return state;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            throw new IOException("File not found");
        }
    }
}
