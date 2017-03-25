package repository;

import model.FileTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTemplateRepository extends JpaRepository<FileTemplate, Long> {
}
