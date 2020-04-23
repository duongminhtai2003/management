/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import management.dao.TransactionDao;
import management.dto.TransactionDto;
import management.model.TransactionEntity;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) TaiDM
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/20      (VNEXT) TaiDM       Create new
*/
@Repository
@Transactional
public class TransactionDaoImpl implements TransactionDao {

    private static final Logger LOGGER = LogManager.getLogger(TransactionDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void createTransaction(TransactionEntity entity) {
        entityManager.persist(entity);

    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object TransactionDto
     */
    @Override
    public TransactionDto getTransactionById(Integer id) {
        LOGGER.info("------getTransactionById START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new management.dto.TransactionDto( ");
        sql.append(" t.moneyTransaction, ");
        sql.append(" t.dateTransaction, ");
        sql.append(" t.typeTransaction )");
        sql.append(" FROM ");
        sql.append("    TransactionEntity t ");
        sql.append(" WHERE ");
        sql.append("    t.transactionId = :id ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        TransactionDto transactionDto = null;
        try {
            transactionDto = (TransactionDto) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getTransactionById END--------------");
        return transactionDto;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param accountId
     * @return List TransactionEntity
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TransactionEntity> getAllTransactionBank(Integer accountId) {
        LOGGER.info("------getAllTransactionBank START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t ");
        sql.append(" FROM ");
        sql.append("    TransactionEntity t ");
        sql.append(" WHERE ");
        sql.append("    t.accountId = :accountId ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("accountId", accountId);
        List<TransactionEntity> listTransactions = null;
        try {
            listTransactions = query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getAllTransactionBank END--------------");
        return listTransactions;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param accountId
     * @return List TransactionEntity
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TransactionEntity> getAllTransactionUser(Integer userId) {
        LOGGER.info("------getAllTransactionUser START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t ");
        sql.append(" FROM ");
        sql.append("    TransactionEntity t ");
        sql.append(" INNER JOIN AccountEntity a ");
        sql.append("    ON t.accountId = a.accountId ");
        sql.append(" WHERE ");
        sql.append("    a.userId = :userId ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<TransactionEntity> listTransactions = null;
        try {
            listTransactions = query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getAllTransactionUser END--------------");
        return listTransactions;
    }

}
