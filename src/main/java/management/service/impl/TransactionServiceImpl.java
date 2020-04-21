/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import management.dao.AccountDao;
import management.dao.FeeDao;
import management.dao.TransactionDao;
import management.dto.TransactionDto;
import management.dto.TransactionTransferDto;
import management.model.AccountEntity;
import management.model.FeeEntity;
import management.model.TransactionEntity;
import management.service.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private FeeDao feeDao;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<TransactionTransferDto> getTransactionByDate(String json) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TransactionTransferDto> getListTransactionByAccountId(Integer accountId) {
        List<TransactionTransferDto> listTransactions = new ArrayList<TransactionTransferDto>();

        for (TransactionEntity entity : transactionDao.getAllTransactionByAccountId(accountId)) {
            TransactionTransferDto dto = new TransactionTransferDto();
            if (entity.getTypeTransaction() == 0 || entity.getTypeTransaction() == 1) {
                dto.setAccountTransactionDto(null);
                dto.setTransactionDto(new TransactionDto(entity.getMoneyTransaction(), entity.getDateTransaction(), entity.getTypeTransaction()));
            }
            if (entity.getTypeTransaction() == 2) {
                dto.setAccountTransactionDto(accountDao.getAccountTransaction(entity.getToAccountId()));
                dto.setTransactionDto(new TransactionDto(entity.getMoneyTransaction(), entity.getDateTransaction(), entity.getTypeTransaction()));
            }
            if (entity.getTypeTransaction() == 3) {
                dto.setAccountTransactionDto(accountDao.getAccountTransaction(entity.getFromAccountId()));
                dto.setTransactionDto(new TransactionDto(entity.getMoneyTransaction(), entity.getDateTransaction(), entity.getTypeTransaction()));

            }
            listTransactions.add(dto);
        }
        return listTransactions;
    }

    @Override
    public String outputTransactionToCSV(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionDto withdrawal(Integer accountId, String json) throws ApiValidateException {
        JSONObject jsonObject = new JSONObject(json);
        TransactionEntity transactionEntity = new TransactionEntity();
        AccountEntity accountEntity = accountDao.getAccountById(accountId);
        Long moneyTransaction = jsonObject.getLong("moneyTransaction");
        FeeEntity feeEntity = feeDao.getFee(accountEntity.getBankId(), moneyTransaction);
        Long balance = (long) (accountEntity.getBalance() - moneyTransaction - feeEntity.getMoneyFee() - moneyTransaction * feeEntity.getPercentFee() / 100);
        if (balance >= 50000) {

            // create transaction
            transactionEntity.setDateTransaction(new java.sql.Timestamp(new java.util.Date().getTime()));
            transactionEntity.setMoneyTransaction(accountEntity.getBalance() - balance);
            transactionEntity.setToAccountId(null);
            transactionEntity.setAccountId(accountId);
            transactionEntity.setTypeTransaction(0);
            transactionDao.createTransaction(transactionEntity);

            // update balance in account
            accountEntity.setBalance(balance);
            accountDao.updateAccount(accountEntity);

        } else {
            throw new ApiValidateException("404", "withdrawal fail");
        }
        return transactionDao.getTransactionById(transactionEntity.getTransactionId());
    }

    @Override
    public TransactionDto payin(Integer accountId, String json) {
        JSONObject jsonObject = new JSONObject(json);
        TransactionEntity transactionEntity = new TransactionEntity();
        AccountEntity accountEntity = accountDao.getAccountById(accountId);
        Long moneyTransaction = jsonObject.getLong("moneyTransaction");
        Long balance = accountEntity.getBalance() + moneyTransaction;

        // create transaction
        transactionEntity.setDateTransaction(new java.sql.Timestamp(new java.util.Date().getTime()));
        transactionEntity.setMoneyTransaction(moneyTransaction);
        transactionEntity.setToAccountId(null);
        transactionEntity.setAccountId(accountId);
        transactionEntity.setTypeTransaction(1);
        transactionDao.createTransaction(transactionEntity);

        // update balance in account
        accountEntity.setBalance(balance);
        accountDao.updateAccount(accountEntity);

        return transactionDao.getTransactionById(transactionEntity.getTransactionId());
    }

    @Override
    public TransactionTransferDto transfers(Integer accountId, String json) throws ApiValidateException {
        JSONObject jsonObject = new JSONObject(json);
        TransactionEntity transactionEntity1 = new TransactionEntity();
        TransactionEntity transactionEntity2 = new TransactionEntity();
        Long moneyTransaction = jsonObject.getLong("moneyTransaction");
        Integer toAccountId = jsonObject.getInt("toAccountId");
        // get account-transfer
        AccountEntity accountTransfer = accountDao.getAccountById(accountId);
        //get account-reciever
        AccountEntity accountReciever = accountDao.getAccountById(toAccountId);

        FeeEntity feeEntity = feeDao.getFee(accountTransfer.getBankId(), moneyTransaction);
        Long balance = (long) (accountTransfer.getBalance() - moneyTransaction - feeEntity.getMoneyFee() - moneyTransaction * feeEntity.getPercentFee() / 100);
        if (balance >= 50000) {

            // create account-transfer's transaction
            transactionEntity1.setDateTransaction(new java.sql.Timestamp(new java.util.Date().getTime()));
            transactionEntity1.setMoneyTransaction(accountTransfer.getBalance() - balance);
            transactionEntity1.setToAccountId(toAccountId);
            transactionEntity1.setAccountId(accountId);
            transactionEntity1.setTypeTransaction(2);
            transactionDao.createTransaction(transactionEntity1);

            // create account-reciever's transaction
            transactionEntity2.setDateTransaction(new java.sql.Timestamp(new java.util.Date().getTime()));
            transactionEntity2.setMoneyTransaction(moneyTransaction);
            transactionEntity2.setFromAccountId(accountId);
            transactionEntity2.setAccountId(toAccountId);
            transactionEntity2.setTypeTransaction(3);
            transactionDao.createTransaction(transactionEntity2);

            // update account-transfer's balance
            accountTransfer.setBalance(balance);
            accountDao.updateAccount(accountTransfer);

            // update account-reciever's balance
            accountReciever.setBalance(accountReciever.getBalance() + moneyTransaction);
            accountDao.updateAccount(accountReciever);

        } else {
            throw new ApiValidateException("404", "withdrawal fail");
        }
        TransactionTransferDto transactionTransferDto = new TransactionTransferDto();
        transactionTransferDto.setTransactionDto(transactionDao.getTransactionById(transactionEntity1.getTransactionId()));
        transactionTransferDto.setAccountTransactionDto(accountDao.getAccountTransaction(toAccountId));
        return transactionTransferDto;
    }

}
