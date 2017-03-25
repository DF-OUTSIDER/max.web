package service;

import model.FileTemplate;
import org.springframework.data.domain.Page;

public interface FileTemplateService {
    Page<FileTemplate> findFileTemplate(int pageIndex, int pageSize);
}
