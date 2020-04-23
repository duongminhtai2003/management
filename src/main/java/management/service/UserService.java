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
     * @param entity
     * @throws ApiValidateException
     */
    public void createUser(UserEntity entity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @return Object UserDto
     */
    public UserDto getUser();

    /**
     * @author: (VNEXT) TaiDM
     * @param userEntity
     * @throws ApiValidateException
     */
    public void updateUser(UserEntity userEntity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Map phone password
     * @throws ApiValidateException
     */
    public Map<String, String> login(String json) throws ApiValidateException;
}
