/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import management.bean.UserDetail;
import management.dao.UserDao;
import management.model.UserEntity;
import management.utils.ApiValidateException;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT)TaiDM
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/19      (VNEXT)TaiDM       Create new
*/
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    //private static final Logger LOGGER = LogManager.getLogger(BankController.class);

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //        LOGGER.info("------loadUserByUsername START--------------");
        UserEntity user = userDao.getUserByPhone(phone);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(phone);
        }
        //        LOGGER.info("------loadUserByUsername END--------------");
        return new UserDetail(user);
    }

    /**
     * @author: (VNEXT)TaiDM
     * @param id
     * @return
     * @throws ApiValidateException
     */
    public UserDetails loadUserById(Integer id) throws ApiValidateException {
        //        LOGGER.info("------loadUserById START--------------");
        UserEntity userEntity = userDao.getUserById(id);
        //        LOGGER.info("------loadUserById END--------------");
        return new UserDetail(userEntity);
    }
}
