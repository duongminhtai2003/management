package management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import management.bean.ResultBean;
import management.model.AccountEntity;
import management.service.AccountService;
import management.utils.ApiValidateException;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    private AccountService service;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    @PostMapping(value = "/account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createAccount(@RequestBody AccountEntity entity) {
        try {
            service.createAccount(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultBean>(new ResultBean("201", "create success"), HttpStatus.CREATED);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    @GetMapping(value = "/account/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> checkBalance(@PathVariable Integer id) {
        Long balance = 0L;
        balance = service.checkBalance(id);
        return new ResponseEntity<ResultBean>(new ResultBean(balance, "200", "check balance success"), HttpStatus.OK);
    }
}
