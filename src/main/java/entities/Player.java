package entities;


/**
 * A Player.
 */
public class Player {

    private String displayName;

    private final String playerId;

    /**
     * Constructor for the Player.
     * @param newDisplayName the display name of the new Player.
     * @param newPlayerId the ID given to the new Player.
     */
    public Player(String newDisplayName, String newPlayerId) {
        this.displayName = newDisplayName;
        this.playerId = newPlayerId;
    }

    /**
     * Sets a new display name for the Player.
     * @param newDisplayName the new display name.
     */
    public void setDisplayName(String newDisplayName) {this.displayName = newDisplayName;}

    /**
     * Gets the display name of the Player.
     * @return display name.
     */
    public String getDisplayName() {return this.displayName;}

    /**
     * Gets the ID of the Player.
     * @return ID.
     */
    public String getPlayerId() {return this.playerId;}

    /**
     * Return if two players have the same ID. Display name doesn't matter
     * @param obj Another object
     * @return false if obj is not a player. Otherwise, return if the player IDs are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).getPlayerId().equals(this.playerId);
        } return false;
    }
}