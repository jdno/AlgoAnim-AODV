package generators.network.aodv;

/**
 * Created by sascha on 08.06.14.
 */
public interface AODVNodeListener {

    public void highlightNode(AODVNode node);
    public void highlightEgde(AODVNode startNode,AODVNode endNode);
    public void updateInfoTable(AODVNode node);
}
