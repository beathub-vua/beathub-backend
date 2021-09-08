package beathub.api.controller;

import beathub.api.exception.NoSuchProjectException;
import beathub.api.model.Commit;
import beathub.api.model.dto.CommitDto;
import beathub.api.service.CommitService;
import beathub.api.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
@RequestMapping("/commits")
public class CommitController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProjectService projectService;
    private final CommitService commitService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommitController(ProjectService projectService, CommitService commitService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.commitService = commitService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<Object> commit(@RequestParam("file") MultipartFile file,
                                         @RequestParam(name = "commit", required = false) String jsonCommit,
                                         @RequestParam("projectId") Long projectId) {
        Commit commit;
        try {
            logger.info("[START] Parsing commit json to object");
            commit = objectMapper.readValue(jsonCommit, Commit.class);
            logger.info("[STOP] Parsing commit json to object");
        } catch (JsonProcessingException e) {
            logger.error("[ERROR] Parsing commit json with message ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

        try {
            logger.info("[START] Saving commit object to database");
            commitService.saveCommit(commit, projectId);
            logger.info("[STOP] Saving commit object to database");
        } catch (NoSuchProjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("[ERROR] Saving commit object to database with message ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully received file and json!");
    }

    @GetMapping
    public ResponseEntity<Object> getCommitsByProjectId(@RequestParam Long projectId) {
        logger.info("[START] Get commits by project id {}", projectId);

        if (!projectService.projectExists(projectId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project with id " + projectId + "!");
        }

        List<CommitDto> commitList;
        try {
            commitList = CommitDto.fromList(commitService.getCommitsByProjectId(projectId));
        } catch (DataAccessException e) {
            logger.error("[ERROR] Get commits by project id {} with message {}", projectId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("[STOP] Get commits by project id {}", projectId);
        return ResponseEntity.status(HttpStatus.OK).body(commitList);
    }

    @GetMapping("/{commitId}")
    public ResponseEntity<Object> getCommit(@PathVariable Long commitId) {
        logger.info("[START] Get commit by id {}", commitId);
        CommitDto commitDto;
        try {
            commitDto = CommitDto.from(commitService.getCommitById(commitId));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("[ERROR] Get commit by id {} with message {}", commitId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataAccessException e) {
            logger.error("[ERROR] Get commit by id {} with message {}", commitId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("[STOP] Get commit by id {}", commitId);
        return ResponseEntity.status(HttpStatus.OK).body(commitDto);
    }
}
