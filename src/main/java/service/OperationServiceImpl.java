package service;

import model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import repository.OperationRepository;

import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Operation> findAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Operation> findOperations(String action, Integer pageIndex, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
        if (StringUtils.hasText(action))
            return operationRepository.findByActionContains(action, pageable);
        else
            return operationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Operation findOperationById(int id) {
        return operationRepository.findOne(id);
    }

    @Override
    @Transactional
    public void saveOperation(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    @Transactional
    public void deleteOperation(Operation operation) {
        operationRepository.delete(operation);
    }
}
