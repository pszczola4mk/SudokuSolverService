package pl.wojo.SudokuSolverService.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base64Coder {

	public static String encoder(String imagePath) {
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			String base64Image = "";
			byte[] imageData = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
			return base64Image;
		} catch (FileNotFoundException e) {
			log.error("Image not found" + e);
		} catch (Exception ioe) {
			log.error("Exception while reading the Image " + ioe);
		}
		return null;
	}
	public static byte[] readFile(String imagePath) {
		File file = new File(imagePath);
		return readFile(file);
	}

	public static byte[] readFile(File file) {
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			byte[] imageData = new byte[(int) file.length()];
			imageInFile.read(imageData);
			return imageData;
		} catch (FileNotFoundException e) {
			log.error("Image not found" + e);
		} catch (Exception ioe) {
			log.error("Exception while reading the Image " + ioe);
		}
		return null;
	}

	public static void decoder(String base64Image, String pathFile) {
		try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
			byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
			imageOutFile.write(imageByteArray);
		} catch (FileNotFoundException e) {
			log.error("Image not found" + e);
		} catch (Exception ioe) {
			log.error("Exception while reading the Image " + ioe);
		}
	}
}
