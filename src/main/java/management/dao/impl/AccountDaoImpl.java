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

import management.dao.AccountDao;
import management.dto.AccountDto;
import management.dto.AccountTransactionDto;
import management.model.AccountEntity;

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
@Transactional
@Repository
public class AccountDaoImpl implements AccountDao {

    private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void createAccount(AccountEntity entity) {
        LOGGER.info("------createAccount START--------------");

        entityManager.persist(entity);

        LOGGER.info("------createAccount END--------------");
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object AccountEntity
     */
    @Override
    public AccountEntity getAccountById(Integer id) {
        LOGGER.info("------getAccountById START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT a");
        sql.append(" FROM ");
        sql.append("    AccountEntity a ");
        sql.append(" WHERE ");
        sql.append("    a.accountId = :id ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        AccountEntity entity = null;
        try {
            entity = (AccountEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getAccountById END--------------");
        return entity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param userId
     * @param bankId
     * @return Object AccountEntity
     */
    @Override
    public AccountEntity getAccount(Integer userId, Integer bankId) {
        LOGGER.info("------getAccount START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT a");
        sql.append(" FROM ");
        sql.append("    AccountEntity a ");
        sql.append(" WHERE ");
        sql.append("    a.userId = :userId ");
        sql.append(" AND ");
        sql.append("    a.bankId = :bankId ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("bankId", bankId);
        AccountEntity entity = null;
        try {
            entity = (AccountEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getAccount END--------------");
        return entity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void updateAccount(AccountEntity entity) {
        LOGGER.info("------updateAccount START--------------");

        entityManager.merge(entity);

        LOGGER.info("------updateAccount END--------------");
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return List listAccount
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountDto> getListAccountByUserId(Integer id) {
        LOGGER.info("------getListAccountByUserId START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new management.dto.AccountDto(");
        sql.append("    b.bankName, ");
        sql.append("    a.balance) ");
        sql.append(" FROM ");
        sql.append("    AccountEntity a ");
        sql.append(" INNER JOIN ");
        sql.append("    BankEntity b ");
        sql.append("    ON b.bankId = a.bankId ");
        sql.append(" WHERE ");
        sql.append("    a.userId = :id ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        List<AccountDto> listEntity = null;
        try {
            listEntity = query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getListAccountByUserId END--------------");
        return listEntity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object AccountTransactionDto
     */
    @Override
    public AccountTransactionDto getAccountTransaction(Integer id) {
        LOGGER.info("------getAccountTransaction START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new management.dto.AccountTransactionDto( ");
        sql.append(" u.fullname, ");
        sql.append(" b.bankName) ");
        sql.append(" FROM ");
        sql.append("    AccountEntity a ");
        sql.append("        INNER JOIN ");
        sql.append("    BankEntity b ON b.bankId = a.bankId ");
        sql.append("        INNER JOIN ");
        sql.append("    UserEntity u ON u.userId = a.userId ");
        sql.append(" WHERE ");
        sql.append("    a.accountId = :id ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        AccountTransactionDto accountTransactionDto = null;
        try {
            accountTransactionDto = (AccountTransactionDto) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getAccountTransaction END--------------");
        return accountTransactionDto;
    }

}
