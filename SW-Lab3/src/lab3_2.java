import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.riot.RiotException;

public class lab3_2 {

	public static void main(final String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

		File file1 = new File("MyDatabases/Dataset1");      
		   String[] myFiles;    
		       if(file1.isDirectory()){
		           myFiles = file1.list();
		           for (int i=0; i<myFiles.length; i++) {
		               File myFile = new File(file1, myFiles[i]); 
		               myFile.delete();
		           }
		        }
		       
		String directory = "MyDatabases/";
		Dataset dataset = TDBFactory.createDataset(directory + "Dataset1");

		Model model = dataset.getDefaultModel();
		//Model model = ModelFactory.createDefaultModel();

		double start = System.currentTimeMillis();
		String file = "Monterey.rdf";
		//model.read("Monterey.rdf", "RDFXML") ;
		FileManager.get().readModel(model, file);
		double end = System.currentTimeMillis();


		double time = (end - start) / 1000.0;
		System.out.println(String.format("Load of %s took %.1f seconds", file, time));
		
		dataset.begin(ReadWrite.READ);
		
		try {
			String queryString = "SELECT ?p ?o WHERE { <urn:monterey:#incident1> ?p ?o }";
			//String queryString = "SELECT ?p ?o WHERE { <urn:monterey:#incident1> ?p ?o . }";
			Query query = QueryFactory.create(queryString);

			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			ResultSetFormatter.outputAsXML(new FileOutputStream("Lab3_2_AMohapatra.xml"), results);
			qe.close();
		} catch (final IOException e) {
			e.printStackTrace();
		} 
		finally {
			dataset.end();
			model.close();
		} 
	}
}
