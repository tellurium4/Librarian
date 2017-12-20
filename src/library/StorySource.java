package library;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface StorySource {

	public void format(Path p, Queue<StoryInfo> q) throws IOException;

}
