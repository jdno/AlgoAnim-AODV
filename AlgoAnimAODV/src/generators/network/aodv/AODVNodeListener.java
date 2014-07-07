package generators.network.aodv;

import translator.Translator;

/**
 * Interface to use whenever an AODVNode needs to update the GUI
 */
public interface AODVNodeListener {

    /**
     * Return the current instance of Translator
     * @return The current instance of Translator
     */
    public Translator getTranslator();

    /**
     * Highlights the given AODVNode on the screen
     * @param node
     *          Node to be highlighted
     */
    public void highlightNode(AODVNode node);

    /**
     * Highlights the edge from the given startnode to the given endnode
     * @param startNode
     *          node from which the edge starts
     * @param endNode
     *          node to which the edge leads
     */
    public void highlightEgde(AODVNode startNode,AODVNode endNode);

    /**
     * Tell the language object to insert a new step.
     */
    public void nextStep();

    /**
     * Tell the language object to insert a new step, and set the given label.
     *
     * <b>CAUTION</b> The label will NOT be translated!
     *
     * @param label The label for the current step
     */
    public void nextStep(String label);

    /**
     * Reset all highlights on the graph.
     */
    public void unhighlightAll();

    /**
     * Updates the InfoTable for the given AODVNode
     * @param node
     *          Node for which the table needs to be updated
     */
    public void updateInfoTable(AODVNode node);

    /**
     * Updates the InfoBox with the given message.
     *
     * <b>CAUTION</b> The message will NOT be translated!
     *
     * @param message The message to print
     */
    public void updateInfoText(String message);
}
