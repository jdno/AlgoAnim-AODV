package generators.network.aodv;

/**
 * Interface to use whenever an AODVNode needs to update the GUI
 */
public interface AODVNodeListener {

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
     * Updates the InfoTable for the given AODVNode
     * @param node
     *          Node for which the table needs to be updated
     */
    public void updateInfoTable(AODVNode node);

    /**
     * Updates the InfoBox with the text for the given message identifier.
     * The message identifier must match a string in the localization file.
     *
     * @param messageId The identifier of the message
     */
    public void updateInfoText(String messageId);
}
