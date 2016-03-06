package state;
import java.util.HashMap;

/**
 * Created by Emily on 3/5/2016.
 */
public class State {

    private Integer currentPlayer = 1;
    private HashMap<Integer, Integer> scoresMap;

    public State() {
        scoresMap = new HashMap<Integer, Integer>();
        scoresMap.put(1,3);
        scoresMap.put(2,5);
    }

    public HashMap<Integer,Integer> getScores() {
        return scoresMap;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }
}
