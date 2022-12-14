import adapters.controllers.*;
import adapters.presenters.*;
import adapters.view_models.PdViewModel;
import adapters.view_models.PgeViewModel;
import entities.LobbyManager;
import entities.PlayerFactory;
import entities.comment_checkers.CommentChecker;
import entities.comment_checkers.CommentCheckerBasic;
import entities.statistics.AverageTurnDurationPlayerStatistic;
import entities.statistics.LettersUsedByPlayerStatistic;
import entities.statistics.PerPlayerIntStatistic;
import entities.statistics.WordCountPlayerStatistic;
import entities.suggested_title_checkers.SuggestedTitleChecker;
import entities.suggested_title_checkers.SuggestedTitleCheckerBasic;
import entities.display_name_checkers.DisplayNameChecker;
import entities.display_name_checkers.DisplayNameCheckerBasic;
import entities.games.GameFactory;
import entities.games.GameFactoryRegular;
import frameworks_drivers.repository.in_memory.InMemoryCommentsRepo;
import frameworks_drivers.repository.in_memory.InMemoryStoryRepo;
import frameworks_drivers.repository.in_memory.InMemoryTitlesRepo;
import usecases.comment_as_guest.CagInteractor;
import usecases.ThreadRegister;
import usecases.disconnecting.DcInteractor;
import usecases.get_all_titles.GatInteractor;
import usecases.get_latest_stories.GlsInteractor;
import usecases.get_most_liked_stories.GmlsInteractor;
import usecases.get_story_comments.GscInteractor;
import usecases.join_public_lobby.JplInteractor;
import usecases.like_story.LsInteractor;
import usecases.pull_data.PdInteractor;
import usecases.pull_game_ended.PgeInteractor;
import usecases.shutdown_server.SsInteractor;
import usecases.sort_players.SpInteractor;
import usecases.submit_word.SwInteractor;
import usecases.suggest_title.StInteractor;
import usecases.upvote_title.UtInteractor;

/**
 * Orchestrator. Contains only a main method which boots up
 * the server.
 */
public class Main {

    /**
     * Builds the clean arch structure and initializes it.
     * @param args Command line arguments (currently none necessary)
     */
    public static void main (String[] args) {
        PdViewModel pdViewM = new PdViewModel();
        PgeViewModel pgeViewM = new PgeViewModel();

        ThreadRegister register = new ThreadRegister();

        PdPresenter pdPresenter = new PdPresenter(pdViewM);
        PgePresenter pgePresenter = new PgePresenter(pgeViewM);

        // Create desired checkers for injection
        CommentChecker commentChecker = new CommentCheckerBasic();
        DisplayNameChecker displayChecker = new DisplayNameCheckerBasic();
        SuggestedTitleChecker titleChecker = new SuggestedTitleCheckerBasic();

        // Create desired Story, Titles, and Comments repos
        InMemoryTitlesRepo titlesRepo = new InMemoryTitlesRepo();
        InMemoryCommentsRepo commentsRepo = new InMemoryCommentsRepo();
        InMemoryStoryRepo storyRepo = new InMemoryStoryRepo();

        // Create desired per-player statistics for injection
        PerPlayerIntStatistic[] statistics = {
                new AverageTurnDurationPlayerStatistic(), new WordCountPlayerStatistic(),
                new LettersUsedByPlayerStatistic()
        };

        // Factory which accepts ALL display names with at least 3 characters (temporary)
        PlayerFactory playerFac = new PlayerFactory(displayChecker);
        GameFactory gameFac = new GameFactoryRegular(statistics);

        // Inject particular factories into LobbyManager
        LobbyManager manager = new LobbyManager(playerFac, gameFac);

        // Start up sort players
        PdInteractor pd = new PdInteractor(pdPresenter);
        PgeInteractor pge = new PgeInteractor(pgePresenter, storyRepo);
        SpInteractor sp = new SpInteractor(manager, pge, pd);
        sp.startTimer();

        // Use cases called by users

        CagInteractor cag = new CagInteractor(commentsRepo, commentChecker, displayChecker, register);
        DcInteractor dc = new DcInteractor(manager, register);
        GlsInteractor gls = new GlsInteractor(storyRepo, register);
        GmlsInteractor gmls = new GmlsInteractor(storyRepo, register);
        GscInteractor gsc = new GscInteractor(commentsRepo, register);
        GatInteractor gat = new GatInteractor(titlesRepo, register);
        JplInteractor jpl = new JplInteractor(manager, register);
        LsInteractor ls = new LsInteractor(storyRepo, register);
        SsInteractor ss = new SsInteractor(register);
        SwInteractor sw = new SwInteractor(manager, register);
        StInteractor st = new StInteractor(titlesRepo, titleChecker, register);
        UtInteractor ut = new UtInteractor(titlesRepo, register);

        // Controllers
        CagController cagController = new CagController(cag);
        DcController dcController = new DcController(dc);
        GatController gatController = new GatController(gat);
        GlsController glsController = new GlsController(gls);
        GmlsController gmlsController = new GmlsController(gmls);
        GscController gscController = new GscController(gsc);
        JplController jplController = new JplController(jpl);
        LsController lsController = new LsController(ls);
        SsController ssController = new SsController(ss);
        SwController swController = new SwController(sw);
        StController stController = new StController(st);
        UtController utController = new UtController(ut);

        // TODO: Setup and run the view
    }
}
