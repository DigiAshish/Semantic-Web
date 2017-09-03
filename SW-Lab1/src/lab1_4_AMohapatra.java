import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;
import org.apache.jena.query.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import java.io.*;


public class lab1_4_AMohapatra {
	public static void main(final String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);	
		Dataset dataset=TDBFactory.createDataset("MyDatabases/Dataset1");

		String personURI = "http://utdallas/semclass";
		String prefix = "Sir";
		String givenName = "Timothy John";
		String familyName = "Berners-Lee";
		String fullName = prefix + " " + givenName + " " + familyName;
		String bday="1955 June 08";
		String title = "Computer Scientist";
		String email = "mailto:timbl@w3.org";

		try {
			Model model = dataset.getNamedModel("myrdf");
			
			dataset.begin(ReadWrite.WRITE);
			
			Resource timothy 
			= model.createResource(personURI)
				.addProperty(VCARD.FN, fullName)
				.addProperty(VCARD.N, model.createResource()
					.addProperty(VCARD.Prefix, prefix)
					.addProperty(VCARD.Given, givenName)
					.addProperty(VCARD.Family, familyName))
				.addProperty(VCARD.BDAY, bday)
				.addProperty(VCARD.EMAIL, email)
				.addProperty(VCARD.TITLE, title);

			FileManager.get()
				.readModel(model, "AMohapatra_FOAFFriends.rdf");
			
			dataset.commit();

			FileWriter WriteAsXML = new FileWriter("Lab1_4_AMohapatra.xml");
			FileWriter WriteAsNTP= new FileWriter("Lab1_4_AMohapatra.ntp");
			FileWriter WriteAsN3 = new FileWriter("Lab1_4_AMohapatra.n3");
			model.write(WriteAsXML, "RDF/XML");
			model.write(WriteAsNTP, "N-TRIPLE");
			model.write(WriteAsN3, "N3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally { 
			dataset.end() ; 
		}
	}
}