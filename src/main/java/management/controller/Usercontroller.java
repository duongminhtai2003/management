/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import management.bean.ResultBean;
import management.dto.UserDto;
import management.model.UserEntity;
import management.service.UserService;
import management.utils.ApiValidateException;
import management.utils.Constant;
import management.utils.MessageUtils;

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

@RestController
@RequestMapping(value = "/api")
public class Usercontroller {

    @Autowired
    private UserService service;

    private static final Logger LOGGER = LogManager.getLogger(Usercontroller.class);

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object ResultBean
     */
    @PostMapping(value = "/register", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createUser(@RequestBody UserEntity entity) {
        LOGGER.info("------createUser START--------------");

        try {
            service.createUser(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------createUser END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Register" })), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     * @return Object ResultBean
     */
    @PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> login(@RequestBody String entity) {
        LOGGER.info("------login START--------------");

        Map<String, String> result = null;
        try {
            result = service.login(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------login END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(result, Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Login" })), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object ResultBean
     */
    @PutMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> updateUser(@RequestBody UserEntity userEntity) {
        LOGGER.info("------updateUser START--------------");

        try {
            service.updateUser(userEntity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------updateUser END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Update" })), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @return Object UserDto
     */
    @GetMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getUser() {
        LOGGER.info("------getUser START--------------");

        UserDto userDto = null;
        try {
            userDto = service.getUser();
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------getUser END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(userDto, Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Get user" })),
                HttpStatus.OK);
    }
}
