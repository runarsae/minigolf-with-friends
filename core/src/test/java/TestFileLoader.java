import com.mygdx.minigolf.model.levels.Course;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

abstract class TestFileLoader {
    String dir = "";

    String getPath(String filename) {
        String filePath = dir + filename;
        String absPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource(filePath)
        ).getPath();
        if (System.getProperty("os.name").startsWith("Windows")) {
            absPath = absPath.substring(1);
        }
        return absPath;
    }

    InputStream getFileStream(String filename) throws FileNotFoundException {
        return new FileInputStream(getPath(filename));
    }

    String getFileContents(String filename) throws IOException {
        String path = getPath(filename);
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    Course getCourse(String filename) throws IOException {
        return new Course(getFileStream(filename), filename);
    }
}
