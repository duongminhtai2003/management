/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import management.dao.AccountDao;
import management.dao.UserDao;
import management.dto.UserDto;
import management.model.UserEntity;
import management.service.UserService;
import management.utils.ApiValidateException;

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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public void createUser(UserEntity entity) throws ApiValidateException {
        if (entity == null) {
            throw new ApiValidateException("404", "Please enter all field");
        }
        userDao.createUser(entity);
    }

    @Override
    public UserDto findInfoUserById(Integer id) {
        UserDto userDto = new UserDto();
        userDto.setUserEntity(userDao.getUserById(id));
        userDto.setListAccounts(accountDao.getListAccountByUserId(id));
        return userDto;
    }

    @Override
    public UserEntity getUserById(Integer id) {
        UserEntity entity = userDao.getUserById(id);
        return entity;
    }

    @Override
    public void updateUser(Integer id, UserEntity entity) throws ApiValidateException {
        UserEntity entityOld = getUserById(id);
        if (entity == null) {
            throw new ApiValidateException("404", "Please enter all field");
        } else {
            if (entity.getFullname() == null) {
                entity.setFullname(entityOld.getFullname());
            }
            if (entity.getBirthday() == null) {
                entity.setBirthday(entityOld.getBirthday());
            }
            if (entity.getCode() == null) {
                entity.setCode(entityOld.getCode());
            }
            if (entity.getPhone() == null) {
                entity.setPhone(entityOld.getPhone());
            }
            if (entity.getPassword() == null) {
                entity.setPassword(entityOld.getPassword());
            }
            entity.setUserId(id);
        }
        userDao.updateUser(entity);
    }
}
