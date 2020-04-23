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

import management.dao.BankDao;
import management.model.BankEntity;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) TaiDM
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/22      (VNEXT) TaiDM       Create new
*/

@Transactional
@Repository
public class BankDaoImpl implements BankDao {

    private static final Logger LOGGER = LogManager.getLogger(BankDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @return Object BankEntity
     */
    @Override
    public BankEntity getBankById(Integer bankId) {
        LOGGER.info("------getBankById START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT b");
        sql.append(" FROM ");
        sql.append("    BankEntity b ");
        sql.append(" WHERE ");
        sql.append("    b.bankId = :bankId ");
        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("bankId", bankId);
        BankEntity entity = null;
        try {
            entity = (BankEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getBankById END--------------");
        return entity;
    }

}
