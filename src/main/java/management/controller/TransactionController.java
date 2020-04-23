/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import management.dto.TransactionDto;
import management.dto.TransactionTransferDto;
import management.service.TransactionService;
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
public class TransactionController {

    @Autowired
    private TransactionService service;

    private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionDto
     */
    @PostMapping(value = "/transaction/withdrawal", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> withdrawal(@RequestBody String json) {
        LOGGER.info("------withdrawal START--------------");

        TransactionDto transactionDto = null;
        try {
            transactionDto = service.withdrawal(json);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------withdrawal END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(transactionDto, Constant.OK, MessageUtils.getMessage("MSG01", "Rút tiền")), HttpStatus.CREATED);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @param id
     * @return Object TransactionDto
     */
    @PostMapping(value = "/transaction/payin", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> payin(@RequestBody String json) {
        LOGGER.info("------payin START--------------");

        TransactionDto transactionDto = null;
        try {
            transactionDto = service.payin(json);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------payin END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(transactionDto, Constant.OK, MessageUtils.getMessage("MSG01", "Nạp tiền")), HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionTransferDto
     */
    @PostMapping(value = "/transaction/transfers", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> transfers(@RequestBody String json) {
        LOGGER.info("------transfers START--------------");

        TransactionTransferDto transactionTransferDto = null;
        try {
            transactionTransferDto = service.transfers(json);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------transfers END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(transactionTransferDto, Constant.OK, MessageUtils.getMessage("MSG01", "Chuyển tiền")),
                HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @return List listTransacions
     */
    @GetMapping(value = "/transaction/{bankId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getListTransactionBank(@PathVariable Integer bankId) {
        LOGGER.info("------getListTransactionBank START--------------");

        List<TransactionTransferDto> listTransacions = null;
        try {
            listTransacions = service.getListTransactionBank(bankId);
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------getListTransactionBank END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(listTransacions, Constant.OK, MessageUtils.getMessage("MSG01", "Hiển thị giao dịch")),
                HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @return List listTransacions
     */
    @GetMapping(value = "/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getListTransactionUser() {
        LOGGER.info("------getListTransactionUser START--------------");

        List<TransactionTransferDto> listTransacions = null;
        try {
            listTransacions = service.getTransactionUser();
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------getListTransactionUser END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(listTransacions, Constant.OK, MessageUtils.getMessage("MSG01", "Hiển thị giao dịch")),
                HttpStatus.OK);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @return String file
     */
    @GetMapping(value = "/transaction/csv", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> outputCSVFile() {
        LOGGER.info("------outputCSVFile START--------------");

        String file = null;
        try {
            file = service.outputTransactionToCSV();
        } catch (Exception e) {
            return new ResponseEntity<ResultBean>(new ResultBean(Constant.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("MSG03")), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("------outputCSVFile END--------------");

        return new ResponseEntity<ResultBean>(new ResultBean(file, Constant.OK, MessageUtils.getMessage("MSG01", "Xuất file")), HttpStatus.CREATED);
    }

}
