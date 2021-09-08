package beathub.api.repository;

import beathub.api.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommitRepository extends JpaRepository<Commit, Long> {
}
