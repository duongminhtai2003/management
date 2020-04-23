/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import management.bean.ResultBean;
import management.model.AccountEntity;
import management.service.AccountService;
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
public class AccountController {

    @Autowired
    private AccountService service;

    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object ResultBean
     */
    @PostMapping(value = "/account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> createAccount(@RequestBody AccountEntity entity) {
        LOGGER.info("------addAccount START--------------");

        try {
            service.createAccount(entity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------addAccount END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Registration" })), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Long balance
     */
    @GetMapping(value = "/account", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> checkBalance(@RequestBody AccountEntity accountEntity) {
        LOGGER.info("------getBalance START--------------");

        Long balance = 0L;
        try {
            balance = service.checkBalance(accountEntity);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------getBalance END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(balance, Constant.OK, MessageUtils.getMessage("MSG01", new Object[] { "Check balance" })),
                HttpStatus.OK);
    }
}
