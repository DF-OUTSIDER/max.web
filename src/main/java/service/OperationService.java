package service;

import common.PaginationData;
import model.Operation;

import java.util.List;

public interface OperationService {
    List<Operation> findAllOperations();

    PaginationData<Operation> findOperations(String action, Integer pageIndex, Integer pageSize);

    Operation findOperationById(int id);

    void saveOperation(Operation operation);

    void updateOperation(Operation operation);

    void deleteOperation(Operation operation);
}
