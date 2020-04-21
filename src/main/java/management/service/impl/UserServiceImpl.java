/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.bridge.MessageUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import management.bean.UserDetail;
import management.dao.AccountDao;
import management.dao.UserDao;
import management.dto.UserDto;
import management.jwt.JwtTokenProvider;
import management.model.UserEntity;
import management.service.UserService;
import management.utils.ApiValidateException;
import management.utils.Constant;
import management.utils.MessageUtils;
import management.utils.Regex;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void createUser(UserEntity entity) throws ApiValidateException {
        String fullname = entity.getFullname().trim();
        // valiadate fullname
        if (!fullname.matches(Regex.NAME_PATTERN)) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR6"));
        }

        String birthday = entity.getBirthday().trim();
        // validate birthday
        if (!birthday.matches(Regex.DATE_PATTERN)) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR8"));
        }

        String phone = entity.getPhone().trim();
        // validate phone
        if (!phone.matches(Regex.PHONE_PATTERN)) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR5"));
        }
        // check phone in db
        if (userDao.getUserByPhone(phone) != null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR1", new Object[] { "SĐT" }));
        }

        String password = entity.getPassword().trim();
        // validate password
        if (!password.matches(Regex.PASSWORD_PATTERN)) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR7"));
        }

        Integer code = entity.getCode();
        // validate code
        if (!code.toString().matches(Regex.CODE_PATTERN)) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR9"));
        }
        // check code in db
        if (userDao.getUserByCode(code) != null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR1", new Object[] { "Số CMND" }));
        }
        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
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

    @Override
    public Map<String, String> login(String json) throws ApiValidateException {
        JSONObject jsonObject = new JSONObject(json);
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        String tmp = "";
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            tmp = tokenProvider.generateToken((UserDetail) authentication.getPrincipal());
        } catch (Exception e) {
            throw new ApiValidateException("400", "login fail");
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("type", "Bearer");
        result.put("token", tmp);
        return result;
    }
}
