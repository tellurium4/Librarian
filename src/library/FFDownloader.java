package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;

public class FFDownloader implements StorySource{

	@Override
	public void format(Path path, Queue<StoryInfo> q) throws IOException {
		File file = path.toFile();
		String fileString = file.getName();
		File tempFile = new File("temp" + fileString);
		if(Files.deleteIfExists(tempFile.toPath())){
			System.out.println("Cleaned format temp file");
		}
		StoryInfo storyinfo = new StoryInfo();
		boolean headFlag = true;
		boolean titleFlag = true;
		boolean categoryFlag = true;
		
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			FileWriter fileWriter = new FileWriter(tempFile);
			String input = buffer.readLine();
			fileWriter.write(input + "\n");
			input = buffer.readLine();
			while(input != null){
				if(headFlag){
					String input2 = buffer.readLine();
					if(input.contains("<head>") && !input2.contains("style")){
						writeStyle(fileWriter);
						headFlag = false;
					}
					fileWriter.write(input2 + "\n");
					input = input2;
				} else if (titleFlag) {
					if(input.contains("<title>")){
						storyinfo.setTitle(getTitleFromLine(input));
						titleFlag = false;	
					}
				} else if (categoryFlag){
					if(input.contains("<b>Category:</b>")){
						storyinfo.setCategory(getCategoryFromLine(input));
						categoryFlag = false;
					}
				}
				fileWriter.write(input + "\n");
				input = buffer.readLine();
			}
			fileWriter.close();
			buffer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getCategoryFromLine(String input) {
		if(input.contains("Crossover")){
			return "Crossover";
		}
		return null;
	}

	private String getTitleFromLine(String input) {
		
		return null;
	}

	private void writeStyle(FileWriter fileWriter) throws IOException {
		fileWriter.write(
				  "\t\t<style>\n" 
				+ "\t\t\tbody {\n"
				+ "\t\t\t\tcolor: white;\n" 
				+ "\t\t\t\tbackground-color: rgb(30, 30, 30);\n"
				+ "\t\t\ta {\n"
				+ "\t\t\t\tcolor: grey;\n"
				+ "\t\t\t}\n"
				+ "\t\t</style>\n");
	}

}
