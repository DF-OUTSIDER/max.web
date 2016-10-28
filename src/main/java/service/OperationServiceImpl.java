package service;

import common.PaginationData;
import dao.OperationDao;
import model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationDao operationDao;

    @Override
    public List<Operation> findAllOperations() {
        return operationDao.findAllOperations();
    }

    @Override
    public PaginationData<Operation> findOperations(String action, Integer pageIndex, Integer pageSize) {
        if (pageIndex == null)
            pageIndex = 1;
        if (pageSize == null)
            pageSize = 10;
        return operationDao.findOperations(action, pageIndex, pageSize);
    }

    @Override
    public Operation findOperationById(int id) {
        return operationDao.findOperationById(id);
    }

    @Override
    @Transactional
    public void saveOperation(Operation operation) {
        operationDao.saveOperation(operation);
    }

    @Override
    @Transactional
    public void updateOperation(Operation operation) {
        operationDao.updateOperation(operation);
    }

    @Override
    @Transactional
    public void deleteOperation(Operation operation) {
        operationDao.deleteOperation(operation);
    }
}
