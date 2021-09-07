package beathub.api.controller;

import beathub.api.annotation.ShowAPI;
import beathub.api.model.Project;
import beathub.api.service.AccountService;
import beathub.api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@ShowAPI
@RequestMapping("/projects")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProjectService projectService;
    private final AccountService accountService;

    @Autowired
    public ProjectController(ProjectService projectService, AccountService accountService) {
        this.projectService = projectService;
        this.accountService = accountService;
    }

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

    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getProjects(@PathVariable("accountId") Long accountId) {
        logger.info("[START] Get projects by account id {}", accountId);

        if (!accountService.accountExists(accountId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account with id " + accountId + "!");
        }

        List<Project> projectList;
        try {
            projectList = projectService.getProjectsByAccountId(accountId);
        } catch (DataAccessException e) {
            logger.error("[ERROR] Get projects by account id {} with message {}", accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("[STOP] Get projects by account id {}", accountId);
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

}
