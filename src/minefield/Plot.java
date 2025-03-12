package minefield;

public class Plot {
    private boolean isVisited;
    private boolean hasBomb;
    private int bombsAdjacent;

    public Plot(){
        isVisited = false;
        hasBomb = false;
        bombsAdjacent = 0;
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

    public void setAdjacentBombs(int newAdjacentBombs){
        bombsAdjacent = newAdjacentBombs;
    }

    public void setHasBomb(){
        this.hasBomb = !hasBomb;
    }

    public void setIsVisited(){
        this.isVisited = !isVisited;
    }

}
