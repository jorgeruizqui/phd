package es.jor.phd.dblp.gephi.generator.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;

public class GexpFileExporter {

	private static final String OUTPUT_FOLDER = "output";

	/**
	 * Gexp exporter to a file
	 * @param gexf Gexf graph information
	 * @param gexpFileName Gexf File Name
	 */
	public static void writeGexpToFile(Gexf gexf, String gexfFileName) {
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File(OUTPUT_FOLDER + System.getProperty("file.separator") + gexfFileName);
		Writer out;
		try {
			out = new FileWriter(f, false);
			graphWriter.writeToStream(gexf, out, "UTF-8");
			System.out.println(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
