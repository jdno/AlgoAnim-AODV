package generators.network.aodv.guielements.Tables;

import algoanim.primitives.generators.Language;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;
import generators.network.aodv.animal.Statistics;

/**
 * The statistics table prints the most interesting information about the algorithm on
 * the canvas. It is updated during the animation to show how certain operations of AODV
 * influence the number of messages sent or the accesses to the routing tables of nodes.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class StatisticTable extends GUITable {

    /**
     * The numbre of sent messages
     */
    private TableCell messageCount;

    /**
     * The number of completed route discoveries
     */
    private TableCell routesDiscoveredCount;

    /**
     * The number of read accesses to routing tables
     */
    private TableCell routingTableReadsCount;

    /**
     * The number of write accesses to routing tables
     */
    private TableCell routingTableUpdatesCount;

    /**
     * The shared instance of Statistics
     */
    private Statistics stat;

    /**
     * Create a new statistics table. This automatically prints the table at the given position on the canvas.
     *
     * @param lang The language object
     * @param position The position where to paint the table (top-left corner)
     * @param title The title of the table
     * @param highlight The look & feel of the table
     */
    public StatisticTable(Language lang, Coordinates position, String title, RectProperties highlight) {
        super(lang,position,highlight);
        this.tableTitle = title;
        stat = Statistics.sharedInstance();
        cellHeight = 15;
        cellWidth = 150;
        columnTitles = new String[]{"Messages","RouteDiscoveries","RoutingTableReads","RoutingTableUpdates"};
        initContent();
    }

    /**
     * Initialize the content of the table
     */
    private void initContent() {
        drawTitles(2);

        messageCount = new TableCell(lang, Integer.toString(stat.getMessageCount()),
                moveToCell(0), cellHeight, cellWidth, highlight);
        routesDiscoveredCount = new TableCell(lang, Integer.toString(stat.getRoutesDiscoveredCount()),
                moveToCell(1), cellHeight, cellWidth, highlight);
        routingTableReadsCount = new TableCell(lang, Integer.toString(stat.getRoutingTableReadsCount()),
                moveToCell(2), cellHeight, cellWidth, highlight);
        routingTableUpdatesCount = new TableCell(lang, Integer.toString(stat.getRoutingTableUpdatesCount()),
                moveToCell(3), cellHeight, cellWidth, highlight);
    }

    /**
     * Update the statistics table
     */
    public void updateStatisticTable() {
        messageCount.updateText(Integer.toString(stat.getMessageCount()));
        routesDiscoveredCount.updateText(Integer.toString(stat.getRoutesDiscoveredCount()));
        routingTableReadsCount.updateText(Integer.toString(stat.getRoutingTableReadsCount()));
        routingTableUpdatesCount.updateText(Integer.toString(stat.getRoutingTableUpdatesCount()));
    }

}
