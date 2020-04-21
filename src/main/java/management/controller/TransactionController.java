package management.controller;

import java.util.ArrayList;
import java.util.List;

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

@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    @Autowired
    private TransactionService service;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    @PostMapping(value = "/transaction/withdrawal/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> withdrawal(@RequestBody String json, @PathVariable Integer id) {
        TransactionDto transactionDto = null;
        try {
            transactionDto = service.withdrawal(id, json);
        } catch (ApiValidateException e) {
            return new ResponseEntity<ResultBean>(new ResultBean(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultBean>(new ResultBean(transactionDto, "201", "withdrawal success"), HttpStatus.CREATED);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @param id
     * @return
     */
    @PostMapping(value = "/transaction/payin/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> payin(@RequestBody String json, @PathVariable Integer id) {
        TransactionDto transactionDto = null;
        transactionDto = service.payin(id, json);
        return new ResponseEntity<ResultBean>(new ResultBean(transactionDto, "201", "payin success"), HttpStatus.CREATED);
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @param id
     * @return
     */
    @PostMapping(value = "/transaction/transfers/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> transfers(@RequestBody String json, @PathVariable Integer id) {
        TransactionTransferDto transactionTransferDto = null;
        try {
            transactionTransferDto = service.transfers(id, json);
        } catch (ApiValidateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<ResultBean>(new ResultBean(transactionTransferDto, "201", "transfers success"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> getListTransaction(@PathVariable Integer id) {
        List<TransactionTransferDto> listTransacions = new ArrayList<TransactionTransferDto>();
        listTransacions = service.getListTransactionByAccountId(id);
        return new ResponseEntity<ResultBean>(new ResultBean(listTransacions, "201", "transfers success"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction/csv/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResultBean> outputCSVFile(@PathVariable Integer id) {
         String file = service.outputTransactionToCSV(id);
        return new ResponseEntity<ResultBean>(new ResultBean(file, "200", "output success"), HttpStatus.CREATED);
    }

}
