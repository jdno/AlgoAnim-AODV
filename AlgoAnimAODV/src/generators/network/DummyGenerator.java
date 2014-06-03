	package generators.network;

	import generators.framework.Generator;
import generators.framework.GeneratorBundle;
import generators.network.aodv.AODVRoutingGenerator;
import generators.network.dns.DNSQueryGenerator;
import generators.network.graph.BellmanFordGenerator;
import generators.network.graph.DijkstraGenerator;
import generators.network.graph.FloydWarshallGenerator;
import generators.network.graph.KruskalGenerator;
import generators.network.graph.PrimGenerator;
import generators.network.routing.DistanceVectorRouting;
import generators.network.routing.VectorRoutingGenerator;
import generators.network.routing.impl.dvr.DistanceVectorFactory;
import generators.network.routing.impl.pvr.PathVectorFactory;

import java.util.Locale;
import java.util.Vector;

	/**
	 * @author Marc Werner <mwerner@rbg.informatik.tu-darmstadt.de>
	 * 
	 *         Provides all specific generators for 'networking'.
	 * 
	 */
	public class DummyGenerator implements GeneratorBundle {

	  public Vector<Generator> getGenerators() {
	    Vector<Generator> generators = new Vector<Generator>(165, 45);
	    /*
	     * Graph Algorithms
	     */
	    generators.add(new BellmanFordGenerator(Locale.GERMANY));
	    generators.add(new BellmanFordGenerator(Locale.US));

	    generators.add(new DijkstraGenerator(Locale.GERMANY));
	    generators.add(new DijkstraGenerator(Locale.US));

	    generators.add(new FloydWarshallGenerator(Locale.GERMANY));
	    generators.add(new FloydWarshallGenerator(Locale.US));

	    generators.add(new KruskalGenerator(Locale.GERMANY));
	    generators.add(new KruskalGenerator(Locale.US));

	    generators.add(new PrimGenerator(Locale.GERMANY));
	    generators.add(new PrimGenerator(Locale.US));

	    /*
	     * Basic Network Algorithms
	     */
	    generators.add(new DNSQueryGenerator(Locale.GERMANY));
	    generators.add(new DNSQueryGenerator(Locale.US));

	    /*
	     * Routing Algorithms
	     */
	    generators.add(new VectorRoutingGenerator(Locale.GERMANY,
	        new DistanceVectorFactory()));
	    generators.add(new VectorRoutingGenerator(Locale.US,
	        new DistanceVectorFactory()));

	    generators.add(new VectorRoutingGenerator(Locale.GERMANY,
	        new PathVectorFactory()));
	    generators.add(new VectorRoutingGenerator(Locale.US,
	        new PathVectorFactory()));

	    generators.add(new AODVRoutingGenerator());

	    // TODO "under probation"
	    generators.add(new DistanceVectorRouting());
	    
	    return generators;
	  }

	}



