package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

public class Catalog {
	private Path workingDir;
	private Map<Path, StorySource> storyMap;
	private Map<String, StorySource> storySources;
	private List<Path> checkins;
	private List<Path> truncatedCheckins;
	private List<String> supportedFileExt;
	private Queue<StoryInfo> storyInfo;
	
	public Catalog(){
		this.storyMap = new HashMap<Path, StorySource>();
		this.storySources = new HashMap<String, StorySource>();
		this.checkins = new ArrayList<Path>();
		this.truncatedCheckins = new ArrayList<Path>();
		this.storyInfo = new LinkedList<StoryInfo>();
	}
	
	public void addStory(String title, StorySource storySource) {
		String newTitle = Paths.get("").toAbsolutePath().normalize().toString()
				+ title;
		List<Path> singleCheckin = new ArrayList<Path>();
		singleCheckin.add(Paths.get(newTitle));
		truncateNames(singleCheckin);
//		this.storyMap.put(path, determineSource(path));
	}
	
	public void removeStory(String title){
		this.storyMap.remove(title);
	}
	
	public void formatStories() {
		for (Entry<Path, StorySource> entry : this.storyMap.entrySet()) {
			try {
				entry.getValue().format(entry.getKey(), this.storyInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isEmpty() {
		return this.storyMap.isEmpty();
	}
	
	public boolean contains(String title){
		return storyMap.containsKey(title);
	}
	public boolean loadCatalog(Path catalogPath){
		return false;
	}
	public void generateCheckins(Path path){
		String dirString = path.toString();
		System.out.println("Generating Checkins from Directory: " + dirString);
		File f = new File(dirString);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		this.checkins = getCheckinsFromFileArray(files);
		if(checkins.isEmpty()){
			System.out.println("Error, no checkins found");
		} else {
			System.out.println("Checkins: " + this.checkins.toString());
			System.out.println("Checkins Generated");
			truncateNames(this.checkins);
			System.out.println();

			System.out.println("Truncating checkins...");
			System.out.println("Truncated Checkins: " + this.truncatedCheckins.toString());
			System.out.println("Truncated Checkins Generated");
			System.out.println();
		}
	}
	
	private List<Path> getCheckinsFromFileArray(ArrayList<File> files) {
		ArrayList<Path> list = new ArrayList<Path>();
		// remove non-supported file types and change representation in the system
		for (File file : files) {
			if(file.toString().contains(".html")){
				System.out.println(file.toPath().toString());
				list.add(file.toPath());
			}
		}
		return list;
	}
	public void truncateNames(List<Path> checkins){
		for (Path path : checkins) {
			String fileString = path.toString();
			System.out.println(fileString);
			if(fileString.contains(" - ")){
				File oldFile = path.toFile();
				File newFile = new File(fileString.substring(0, fileString.indexOf(" - ")) + ".html");
				Path newPath = newFile.toPath();
				System.out.println(newFile.toPath().toString());
				try {
					if(Files.deleteIfExists(path)){
						System.out.println("Deleting old story and updating");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				oldFile.renameTo(newFile);
				this.truncatedCheckins.add(newPath);
			} else {
				this.truncatedCheckins.add(path);
			}
		}
	}
	
	public void populateStoryMap() {
		System.out.println("Populating Story Map...");
		for (Path path : this.truncatedCheckins) {
			this.storyMap.put(path, determineSource(path));
		}
		System.out.println("Catalog Populated");
	}
	
	private StorySource determineSource(Path path){
		File file = new File(path.toString());

		try {
		    Scanner scanner = new Scanner(file);
		    //now read the file line by line...
		    int lineNum = 0;
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        lineNum++;
		        if(line.contains("FanFiction.net")) { 
		        	return storySources.get("FFDownloader");
		        }
		    }
		    scanner.close();
		} catch(FileNotFoundException e) { 
		    //handle this
		}
		System.out.println("Error StorySource not supported");
		return null;
	}
	
	public void setExtensions(List<String> exts){
		this.supportedFileExt = exts;
	}
	
	public void generateCatalog(Path path){
		
	}

	public void addStoryType(String Name, StorySource storySource) {
		this.storySources.put(Name, storySource);
	}

	public void deleteAndRename() {
		String dirString = this.workingDir.toString();
		File f = new File(dirString);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		List<Path> list = new ArrayList<Path>();
		for (File file : files) {
			if(file.toString().contains(".html")){
				System.out.println(file.toPath().toString());
				list.add(file.toPath());
			}
		}
		for (Path p : list) {
			String fileString = p.toString();
			if(!fileString.contains("temp")){
				try {
					Files.deleteIfExists(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (Path p : list) {
			String fileString = p.toString();
			if(fileString.contains("temp")){
				
			}
		}
	}

	public Path getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(Path workingDir) {
		this.workingDir = workingDir;
	}

	
}
