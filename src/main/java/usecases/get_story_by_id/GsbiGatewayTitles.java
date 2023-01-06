package usecases.get_story_by_id;

import org.jetbrains.annotations.NotNull;
import usecases.RepoRes;

public interface GsbiGatewayTitles {
    /**
     * @param storyId id of the story for which to get the title
     * @return the string of the most upvoted suggested title for this story, or a fail
     * code if something is wrong
     */
    @NotNull
    RepoRes<String> getMostUpvotedStoryTitle(int storyId);
}