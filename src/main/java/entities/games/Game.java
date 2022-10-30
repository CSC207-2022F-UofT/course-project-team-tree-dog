package entities.games;

import entities.Player;
import entities.Story;
import entities.ValidityChecker;
import entities.WordFactory;

import java.util.Collection;
import java.util.Timer;

/**
 * An abstract game
 * Every type of game extends this class
 */
public abstract class Game {

    private final Story story;

    private final Timer gameTimer;

    private boolean timerStopped;

    private final int secondsPerTurn;
    protected int secondsLeftInCurrentTurn;

    /**
     * Constructor for a Game
     * @param secondsPerTurn The amount of seconds for each turn
     * @param v The validity checker (to check if a word is valid)
     */
    public Game(int secondsPerTurn, ValidityChecker v) {
        this.story = new Story(new WordFactory(v));
        this.secondsPerTurn = secondsPerTurn;
        this.gameTimer = new Timer();
        this.timerStopped = false;
    }

    /**
     * @return The story as it has been typed in this game instance
     */
    public Story getStory() {return story;}

    /**
     * @return Returns how many seconds is given for every player per turn (therefore, every player gets the same
     * amount of time every turn)
     */
    public int getSecondsPerTurn() {return secondsPerTurn;}

    /**
     * @return Returns how many seconds are left for the current turn
     */
    public int getSecondsLeftInCurrentTurn() {return secondsLeftInCurrentTurn;}

    /**
     * Sets the seconds left in the current turn
     * @param newSeconds The amount of seconds to set the current turn for
     */
    public void setSecondsLeftInCurrentTurn(int newSeconds) {
        this.secondsLeftInCurrentTurn = newSeconds;
    }


    /** This method adds the initial players to the game by looping and calling addPlayer
     */
    public void addAllPlayers(Collection<Player> players) {
        for(Player player : players) {
            this.addPlayer(player);
        }
    }

    /**
     * @return Returns the game timer which is used by the use case layer
     */
    public Timer getGameTimer() {return this.gameTimer;}

    /**
     * @param timerStopped Sets timerStopped boolean instance variable to the given value
     */
    public void setTimerStopped(boolean timerStopped) {this.timerStopped = timerStopped;}

    /**
     * @return Returns whether timer has been stopped
     */
    public boolean isTimerStopped() {return timerStopped;}

    /**
     * @return Returns all the present players in the game
     */
    public abstract Collection<Player> getPlayers();

    /**
     * @return Returns whether the game is over
     */
    public abstract boolean isGameOver();

    /**
     * Additional actions that can be done by the game every time the timer is updated
     */
    protected abstract void onTimerUpdate();

    /**
     * Returns the player by its id
     */
    public abstract Player getPlayerById(String PlayerId);
    /**
     * Removes the player specified from this GameRegular instance
     * @param playerToRemove The Player to be removed
     * @return if the player was successfully removed
     */
    public abstract boolean removePlayer(Player playerToRemove);

    /**
     * Adds new player to the game
     */
    public abstract boolean addPlayer(Player playerToAdd);

    /**
     * Switches this game's turn
     */
    public abstract boolean switchTurn();

    /**
     * Modifies the turn time for the move
     */
    public abstract void modifyTurnTime();

    /**
     * Returns the player whose turn it is
     */
    public abstract Player getCurrentTurnPlayer();

}
