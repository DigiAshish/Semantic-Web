import java.io.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.*;

public class Lab_2 {

	public static void main( String[] args) {
		
	org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

		File file = new File("MyDatabases/Dataset1");      
		   String[] myFiles;    
		       if(file.isDirectory()){
		           myFiles = file.list();
		           for (int i=0; i<myFiles.length; i++) {
		               File myFile = new File(file, myFiles[i]); 
		               myFile.delete();
		           }
		        }


		 String URI = "http://utdallas/semclass#";
		 String movieURI = URI + "Movie-";
		 String personURI = URI + "Person-";
		 String bookURI = URI + "Book-";
		 String directorTitle = "director";
		 String authorTitle = "author";

		 String DirectorURI = personURI + "StanleyKubrick";
		 String DirectorGiven = "Stanley";
		 String DirectorFamily = "Kubrick";
		 String DirectorFullName = DirectorGiven + " " + DirectorFamily;

		 String DrStrangeLoveURI = movieURI + "DrStrangelove";
		 String DrStrangeLoveTitle = "Dr. Strangelove";
		 String DrStrangeLoveYear = "1964";

		 String AClockworkOrangeURI = movieURI + "AClockworkOrange";
		 String AClockworkOrangeTitle = "A Clockwork Orange";
		 String AClockworkOrangeYear = "1971";
		 
		 String AuthorURI = personURI + "PeterGeorge";
		 String AuthorGiven = "Peter";
		 String AuthorFamily = "George";
		 String AuthorFullName = AuthorGiven + " " + AuthorFamily;

		 String redAlertURI = bookURI + "redAlert";
		 String redAlertTitle = "Red Alert";
		 String redAlertYear = "1958";


		 String directory = "MyDatabases/";
		 Dataset dataset = TDBFactory.createDataset(directory + "Dataset1");

		 Model model = ModelFactory.createDefaultModel();

		 Resource movie = model.createResource(movieURI);
		 Resource person = model.createResource(personURI);
		 Resource book = model.createResource(bookURI);

		 Property directorProperty = model.createProperty(movieURI, directorTitle);
		 Property adaptationOfProperty = model.createProperty(movieURI, "AdaptationOf");


		 Resource kubrick = model.createResource(DirectorURI)
			.addProperty(RDF.type, person)
			.addProperty(VCARD.FN, DirectorFullName)
			.addProperty(VCARD.N, model.createResource()
				.addProperty(VCARD.Given, DirectorGiven)
				.addProperty(VCARD.Family, DirectorFamily))
			.addProperty(VCARD.TITLE, directorTitle);


		 Resource george = model.createResource(AuthorURI)
			.addProperty(RDF.type, person)
			.addProperty(VCARD.FN, AuthorFullName)
			.addProperty(VCARD.N, model.createResource()
				.addProperty(VCARD.Given, AuthorGiven)
				.addProperty(VCARD.Family, AuthorFamily))
			.addProperty(VCARD.TITLE, authorTitle);


		 Resource redAlert = model.createResource(redAlertURI)
			.addProperty(RDF.type, book)
			.addProperty(DC.creator, george)
			.addProperty(DC.title, redAlertTitle)
		    .addProperty(DC.date, redAlertYear);


		 Resource AClockworkOrange = model.createResource(AClockworkOrangeURI)
			.addProperty(RDF.type, movie)
			.addProperty(DC.title, AClockworkOrangeTitle)
			.addProperty(DC.date, AClockworkOrangeYear)
			.addProperty(directorProperty, kubrick)
			.addProperty(adaptationOfProperty, redAlert);

		 Resource DrStrangeLove = model.createResource(DrStrangeLoveURI)
			.addProperty(RDF.type, movie)
			.addProperty(DC.title, DrStrangeLoveTitle)
			.addProperty(DC.date, DrStrangeLoveYear)
			.addProperty(directorProperty, kubrick)
			.addProperty(adaptationOfProperty, redAlert);

		dataset.begin(ReadWrite.WRITE);
		
		try {
			dataset.commit();

			 FileWriter WriteAsXML = new FileWriter("Lab2_3_AMohapatra.xml");
			 FileWriter WriteAsN3 = new FileWriter("Lab2_3_AMohapatra.n3");

			model.write(WriteAsXML, "RDF/XML");
			model.write(WriteAsN3, "N3");

			WriteAsXML.close();
			WriteAsN3.close();
			
		} catch ( IOException e) 
		{
			e.printStackTrace();
			dataset.end();
			model.close();
		}
		
		
	}
}