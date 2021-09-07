package beathub.api.controller;

import beathub.api.model.Commit;
import beathub.api.model.Project;
import beathub.api.service.CommitService;
import beathub.api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commit")
public class CommitController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProjectService projectService;
    private final CommitService commitService;

    @Autowired
    public CommitController(ProjectService projectService, CommitService commitService) {
        this.projectService = projectService;
        this.commitService = commitService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getCommitsByProjectId(@PathVariable Long projectId) {
        logger.info("[START] Get commits by project id {}", projectId);

        if (!projectService.projectExists(projectId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project with id " + projectId + "!");
        }

        List<Commit> commitList;
        try {
            commitList = commitService.getCommitsByProjectId(projectId);
        } catch (DataAccessException e) {
            logger.error("[ERROR] Get commits by project id {} with message {}", projectId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("[STOP] Get commits by project id {}", projectId);
        return ResponseEntity.status(HttpStatus.OK).body(commitList);
    }
}
