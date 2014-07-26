package generators.network.aodv.animal;

/**
 * Class that collects various statistics about the performance
 * of the AODV routing algorithm. This class implements the
 * singleton pattern to act as the one collector for ALL stats.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class Statistics {

    /**
     * Shared instance of Statistics
     */
    private static Statistics sharedInstance = null;

    /**
     * Number of messages sent
     */
    private int messageCount = 0;

    /**
     * Number of routes discovered
     */
    private int routesDiscoveredCount = 0;

    /**
     * Number of read accesses and comparisons in the routing tables
     */
    private int routingTableReadsCount = 0;

    /**
     * Number of write accesses in the routing tables
     */
    private int routingTableUpdatesCount = 0;

    /**
     * Statistics implements the Singleton pattern. This method returns
     * the shared instance of Statistics.
     *
     * @return The shared instance of Statistics
     */
    public static Statistics sharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new Statistics();
        }

        return sharedInstance;
    }

    /**
     * Private constructor for Statistics. Access from outside this class
     * should always be done using sharedInstance().
     */
    private Statistics() {
    }

    /**
     * Increment the number of sent messages by one.
     */
    public void messageSent() {
        messageCount++;
    }

    /**
     * Increment the number of routes that were successfully discovered.
     */
    public void routeDiscovered() {
        routesDiscoveredCount++;
    }

    /**
     * Increment the number of routing table read accesses.
     */
    public void routingTableRead() {
        routingTableReadsCount++;
    }

    /**
     * Increment the number of routing table write accesses.
     */
    public void routingTableUpdated() {
        routingTableUpdatesCount++;
    }

    /**
     * @return The message count
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * @return The routes discovered count
     */
    public int getRoutesDiscoveredCount() {
        return routesDiscoveredCount;
    }

    /**
     * @return The routing table reads count
     */
    public int getRoutingTableReadsCount() {
        return routingTableReadsCount;
    }

    /**
     * @return The routing table update count
     */
    public int getRoutingTableUpdatesCount() {
        return routingTableUpdatesCount;
    }
}
