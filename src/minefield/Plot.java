package minefield;

public class Plot {
    private boolean isVisited;
    private boolean hasBomb;
    private int bombsAdjacent;
    private boolean goal;

    public Plot(){
        isVisited = false;
        hasBomb = false;
        bombsAdjacent = 0;
        goal = false;
    }

    public int getAdjacentBombs(){
        return bombsAdjacent;
    }

    public boolean hasBomb(){
        return hasBomb;
    }

    public boolean isVisited(){
        return isVisited;
    }

    public boolean isGoal(){
        return goal;
    }

    public void addAdjacentBomb(){
        bombsAdjacent++;
    }

    public void setHasBomb(){
        hasBomb = true;
    }

    public void setIsVisited(){
        isVisited = true;
    }

    public void setIsGoal(){
        goal = true;
    }

}
