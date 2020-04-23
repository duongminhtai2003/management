/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao;

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
public interface UserDao {

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    public void createUser(UserEntity entity);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object UserEntity
     */
    public UserEntity getUserById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    public void updateUser(UserEntity entity);

    /**
     * @author: (VNEXT) TaiDM
     * @param phone
     * @return Object UserEntity
     */
    public UserEntity getUserByPhone(String phone);

    /**
     * @author: (VNEXT) TaiDM
     * @param code
     * @return Object UserEntity
     */
    public UserEntity getUserByCode(Integer code);

}
