import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {
    public void generateArff(String csv, String arff) throws IOException{

    	try{
	    	// load the CSV file (input file)
	    	CSVLoader loader = new CSVLoader();
	        loader.setSource(new File(csv));
	        loader.setNoHeaderRowPresent(false);
	        loader.setFieldSeparator(",");
	        Instances data = loader.getDataSet();

	        // save as an  ARFF (output file)
	        ArffSaver saver = new ArffSaver();
	        saver.setInstances(data);
	        saver.setFile(new File(arff));
	        saver.writeBatch();
        }
        catch(Exception e){
        	System.out.println(e);
        }
    }
}