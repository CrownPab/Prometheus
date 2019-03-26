package prometheus.utils;

import javafx.scene.image.Image;

import java.io.File;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageUtils {
	/**
	 * Function to convert a path to a JavaFX Image object
	 * @param path Path of the image
	 * @return Image object at given path
	 */
    public static Image loadImage(String path) {
        File file = new File(path);
        String imagePath = file.getAbsolutePath();
        //Modify based on OS
        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            imagePath=imagePath.replace('/', File.separatorChar);
            imagePath = imagePath.replace("\\", "\\\\");
        } else {
            // From Linux/Mac to Windows
            imagePath=imagePath.replace('\\', File.separatorChar);

        }
        
        //Add file: to work with JavaFX
        imagePath="file:"+imagePath;

        return new Image(imagePath);
    }
}
