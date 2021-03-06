/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import management.dao.FeeDao;
import management.model.FeeEntity;

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
public class FeeDaoImpl implements FeeDao {

    private static final Logger LOGGER = LogManager.getLogger(FeeDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @param moneyTransaction
     * @return Object FeeEntity
     */
    @Override
    public FeeEntity getFee(Integer bankId, Long moneyTransaction) {
        LOGGER.info("------getFee START--------------");

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

        LOGGER.info("------getFee END--------------");
        return entityResult;
    }

}
