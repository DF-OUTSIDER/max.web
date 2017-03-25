package service;

import model.FileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repository.FileTemplateRepository;

@Service
public class FileTemplateServiceImpl implements FileTemplateService {
    @Autowired
    private FileTemplateRepository fileTemplateRepository;

    @Override
    public Page<FileTemplate> findFileTemplate(int pageIndex, int pageSize) {
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        return fileTemplateRepository.findAll(pageable);
    }
}
