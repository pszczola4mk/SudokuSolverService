package pl.wojo.SudokuSolverService.controller;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wojo.SudokuSolverService.business.Base64Coder;
import pl.wojo.SudokuSolverService.model.Image;

@Slf4j
@RestController
public class ImageController {

	@GetMapping("/echo/{text}")
	public String echo(@PathVariable String text) {
		log.info("echo - start {}",text);
		return "Echo: " + text + " date: " + new Date();
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(@RequestBody Image image) {
		log.info("/POST request with " + image.toString());
		String path = "/home/pszczola/tmp/" + image.getName();
		Base64Coder.decoder(image.getData(), path);
		return "finisched: "+new Date();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Image get(@RequestParam("name") String name) {
		log.info(String.format("/GET info: imageName = %s", name));
		String imagePath = "/home/pszczola/tmp/" + name;
		String imageBase64 = Base64Coder.encoder(imagePath);
		if (imageBase64 != null) {
			Image image = new Image(name, imageBase64);
			return image;
		}
		return null;
	}
}
