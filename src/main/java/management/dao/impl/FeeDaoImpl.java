package management.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import management.dao.FeeDao;
import management.model.FeeEntity;

@Transactional
@Repository
public class FeeDaoImpl implements FeeDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public FeeEntity getFee(Integer bankId, Long moneyTransaction) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT a");
        sql.append(" FROM ");
        sql.append("    FeeEntity a ");
        sql.append(" WHERE ");
        sql.append("    a.level = (SELECT ");
        sql.append("    MAX(level) ");
        sql.append(" FROM ");
        sql.append("    FeeEntity b ");
        sql.append(" WHERE ");
        sql.append("    (:moneyTransaction - b.level) > 0 ");
        sql.append("    AND b.bankId = a.bankId) ");
        sql.append("    AND a.bankId = :bankId ");
        sql.append(" GROUP BY a.bankId ");
        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("moneyTransaction", moneyTransaction);
        query.setParameter("bankId", bankId);
        FeeEntity entityResult = null;
        try {
            entityResult = (FeeEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return entityResult;
    }

}
