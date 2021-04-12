package pl.wojo.SudokuSolverService.prashant.sudoku.image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Classifier {

    private static Map<Integer, int[][]> data;
    private static boolean loaded;

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Load the training data for nearest neighbor comparision
     */
    private static void loadData() throws Exception {

        data = new HashMap<>();

        data.put(0, new int[25][25]);
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 4; j++) {
                byte[] bytes = FileUtils.readFileToByteArray(new File("resources/training_data/" + i + "_" + j + ".png"));
                Mat s = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
                if (s.size().height != 25 || s.size().width != 25) { throw new Exception("Need a 25x25 image for training.."); }
                int[][] _data = new int[25][25];
                for (int x = 0; x < 25; x++) {
                    for (int y = 0; y < 25; y++) {
                        _data[x][y] = (int) s.get(y, x)[0];
                    }
                }
                data.put(i * 100 + j, _data);
            }
        }
    }

    /**
     * Compare the given mat image and return the nearest matching number
     */
    public static int getBox(Mat image) throws Exception {
        if (data == null) { loadData(); }

        if (image.size().height != 25 || image.size().width != 25) { throw new Exception("Need a 25x25 image for identification.."); }
        int max_match = 0;
        int max_match_count = 0;
        for (Integer i : data.keySet()) {
            int[][] _data = data.get(i);

            int count = 0;

            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 25; y++) {
                    if ((int) image.get(y, x)[0] == _data[x][y]) { count++; }
                }
            }

            if (count > max_match_count) {
                max_match = i / 100;
                max_match_count = count;
            }
        }
        return max_match;
    }
}
