package beathub.api.controller;

import beathub.api.annotation.ShowAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@ShowAPI
@RequestMapping("/projects")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/new")
    public ResponseEntity<Object> newProject(@RequestParam("file") MultipartFile file,
                                             @RequestParam(name = "json", required = false) String json) {
        if (file != null) {
            try {
                logger.info(new String(file.getBytes()));
                logger.info(json);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully received file and json!");
    }

}
