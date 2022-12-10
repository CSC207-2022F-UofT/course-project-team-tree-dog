package adapters.presenters;

import adapters.display_data.not_ended_display_data.GameDisplayDataBuilder;
import adapters.view_models.PdViewModel;
import usecases.GameDTO;
import usecases.PlayerDTO;
import usecases.pull_data.PdOutputBoundary;
import usecases.pull_data.PdOutputData;

public class PdPresenter implements PdOutputBoundary {

    private final PdViewModel viewM;

    /**
     * @param viewM Instance of the view model to write to
     */
    public PdPresenter (PdViewModel viewM) { this.viewM = viewM; }

    /**
     * Update the view model's state of the current game
     * @param d PdOutputData
     */
    @Override
    public void updateGameInfo(PdOutputData d) {
        GameDTO g = d.getGameInfo();

        // Create builder
        GameDisplayDataBuilder builder = new GameDisplayDataBuilder();

        // Adds players
        for (PlayerDTO p : g.getPlayers()) {
            builder.addPlayer(p.getPlayerId(), p.getDisplayName(),
                    p.getPlayerId().equals(g.getCurrentTurnPlayerId()));
        }

        // Adds other data
        builder.setSecondsLeftInTurn(g.getSecondsLeftCurrentTurn()).setStoryString(g.getStory());

        // Sets state in view model
        viewM.setCurrentGameState(builder.build());
    }
}
