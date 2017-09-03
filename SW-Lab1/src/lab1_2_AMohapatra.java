import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;
import java.io.*;

public class lab1_2_AMohapatra {
	public static void main(final String[] args) {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

		String personURI = "http://utdallas/semclass";
		String prefix = "Sir";
		String givenName = "Timothy John";
		String familyName = "Berners-Lee";
		String fullName = prefix + " " + givenName + " " + familyName;
		String bday="1955 June 08";
		String title = "Computer Scientist";
		String email = "mailto:timbl@w3.org";

		Model model = ModelFactory.createDefaultModel();

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


		try {
			FileWriter xmlWriter = new FileWriter("Lab1_2_AMohapatra.xml");
			FileWriter ntpWriter = new FileWriter("Lab1_2_AMohapatra.ntp");
			FileWriter n3Writer = new FileWriter("Lab1_2_AMohapatra.n3");
			model.write(xmlWriter, "RDF/XML");
			model.write(ntpWriter, "N-TRIPLE");
			model.write(n3Writer, "N3");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}