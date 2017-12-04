/**
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ccottap
 *
 */
public class FilterDBLP {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int counter = readText("dblp.xml");
		System.out.println(counter + " articles found");
		
		ArrayList<String> articles = ReadArticles("article-ids.txt");
		for (String article: articles) {
			System.out.println(article);
		}
		//FilterArticles("dblp.xml", "article-ids.txt");
		Map<String, Integer> a = CountWords(articles);
		System.out.println("Filtrando");
		Map<String, String> synonyms = Filter(a, 2);
		ArrayList<String> keys = new ArrayList<String>(synonyms.keySet());
//		for (String key: keys) {
//			System.out.println(key + ": " + synonyms.get(key));
//		}
		Map<String, Tuple<Integer,Integer>> b = new HashMap<String, Tuple<Integer,Integer>>();
		Integer i = AssignIDs(a, b);
		System.out.println("Palabras resultantes (ID, frecuencia): " + i);
		System.out.println("------------------------------------ ");

		keys = new ArrayList<String>(b.keySet());
		for (String key: keys) {
			System.out.println(key + ": " + b.get(key));
		}
		Integer[][] graph = new Integer[i][i];
		for (int j=0; j<i; j++)
			for (int k=0; k<i; k++)
				graph[j][k] = 0;
		Process(b, synonyms, graph, articles);
		Write(b,graph,2);
		WriteJSON(b,graph,2);
	}
	
	
	/*
	 * Generates an xml file with the selected articles.
	 */
	private static void FilterArticles(String filedblp, String fileclaves) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(fileclaves));
        ArrayList<String> L = new ArrayList<String>();
	    String sent;
		while(scan.hasNextLine()){
			sent = scan.nextLine(); //clave
			L.add(sent);
			sent = scan.nextLine(); // title
		}
		for (String key: L) {
			System.out.println(key);
		}
        scan.close();
        PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("articles.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        scan = new Scanner(new File(filedblp));
		while(scan.hasNextLine()){
			sent = scan.nextLine(); 
			if (sent.indexOf(" key=")>-1) {
//				System.out.println(sent);
				String clave = sent.toUpperCase().substring(sent.indexOf("key=")+5, sent.length()-2);
				if (L.contains(clave)) {
					String type = sent.substring(1, sent.indexOf(" "));
					System.out.println(type);
					System.out.println(sent);
					out.println(sent);
					do {
						sent = scan.nextLine();
						out.println(sent);
					} while (sent.indexOf(type)<0);
				}
			}
		}
		out.close();
        scan.close();
	}

	private static ArrayList<String> ReadArticles(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(filename));
        ArrayList<String> L = new ArrayList<String>();
	    String sent;
		while(scan.hasNextLine()){
			sent = scan.nextLine(); //clave
			sent = scan.nextLine();
			L.add(sent);
		}
        scan.close();
        return L;
	}

	private static void Process(Map<String, Tuple<Integer, Integer>> a, Map<String, String> syn, Integer[][] graph, ArrayList<String> articles) throws FileNotFoundException {
	    String sent;
	    Tuple<Integer,Integer> val;
		for (String article: articles){
			sent = article.toLowerCase().replaceAll("[^a-z ]", "");
			String[] sentarr = sent.split(" ");
			for (int i = 0; i<sentarr.length; i++) {
				//System.out.println("Cadena" + i + ": " + sentarr[i]);
				String root1 = syn.get(sentarr[i]);
				if (root1 != null) {
					//System.out.println("Found " + sentarr[i] + " and using " + root + " instead.");
					val = a.get(root1);
				}
				else {
					root1 = sentarr[i];
					val = a.get(sentarr[i]);
				}
				if (val != null) {
					Integer id1 = val.x;
					for (int j = i+1; j<sentarr.length; j++) {
						String root2 = syn.get(sentarr[j]);
						if (root2 != null)
							val = a.get(root2);
						else {
							root2 = sentarr[j];
							val = a.get(sentarr[j]);
						}
						if (val != null) {
							Integer id2 = val.x;
							graph[id1][id2] ++;
							graph[id2][id1] ++;
							System.out.println (root1 + " " + val.y + "\t" + root2);
						}
					}
				}
			}
		}	
	}

	private static Integer AssignIDs(Map<String, Integer> a, Map<String, Tuple<Integer,Integer>> b) {
		ArrayList<String> keys = new ArrayList<String>(a.keySet());
		Integer i = 0;
		for (String key: keys) {
			Integer n = a.get(key);
			b.put(key, new Tuple<Integer, Integer>(i,n));
			i++;
		}
		return i;
	}

	private static void Write(Map<String, Tuple<Integer, Integer>> a, Integer[][] graph, int minW) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("data.gexf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<gexf xmlns=\"http://www.gexf.net/1.2draft\" xmlns:viz=\"http://www.gexf.net/1.2draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\" version=\"1.2\">");
		out.println("\t<meta lastmodifieddate=\"2017-01-18\">");
		out.println("\t\t<creator>Carlos Cotta</creator>");
		out.println("\t\t<description>Word network</description>");
		out.println("\t</meta>");
		out.println("\t<graph mode=\"static\" defaultedgetype=\"undirected\">");
		out.println("\t\t<nodes>");
		int i = 0;
		ArrayList<String> keys = new ArrayList<String>(a.keySet());
		for (String key: keys) {
			Tuple<Integer, Integer> t = a.get(key);
			Integer id = t.x;
			boolean attached = false;
			for (int j=0; !attached && (j<graph.length); j++)
				if (graph[id][j] >= minW) {
					attached = true;
				}
			if (attached) {
				Integer n = t.y;
				out.println("\t\t\t<node id=\"" + id + "\" label=\"" + key + "\" >");
				out.println("\t\t\t\t<viz:size value=\"" + (n*10) + "\"/>");
				out.println("\t\t\t</node>");
				i++;
			}
		}
		out.println("\t\t</nodes>");
		out.println("\t\t<edges>");
		i=0;
		for (int j=0; j<graph.length; j++)
			for (int k=j+1; k<graph.length; k++)
				if (graph[j][k]>=minW) {
					System.out.println(j + " " + k);
//					out.println("\t\t\t<edge id=\"" + i + "\" source=\"" + j + "\" target=\"" + k + "\" />");
					out.println("\t\t\t<edge id=\"" + i + "\" source=\"" + j + "\" target=\"" + k + "\" weight=\"" + graph[j][k] + "\" type=\"undirected\" />");
					i++;
				}
		out.println("\t\t</edges>");
		out.println("\t</graph>");
		out.println("</gexf>");
		out.close();		
	}

	private static void WriteJSON(Map<String, Tuple<Integer, Integer>> a, Integer[][] graph, int minW) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("data.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println("{");
		out.println("\t\"nodes\": [");
		int i = 0;
		ArrayList<String> keys = new ArrayList<String>(a.keySet());
		for (String key: keys) {
			Tuple<Integer, Integer> t = a.get(key);
			Integer id = t.x;
			boolean attached = false;
			for (int j=0; !attached && (j<graph.length); j++)
				if (graph[id][j] >= minW) {
					attached = true;
				}
			if (attached) {
				Integer n = t.y;
				if (i>0)
					out.println(",");
				out.println("\t{");
				out.println("\t\t\"id\": \" "+id+"\", ");
				out.println("\t\t\"label\": \" "+key+"\",");
				out.println("\t\t\"x\": "+i+",");
				out.println("\t\t\"y\": "+i+",");
				out.println("\t\t\"size\": "+n);
				out.print("\t}");
				i++;
			}
		}
		out.println("\t],");
		out.println("\t\"edges\": [");
		i=0;
		for (int j=0; j<graph.length; j++)
			for (int k=0; k<graph.length; k++)
				if (graph[j][k]>=minW) {
					System.out.println(j + " " + k);

					if (i>0)
						out.println(",");
					out.println("\t{");
					out.println("\t\t\"id\": \" "+i+"\", ");
					out.println("\t\t\"source\": \" "+j+"\",");
					out.println("\t\t\"target\": \" "+k+"\",");
					out.println("\t\t\"size\": "+graph[j][k]);
					out.print("\t}");
					i++;
				}
		out.println("\t]");
		out.println("}");
		out.close();		
	}

	private static Map<String, String> Filter(Map<String, Integer> a, int minC) throws FileNotFoundException {
	
		System.out.println("Procesando sinónimos... ");
		System.out.println("------------------------------------ ");

		Scanner scan = new Scanner(new File("synonyms.txt"));
		Map<String, String> synonyms = new HashMap<String, String>();
		while(scan.hasNextLine()){
			String sent = scan.nextLine(); //clave
			String[] sentarr = sent.split(" ");
			boolean first = true;
			String firstWord = null;
			Integer total = 0;
			for (String word : sentarr) {
				Integer c = a.get(word);
				if (c!= null) {
					if (first) {
						firstWord = word;
						first = false;
					}
					else {
						synonyms.put(word, firstWord);
					}
					total += c;
					a.remove(word);
					System.out.println("Eliminado " + word + " (" + c + ")");
				}
			}
			if (!first) {
				a.put(firstWord, total);
				System.out.println("Añadido " + firstWord + " (" + total + ")");
			}
		}
		scan.close();
		
		System.out.println("Eliminando palabras no relevantes... ");
		System.out.println("------------------------------------ ");

		scan = new Scanner(new File("filtered-words.txt"));
		while(scan.hasNextLine()){
			String sent = scan.nextLine(); //clave
			String[] sentarr = sent.split(" ");
			for (String word : sentarr) {
				System.out.println("Eliminado " + word + " (" + a.get(word) + ")");
		        a.remove(word);
			}
		}
		scan.close();
		
		System.out.println("Eliminando palabras de baja frecuencia... ");
		System.out.println("------------------------------------ ");

		ArrayList<String> keys = new ArrayList<String>(a.keySet());
		for (String key: keys) {
			Integer n = a.get(key);
			if (n<minC) {
				System.out.println("Eliminando " + key + ": " + a.get(key));
				a.remove(key);
			}
			else
				System.out.println("Se conserva -------->" + key + ": " + a.get(key));
		}
		return synonyms;
	}

	public static Map<String, Integer> CountWords(ArrayList<String> L) {
	        Map<String, Integer> a = new HashMap<String, Integer>();
	        String sent;
		    for (String article: L) {
				sent = article.toLowerCase().replaceAll("[^a-z ]", "");
				String[] sentarr = sent.split(" ");
				for (String word : sentarr) {
					Integer n = a.get(word);
			        n = (n == null) ? 1 : ++n;
			        a.put(word, n);
				}
			}
		    ArrayList<String> aux = new ArrayList<String>();
	        for (String key : a.keySet()) {
	        	aux.add(key);
	            //System.out.println(key + " = " + a.get(key));
	        }
	 aux.sort(null);
	        for (String key : aux) {
	            System.out.println(key + " = " + a.get(key));
	        }
	        return a;
	    }


	/*
	 * Scans through the XML file and finds articles matching the desired keywords.
	 * 
	 */
	public static int readText(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(filename));
		String clave = null;
    	PrintWriter out = null;
    	int counter = 0;
		try {
			out = new PrintWriter(new FileWriter("article-ids.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(scan.hasNextLine()){
			String line = scan.nextLine().toUpperCase();
			if (line.indexOf(" KEY=")>-1) {
				clave = line;
			} else if (((line.indexOf("PORTFOLIO")>-1) ||
             	   (line.indexOf("INVESTMENT")>-1)  ||
             	   (line.indexOf("MARKOWITZ")>-1)   ||
                    (line.indexOf("SHARPE")>-1)  ||
             	   (line.indexOf("PORTAFOLIO")>-1)		  ||
             	   (line.indexOf("CARTERA")>-1) ) && 
             	  ((line.indexOf("EVOLUTIVE")>-1) ||
             	   (line.indexOf("MEMETIC")>-1) || 
             	   (line.indexOf("EVOLUTIONARY")>-1) ||
             	   (line.indexOf("GENETIC")>-1) ||
                   (line.indexOf("HYBRID")>-1) ||                                    
                    (line.indexOf("EVOLUTIVOS")>-1))  
                    //&&
                   //((line.indexOf("OPTIMIZ")>-1)) 
                    )
					   {
							out.println(clave.substring(clave.indexOf("KEY=")+5, clave.length()-2));
							out.println(line.substring(7,line.length()-8));
							System.out.println(clave.substring(clave.indexOf("KEY=")+5, clave.length()-2));
							System.out.println(line.substring(7,line.length()-8));
							counter++;
			}
		}
		scan.close();
		out.close();
		return counter;
	}
}