package adapters.controllers;

import usecases.suggest_title.*;

/**
 * The controller for the Suggested Title use case. Builds input data from user input and uses it to call the method
 * to suggest title on the given input boundary.
 */
public class StController {
    StInputBoundary st;

    /**
     * Constructor for StController.
     * @param st    the input boundary for the controller
     */
    public StController(StInputBoundary st) {
        this.st = st;
    }

    /**
     * Method for the controller to:
     * 1. Build the input data for the Suggest Title use case
     * 2. Call the method st.suggestTitle() to begin the use case
     * @param uniqueRequestId   the unique ID corresponding to this particular request to add a title
     * @param storyId           the ID for the story for which the title is suggested
     * @param suggestedTitle    the title that is suggested for the story
     */
    public void suggestTitle(String uniqueRequestId, int storyId, String suggestedTitle){
        StInputData inp = new StInputData(uniqueRequestId, suggestedTitle, storyId);
        st.suggestTitle(inp);
    }
}