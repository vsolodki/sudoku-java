package logic;

import domain.Game;

public class Utilities {
    public static void copyArrayValues(int[][] old, int[][] newArr){
        for (int xIndex = 0; xIndex < Game.GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex < Game.GRID_BOUNDARY; yIndex++){
                newArr[xIndex][yIndex] = old[xIndex][yIndex];
            }
        }
    }
    public static int[][] copyToNew(int[][] old) {
        int[][] newArr = new int[Game.GRID_BOUNDARY][Game.GRID_BOUNDARY];
        for (int xIndex = 0; xIndex < Game.GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex < Game.GRID_BOUNDARY; yIndex++){
                newArr[xIndex][yIndex] = old[xIndex][yIndex];
            }
        }
        return newArr;
    }
}
