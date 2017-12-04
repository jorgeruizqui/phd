package es.jor.phd.dblp.exporter.excel;

import es.jor.phd.dblp.parser.entity.DBLPEntity;
import es.jor.phd.dblp.parser.util.DBLPEntityGameDesignExecutor;

public class LaunchDblpGameDesingExcel {

	public static void main(String[] args) {

		DBLPEntity entity = DBLPEntityGameDesignExecutor.launch();
		ExcelFileExporter.writeDblpEntityToFile(entity, "dblp_game_design.xlsx");
	}

}
