/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import management.dao.UserDao;
import management.dto.UserDto;
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

    @Autowired
    private EntityManager entityManager;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void createUser(UserEntity entity) {
        entityManager.persist(entity);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    @Override
    public void updateUser(UserEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public UserDto findInfoUserById(Integer id) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT new management.model.dto.UserDto( ");
//        sql.append(" u. ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append(" FROM ");
//        sql.append("    UserDto u ");
//        sql.append(" WHERE ");
//        sql.append("    u.userId = :userId ");
//        Query query = this.entityManager.createQuery(sql.toString());
//        query.setParameter("userId", userId);
//        UserDto userDto = null;
//        try {
//            userDto = (UserDto) query.getSingleResult();
//        } catch (NoResultException e) {
//            e.printStackTrace();
//                }
        return null;
    }

    @Override
    public UserEntity getUserById(Integer id) {
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
            e.printStackTrace();
        }
        return entity;
    }

}
