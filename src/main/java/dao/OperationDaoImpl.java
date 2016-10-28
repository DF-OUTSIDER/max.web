package dao;

import common.PaginationData;
import model.Operation;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class OperationDaoImpl implements OperationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Operation> findAllOperations() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Operation order by id")
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaginationData<Operation> findOperations(String action, int pageIndex, int pageSize) {
        List<Operation> operations;
        Number totalCount = 0;
        if (StringUtils.hasText(action)) {
            operations = sessionFactory
                    .getCurrentSession()
                    .createQuery("from Operation o where o.action like :action order by o.id")
                    .setParameter("action", String.format("%%%s%%", action))
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
            totalCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery("select count (o.id) from Operation o where o.action like :action")
                    .setParameter("action", String.format("%%%s%%", action))
                    .getSingleResult();
        } else {
            operations = sessionFactory
                    .getCurrentSession()
                    .createQuery("from Operation o order by  o.id")
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
            totalCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery("select count (id) from Operation")
                    .getSingleResult();
        }
        PaginationData<Operation> result = new PaginationData<>(pageSize, pageIndex, operations, totalCount.intValue());
        return result;
    }

    @Override
    public Operation findOperationById(int id) {
        return (Operation) sessionFactory
                .getCurrentSession()
                .createQuery("from Operation where id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void saveOperation(Operation operation) {
        sessionFactory
                .getCurrentSession()
                .save(operation);
    }

    @Override
    public void updateOperation(Operation operation) {
        sessionFactory
                .getCurrentSession()
                .update(operation);
    }

    @Override
    public void deleteOperation(Operation operation) {
        sessionFactory
                .getCurrentSession()
                .delete(operation);
    }
}
