import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {
    public void generateArff(String csv, String arff) throws IOException{

        // load CSV
        CSVLoader loader = new CSVLoader();
        
        loader.setSource(new File(csv));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(arff));
//        saver.setDestination(new File(arff));
        saver.writeBatch();

    }
}