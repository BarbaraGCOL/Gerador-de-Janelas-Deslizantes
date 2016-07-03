import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class to generate *.csv file from int[][]matrix
 * @author barbara.lopes
 *
 */
public class CsvGenerator
{
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	public void generateSlideWindow(int idCluster, List<String> seqs, List<String> symbols, 
			List<List<String>>datas, int j)throws IOException{

		String sFile;
		FileWriter writer = null;

		String directory = System.getProperty("user.dir")+"\\SlideWindows\\Cluster"+idCluster+"\\j"+j;
		String directoryCsv = directory+"\\CSV";
		String directoryArff = directory+"\\ARFF";

		new File(directoryCsv).mkdirs();
		new File(directoryArff).mkdirs();

		int interval = 10 - j, id;

		String name;
		
		for(int seq = 0; seq < datas.size(); seq++){
			id = Integer.parseInt(seqs.get(seq));
			name = "seq"+id+"_"+symbols.get(id);
			
			sFile = directoryCsv+"\\"+name+".csv";
			writer = new FileWriter(sFile);

			int stop = 0;

			for(String idSeq: seqs){
				for(int i = 1; i <= j; i++){
					id = Integer.parseInt(idSeq);
//					writer.append("seq"+idSeq+"(t - "+i+")");
					writer.append("seq"+idSeq+"_"+symbols.get(id)+"(t - "+i+")");
					writer.append(COMMA_DELIMITER);
				}
			}

			id = Integer.parseInt(seqs.get(seq));
			writer.append("alvo_seq"+id+"_"+symbols.get(id));
			writer.append(NEW_LINE_SEPARATOR);

			for(int i = 0; i < interval; i++){
				for(List<String> geneData: datas){
					stop = i + j;
					for(int count = i; count < stop; count++){
						writer.append(geneData.get(count)+"");
						writer.append(COMMA_DELIMITER);
					}
				}

				writer.append(datas.get(seq).get(i+j)+"");
				writer.append(NEW_LINE_SEPARATOR);
				writer.flush();
			}
			writer.close();
			CSV2Arff arffGenerator = new CSV2Arff();
			arffGenerator.generateArff(sFile, directoryArff+"\\"+name+".arff");
		}
	}

	public void generateCsvList(int idCluster, List<List<String>>datas) 
			throws IOException{

		String directory = System.getProperty("user.dir")+"\\Clusters";

		new File(directory).mkdirs();

		FileWriter writer = new FileWriter(directory+"\\cluster"+idCluster);
		for(List<String> data: datas){
			for(String value: data){
				writer.append(value+"");
				writer.append(COMMA_DELIMITER);
			}
			writer.append(NEW_LINE_SEPARATOR);
			writer.flush();
		}
		writer.close();
	}
}