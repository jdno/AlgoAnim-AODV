package generators.network.aodv.guielements.Tables;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.animal.Statistics;

/**
 * Created by sascha on 17.06.14.
 */
public class StatisticTable extends GUITable {

    private TableCell messageCount;
    private TableCell routesDiscoveredCount;
    private TableCell routingTableReadsCount;
    private TableCell routingTableUpdatesCount;

    private Statistics stat;

    public StatisticTable(Language lang, Coordinates position){
        super(lang,position);
        stat = Statistics.sharedInstance();
        cellHeight = 15;
        cellWidth = 150;

        titles = new String[]{"Messages","RouteDiscoveries","RoutingTableReads","RoutingTableUpdates"};
        initContent();
    }


    private void initContent(){
        drawTitles(2);

        messageCount = new TableCell(lang, Integer.toString(stat.getMessageCount()), moveToCell(0),cellHeight,cellWidth);
        routesDiscoveredCount = new TableCell(lang, Integer.toString(stat.getRoutesDiscoveredCount()), moveToCell(1),cellHeight,cellWidth);
        routingTableReadsCount = new TableCell(lang, Integer.toString(stat.getRoutingTableReadsCount()), moveToCell(2),cellHeight,cellWidth);
        routingTableUpdatesCount = new TableCell(lang, Integer.toString(stat.getRoutingTableUpdatesCount()), moveToCell(3),cellHeight,cellWidth);
    }

    public void updateStatisticTable(){
        messageCount.updateText(Integer.toString(stat.getMessageCount()));
        routesDiscoveredCount.updateText(Integer.toString(stat.getRoutesDiscoveredCount()));
        routingTableReadsCount.updateText(Integer.toString(stat.getRoutingTableReadsCount()));
        routingTableUpdatesCount.updateText(Integer.toString(stat.getRoutingTableUpdatesCount()));
    }

}
