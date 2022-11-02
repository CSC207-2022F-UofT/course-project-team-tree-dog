package join_public_lobby;

import enumerations.ResCode;

public class JplOutputDataJoinedPool {

    private final ResCode resultCode;
    private final int playerId;

    public JplOutputDataJoinedPool(ResCode resultCode, int playerId) {
        this.resultCode = resultCode;
        this.playerId = playerId;
    }

    public ResCode getResultCode() {
        return resultCode;
    }

    public int getPlayerId() {
        return playerId;
    }
}