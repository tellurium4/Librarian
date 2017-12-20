package library;

public class Librarian {
	private Catalog catalog;
	
	
	public Librarian(){
	}
	
	public void formatStories() {
		System.out.println("Formatting stories with Librarian...");
		if(this.catalog.isEmpty()) {
			System.out.println("Error, no stories found in catalog");
		}else {
			this.catalog.formatStories();
			System.out.println("Format Completed");
			System.out.println("Removing ");
			this.catalog.deleteAndRename();
		}
	}
	
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
}
