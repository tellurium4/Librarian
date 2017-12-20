package library;

import java.awt.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LibrarianApp {

	public static void main(String[] args) {
		// TODO:3 use args to add single stories to loaded catalog -a nameOfStory1 nameOfStory2 -t StoryType1 StoryType2
		// TODO:4 add verbose mode -v
		// TODO:5 make supported exentensions work
		Librarian librarian = new Librarian();
		Catalog catalog = new Catalog();
		NPScraper npScraper = new NPScraper();
		FFDownloader ffDownloader = new FFDownloader();
		ArrayList<String> supportedExtensions = new ArrayList<String>();
		
		supportedExtensions.add(".html");
		
		// TODO:1 add catalog loading support
		Path catalogPath = Paths.get("Catalog.in").toAbsolutePath().normalize();
		catalog.addStoryType("NPScraper", npScraper);
		catalog.addStoryType("FFDownloader", ffDownloader);
		catalog.setExtensions(supportedExtensions);
		catalog.setWorkingDir(Paths.get("").toAbsolutePath().normalize());
		catalog.loadCatalog(catalogPath);
		catalog.generateCheckins(Paths.get("").toAbsolutePath().normalize());
		catalog.populateStoryMap();
		
		
		
		librarian.setCatalog(catalog);
		librarian.formatStories();
		// TODO:2 generate a Catalog.in file
		catalog.generateCatalog(catalogPath);
	}

}
