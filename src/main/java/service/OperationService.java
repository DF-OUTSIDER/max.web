package service;

import model.Operation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperationService {
    List<Operation> findAllOperations();

    Page<Operation> findOperations(String action, Integer pageIndex, Integer pageSize);

    Operation findOperationById(int id);

    void saveOperation(Operation operation);

    void deleteOperation(Operation operation);
}
