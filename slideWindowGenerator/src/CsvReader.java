import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CsvReader {

	public static void main(String[] args) {

		CsvReader obj = new CsvReader();
		try {
			obj.readClusters();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<String> symbols;

	public void readClusters() throws IOException {

		String dir = System.getProperty("user.dir");
        String csvClustersFile = dir+"\\clusters.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		List<String>cluster = new ArrayList<String>();
		String[] lineCluster;

		List<List<String>>clusters = new LinkedList<List<String>>();  


		try {

			br = new BufferedReader(new FileReader(csvClustersFile));
			while ((line = br.readLine()) != null) {

				lineCluster = line.split(cvsSplitBy);

				for(int i = 0; i < lineCluster.length; i++){
					cluster.add(lineCluster[i]);
				}

				clusters.add(cluster);
				cluster = new ArrayList<String>();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		List<List<String>>genesData;


		CsvGenerator generator = new CsvGenerator();

		int count = 0;

		readDictionary();

		for(List<String> seqs: clusters){
			genesData = searchClusterData(seqs);

			try {
				generator.generateCsvList(count, genesData);
				int j = 1;
				generator.generateSlideWindow(count, seqs, symbols, genesData, j);
				j = 2;
				generator.generateSlideWindow(count, seqs, symbols, genesData, j);
			} catch (IOException e) {
				e.printStackTrace();
			}

			count++;
		}

		System.out.println("Done");
	}

	public void readDictionary() throws IOException {

		String dir = System.getProperty("user.dir");
		String csvDictionaryFile = dir+"\\dictionary.csv";
		BufferedReader br = null;
		String line = "";

		symbols = new LinkedList<String>();

		try {

			br = new BufferedReader(new FileReader(csvDictionaryFile));
			while ((line = br.readLine()) != null) {
				symbols.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<List<String>> searchClusterData(List<String>cluster){

		String dir = System.getProperty("user.dir");
        String csvDataFile = dir+"\\dataset.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		int count = 0;

		String[] lineGene;
		List<String> gene = new ArrayList<String>();

		List<List<String>>genesData = new LinkedList<List<String>>();  

		try {

			br = new BufferedReader(new FileReader(csvDataFile));
			while ((line = br.readLine()) != null) {
				if(cluster.contains(count+"")){
					gene = new ArrayList<String>();
					lineGene = line.split(cvsSplitBy);
					for(String data: lineGene){
						gene.add(data);
					}
					genesData.add(gene);
				}
				count++;
			}

			return genesData;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new ArrayList<List<String>>();
	}
}