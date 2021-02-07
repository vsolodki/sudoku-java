package logic;

import domain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static domain.Game.GRID_BOUNDARY;

public class Generator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        while (!solvable){
            Utilities.copyArrayValues(solvedGame, solvableArray);
            int index = 0;
            while (index < 40){
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);
                if (solvableArray[xCoordinate] [yCoordinate] != 0){
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            Utilities.copyArrayValues(solvableArray, toBeSolved);
            solvable = Solver.puzzleIsSolvable(toBeSolved);

        }
        return solvableArray;
    }

    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for (int value = 1; value < GRID_BOUNDARY; value++){
            int allocations = 0;
            int interrupt = 0;
            List<Coordinates> allocationTracker = new ArrayList<>();
            int attempts = 0;
            while (allocations < GRID_BOUNDARY){
                if (interrupt > 200){
                    allocationTracker.forEach(coordinates -> newGrid[coordinates.getX()][coordinates.getY()] = 0);
                    interrupt = 0;
                    allocations = 0;
                    allocationTracker.clear();
                    attempts++;
                    if (attempts > 500){
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);
                if (newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value;
                    if (Logic.gameIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        allocationTracker.add(new Coordinates(xCoordinate,yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }

        }
    }
}
