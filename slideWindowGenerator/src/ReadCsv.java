import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCsv {

    public static void main(String[] args) {

        ReadCsv obj = new ReadCsv();
        obj.readClusters();

    }

    public void readClusters() {

        String dir = System.getProperty("user.dir");
        String csvClustersFile = dir+"\\clusters.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        List<String>cluster = new ArrayList<String>();
        String[] lineCluster;

        List<List<String>>clusters = new ArrayList<List<String>>();  


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


        GenerateCsv generator = new GenerateCsv();

        int count = 0;
        
        for(List<String>c: clusters){
            genesData = searchClusterData(c);
            try {
                generator.generateCsvList(count, genesData);
                int j = 1;
                generator.generateSlideWindow(count, genesData, j);
                j = 2;
                generator.generateSlideWindow(count, genesData, j);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            count++;
        }

        System.out.println("Done");
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

        List<List<String>>genesData = new ArrayList<List<String>>();  

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