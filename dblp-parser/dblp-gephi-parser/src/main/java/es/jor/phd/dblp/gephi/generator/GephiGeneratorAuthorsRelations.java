package es.jor.phd.dblp.gephi.generator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.jor.phd.dblp.gephi.generator.export.GexpFileExporter;
import es.jor.phd.dblp.parser.entity.DBLPEntity;
import es.jor.phd.dblp.parser.entity.Hit;
import es.jor.phd.dblp.parser.util.DBLPEntityGameDesignExecutor;
import it.uniroma1.dis.wsngroup.gexf4j.core.EdgeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Mode;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.viz.NodeShape;

public class GephiGeneratorAuthorsRelations {


	private static final String FILE_NAME = "dblp_authors_relation.gexf";
	private static final float DEFAULT_NODE_SIZE = 100;
	private static final float FACTOR_NODE_SIZE = 10f;

	private static Map<String, Integer> appearsIn = new HashMap<>();
	private static Map<String, List<String>> authorRelatedWith = new HashMap<>();
	private static Map<String, Node> authorNodes = new HashMap<>();

	private GephiGeneratorAuthorsRelations() {
	}

	public static void launch() {

		// Preparing Gexf graphics properties
		Gexf gexf = new GexfImpl();
		Calendar date = Calendar.getInstance();
		gexf.getMetadata().setLastModified(date.getTime()).setCreator("Jorge Ruiz").setDescription("Game Design Authors Relations");
		gexf.setVisualization(true);
		Graph graph = gexf.getGraph();
		graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);
		AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
		graph.getAttributeLists().add(attrList);
		Attribute attName = attrList.createAttribute("0", AttributeType.STRING, "Name");

		// Just to know total number of hits
		DBLPEntity entityTotal = DBLPEntityGameDesignExecutor.launch();
		List<Hit> listOfHits = entityTotal.getResult().getHits().getHit();

		for (Hit hit : listOfHits) {
			if (hit.getInfo().getAuthors() == null) {
				//generateNode(graph, attName, NO_AUTHOR);
			} else {
				for (String author : hit.getInfo().getAuthors().getName()) {
					generateNode(graph, attName, author);

					// Generate a relation with the other authors
					List<String> others = hit.getInfo().getAuthors().getName().stream().filter(otherAuthor -> !author.equals(otherAuthor)).collect(Collectors.toList());
					Node origin = authorNodes.get(author);
					for (String otherAuthor : others) {
						Node destination = authorNodes.get(otherAuthor);
						if (destination == null) destination = generateNode(graph, attName, otherAuthor);
						if (!origin.hasEdgeTo(otherAuthor)) {
							origin.connectTo(destination);
						}
					}
				}
			}
		}
		GexpFileExporter.writeGexpToFile(gexf, FILE_NAME);
	}

	private static Node generateNode(Graph graph, Attribute attName, String author) {
		Node gephiAuthor = authorNodes.get(author);
		if (gephiAuthor == null) {
			gephiAuthor = graph.createNode(author);
			gephiAuthor.getShapeEntity().setNodeShape(NodeShape.DIAMOND);
			gephiAuthor.setLabel(author).getAttributeValues().addValue(attName, author);
			authorNodes.put(author, gephiAuthor);
		}
		if ((appearsIn.get(author) != null)) {
			appearsIn.put(author, appearsIn.get(author) + 1);
			gephiAuthor.setSize(DEFAULT_NODE_SIZE + (appearsIn.get(author) * FACTOR_NODE_SIZE));
		} else {
			gephiAuthor.setSize(DEFAULT_NODE_SIZE);
			appearsIn.put(author, 1);
		}
		return gephiAuthor;
	}

}
