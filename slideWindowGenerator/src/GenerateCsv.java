import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class to generate *.csv file from int[][]matrix
 * @author barbara.lopes
 *
 */
public class GenerateCsv
{
    public void generateSlideWindow(int idCluster, List<List<String>>datas, int j)throws IOException{
        String sFile;
        FileWriter writer = null;

        String directory = System.getProperty("user.dir")+"\\SlideWindows\\Cluster"+idCluster+"\\j"+j;
        String directoryCsv = directory+"\\CSV";
        String directoryArff = directory+"\\ARFF";
        
        new File(directoryCsv).mkdirs();
        new File(directoryArff).mkdirs();
        
        int interval = 10 - j;
        
        for(int seq = 0; seq < datas.size(); seq++){
            sFile = directoryCsv+"\\Seq"+seq+".csv";
            writer = new FileWriter(sFile);

            int stop = 0;
            
            for(int idGene = 0; idGene < datas.size(); idGene ++){
                writer.append("seq"+idGene);
                writer.append(';');
            }
            
            writer.append("alvo_seq"+seq);
            writer.append(';');
            writer.append("\n");
            
            for(int i = 0; i < interval; i++){
                for(List<String> geneData: datas){
                    stop = i + j;
                    for(int count = i; count < stop; count++){
                        writer.append(geneData.get(count)+"");
                        writer.append(';');
                    }
                }

                writer.append(datas.get(seq).get(i+1)+"");
                writer.append(';');
                writer.append("\n");
                writer.flush();
            }
            writer.close();
            CSV2Arff arffGenerator = new CSV2Arff();
            arffGenerator.generateArff(sFile, directoryArff+"\\Seq"+seq+".arff");
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
                writer.append(';');
            }
            writer.append("\n");
            writer.flush();
        }
        writer.close();
    }
}