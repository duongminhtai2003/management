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

import management.dao.UserDao;
import management.model.UserEntity;

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
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void createUser(UserEntity entity) {
        LOGGER.info("------createUser START--------------");

        entityManager.persist(entity);

        LOGGER.info("------createUser END--------------");
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void updateUser(UserEntity entity) {
        LOGGER.info("------updateUser START--------------");

        entityManager.merge(entity);

        LOGGER.info("------updateUser END--------------");
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object UserEntity
     */
    @Override
    public UserEntity getUserById(Integer id) {
        LOGGER.info("------getUserById START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u");
        sql.append(" FROM ");
        sql.append("    UserEntity u ");
        sql.append(" WHERE ");
        sql.append("    u.userId = :id ");
        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        UserEntity entity = null;
        try {
            entity = (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(123);
            e.printStackTrace();
        }

        LOGGER.info("------getUserById END--------------");
        return entity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param phone
     * @return Object UserEntity
     */
    @Override
    public UserEntity getUserByPhone(String phone) {
        LOGGER.info("------getUserByPhone START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u");
        sql.append(" FROM ");
        sql.append("    UserEntity u ");
        sql.append(" WHERE ");
        sql.append("    u.phone = :phone ");
        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("phone", phone);
        UserEntity entity = null;
        try {
            entity = (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getUserByPhone END--------------");
        return entity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param code
     * @return Object UserEntity
     */
    @Override
    public UserEntity getUserByCode(Integer code) {
        LOGGER.info("------getUserByCode START--------------");

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u");
        sql.append(" FROM ");
        sql.append("    UserEntity u ");
        sql.append(" WHERE ");
        sql.append("    u.code = :code ");
        Query query = this.entityManager.createQuery(sql.toString());
        query.setParameter("code", code);
        UserEntity entity = null;
        try {
            entity = (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        LOGGER.info("------getUserByCode END--------------");
        return entity;
    }

}
