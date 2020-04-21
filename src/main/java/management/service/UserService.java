/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service;

import java.util.Map;

import management.dto.UserDto;
import management.model.UserEntity;
import management.utils.ApiValidateException;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) TaiDM
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/19      (VNEXT) TaiDM       Create new
*/
public interface UserService {

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @throws ApiValidateException 
     */
    public void createUser(UserEntity entity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    public UserDto findInfoUserById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public UserEntity getUserById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @throws ApiValidateException 
     */
    public void updateUser(Integer id, UserEntity entity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     * @return
     * @throws ApiValidateException
     */
    public Map<String, String> login(String json) throws ApiValidateException;
}
