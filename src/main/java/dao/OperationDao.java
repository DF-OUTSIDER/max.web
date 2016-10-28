package dao;

import common.PaginationData;
import model.Operation;

import java.util.List;

public interface OperationDao {
    List<Operation> findAllOperations();

    PaginationData<Operation> findOperations(String action, int pageIndex, int pageSize);

    Operation findOperationById(int id);

    void saveOperation(Operation operation);

    void updateOperation(Operation operation);

    void deleteOperation(Operation operation);
}
