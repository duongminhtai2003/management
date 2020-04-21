package management.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping(value = "/api")
public class Usercontroller {

    @Autowired
    private UserService service;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    @PostMapping(value = "/registration", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createUser(@RequestBody UserEntity entity) {
        try {
            service.createUser(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultBean>(new ResultBean(Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Registration" })), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    @PutMapping(value = "/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> updateUser(@RequestBody UserEntity entity, @PathVariable Integer id) {
        try {
            service.updateUser(id, entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultBean>(new ResultBean("201", "update success"), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getUser(@PathVariable Integer id) {
        UserDto userDto = service.findInfoUserById(id);
        return new ResponseEntity<ResultBean>(new ResultBean(userDto, "201", "get success"), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     * @return
     */
    @PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> login(@RequestBody String entity) {
        Map<String, String> result = null;
        try {
            result = service.login(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultBean>(new ResultBean("500", "Internal server error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultBean>(new ResultBean(result, "200", "login success"), HttpStatus.OK);
    }
}
