package beathub.api.controller;

import beathub.api.annotation.ShowAPI;
import beathub.api.model.Commit;
import beathub.api.model.Project;
import beathub.api.model.dto.ProjectDto;
import beathub.api.service.AccountService;
import beathub.api.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

@RestController
@ShowAPI
@RequestMapping("/projects")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProjectService projectService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, AccountService accountService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/new")
    public ResponseEntity<Object> newProject(@RequestParam("file") MultipartFile file,
                                             @RequestParam("commit") String jsonCommit,
                                             @RequestParam("project") String jsonProject,
                                             @RequestParam("accountId") Long accountId) {
        logger.info("[START] Create new project");
        Commit commit;
        Project project;
        try {
            logger.info("[START] Parsing commit and project from json to object");
            commit = objectMapper.readValue(jsonCommit, Commit.class);
            project = objectMapper.readValue(jsonProject, Project.class);
            logger.info("[STOP] Parsing commit json to object");
        } catch (JsonProcessingException e) {
            logger.error("[ERROR] Parsing commit and project from json with message ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

        try {
            projectService.newProject(commit, project, accountId);
        } catch (Exception e) {
            logger.error("[ERROR] Create new project with message", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<Object> getProjects(@RequestParam("accountId") Long accountId) {
        logger.info("[START] Get projects by account id {}", accountId);

        if (!accountService.accountExists(accountId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No account with id " + accountId + "!");
        }

        List<ProjectDto> projectDtoList;
        try {
            projectDtoList = ProjectDto.fromList(projectService.getProjectsByAccountId(accountId));
        } catch (DataAccessException e) {
            logger.error("[ERROR] Get projects by account id {} with message {}", accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("[STOP] Get projects by account id {}", accountId);
        return ResponseEntity.status(HttpStatus.OK).body(projectDtoList);
    }

}
