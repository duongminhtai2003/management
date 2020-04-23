/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import management.dao.AccountDao;
import management.dao.FeeDao;
import management.dao.TransactionDao;
import management.dao.UserDao;
import management.dto.TransactionDto;
import management.dto.TransactionTransferDto;
import management.model.AccountEntity;
import management.model.FeeEntity;
import management.model.TransactionEntity;
import management.model.UserEntity;
import management.service.TransactionService;
import management.utils.ApiValidateException;
import management.utils.Constant;
import management.utils.DataUtils;
import management.utils.MessageUtils;
import management.utils.RenameFile;

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

    private static final Logger LOGGER = LogManager.getLogger(TransactionServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private FeeDao feeDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;

    /**
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @return List TransactionTransferDto
     */
    @Override
    public List<TransactionTransferDto> getListTransactionBank(Integer bankId) {
        LOGGER.info("------getListTransactionBank START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());
        List<TransactionTransferDto> listTransactions = new ArrayList<TransactionTransferDto>();
        AccountEntity accountEntity = accountDao.getAccount(userLogin.getUserId(), bankId);
        for (TransactionEntity entity : transactionDao.getAllTransactionBank(accountEntity.getAccountId())) {
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

        LOGGER.info("------getListTransactionBank END--------------");
        return listTransactions;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @return List TransactionTransferDto
     */
    @Override
    public List<TransactionTransferDto> getTransactionUser() {
        LOGGER.info("------getTransactionUser START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());
        List<TransactionTransferDto> listTransactions = new ArrayList<TransactionTransferDto>();
        for (TransactionEntity entity : transactionDao.getAllTransactionUser(userLogin.getUserId())) {
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

        LOGGER.info("------getTransactionUser END--------------");
        return listTransactions;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @return String file
     */
    @Override
    public String outputTransactionToCSV() {
        LOGGER.info("------outputTransactionToCSV START--------------");

        List<TransactionTransferDto> listTransactions = getTransactionUser();
        String fileName = RenameFile.renameFile();
        String csvFile = "C:/Users/MinhTai/Documents/fileoutput/" + fileName + ".csv";
        String fullname = "";
        String bankName = "";
        String dateTransaction = "";
        String typeTransaction = "";
        Long moneyTransaction = 0L;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Date", "MoneyTransaction", "Type", "Fullname", "BankName"));) {
            for (TransactionTransferDto dto : listTransactions) {
                if (dto.getAccountTransactionDto() != null) {
                    fullname = dto.getAccountTransactionDto().getFullName();
                    bankName = dto.getAccountTransactionDto().getBankName();
                }
                if (dto.getTransactionDto() != null) {
                    dateTransaction = dto.getTransactionDto().getDateTransaction().toString();
                    moneyTransaction = dto.getTransactionDto().getMoneyTransaction();
                    if (dto.getTransactionDto().getTypeTransaction() == 0) {
                        typeTransaction = "Rut tien";
                    } else if (dto.getTransactionDto().getTypeTransaction() == 1) {
                        typeTransaction = "Nap tien";
                    } else if (dto.getTransactionDto().getTypeTransaction() == 2) {
                        typeTransaction = "Chuyen tien";
                    } else {
                        typeTransaction = "Nhan tien";
                    }
                }
                csvPrinter.printRecord(dateTransaction, moneyTransaction, typeTransaction, fullname, bankName);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("------outputTransactionToCSV END--------------");
        return csvFile;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionDto
     * @throws ApiValidateException
     */
    @Override
    public TransactionDto withdrawal(String json) throws ApiValidateException {
        LOGGER.info("------withdrawal START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());
        // get data from client
        JSONObject jsonObject = new JSONObject(json);
        Integer bankId = jsonObject.getInt("bankId");

        // get account transaction
        AccountEntity accountEntity = accountDao.getAccount(userLogin.getUserId(), bankId);

        // check bankId đã đăng ký cho userId chưa
        if (accountDao.getAccount(userLogin.getUserId(), accountEntity.getBankId()) == null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR2", new Object[] { "Ngân hàng" }));
        }

        Long moneyTransaction = jsonObject.getLong("moneyTransaction");

        // get fee transaction
        FeeEntity feeEntity = feeDao.getFee(accountEntity.getBankId(), moneyTransaction);

        TransactionEntity transactionEntity = new TransactionEntity();

        // get account's balance after transaction
        Long balance = (long) (accountEntity.getBalance() - moneyTransaction - feeEntity.getMoneyFee() - moneyTransaction * feeEntity.getPercentFee() / 100);

        // check balance
        if (balance >= Constant.BALANCE_MIN) {

            // create transaction
            transactionEntity.setDateTransaction(new Timestamp(new Date().getTime()));
            transactionEntity.setMoneyTransaction(accountEntity.getBalance() - balance);
            transactionEntity.setAccountId(accountEntity.getAccountId());
            transactionEntity.setTypeTransaction(0);
            transactionDao.createTransaction(transactionEntity);

            // update balance in account
            accountEntity.setBalance(balance);
            accountDao.updateAccount(accountEntity);

        } else {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR3"));
        }

        LOGGER.info("------withdrawal END--------------");
        return transactionDao.getTransactionById(transactionEntity.getTransactionId());
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionDto
     * @throws ApiValidateException
     */
    @Override
    public TransactionDto payin(String json) throws ApiValidateException {
        LOGGER.info("------payin START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());
        // get data from client
        JSONObject jsonObject = new JSONObject(json);
        Integer bankId = jsonObject.getInt("bankId");

        // get account transaction
        AccountEntity accountEntity = accountDao.getAccount(userLogin.getUserId(), bankId);

        // check bankId đã đăng ký cho userId chưa
        if (accountDao.getAccount(userLogin.getUserId(), accountEntity.getBankId()) == null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR2", new Object[] { "Ngân hàng" }));
        }

        Long moneyTransaction = jsonObject.getLong("moneyTransaction");

        Long balance = accountEntity.getBalance() + moneyTransaction;

        TransactionEntity transactionEntity = new TransactionEntity();

        // create transaction
        transactionEntity.setDateTransaction(new java.sql.Timestamp(new java.util.Date().getTime()));
        transactionEntity.setMoneyTransaction(moneyTransaction);
        transactionEntity.setAccountId(accountEntity.getAccountId());
        transactionEntity.setTypeTransaction(1);
        transactionDao.createTransaction(transactionEntity);

        // update balance in account
        accountEntity.setBalance(balance);
        accountDao.updateAccount(accountEntity);

        LOGGER.info("------payin END--------------");
        return transactionDao.getTransactionById(transactionEntity.getTransactionId());
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionTransferDto
     * @throws ApiValidateException
     */
    @Override
    public TransactionTransferDto transfers(String json) throws ApiValidateException {
        LOGGER.info("------transfers START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());
        JSONObject jsonObject = new JSONObject(json);
        Long moneyTransaction = jsonObject.getLong("moneyTransaction");
        Integer toUserId = jsonObject.getInt("toUserId");
        Integer toBankId = jsonObject.getInt("toBankId");
        Integer bankId = jsonObject.getInt("bankId");
        // get account-transfer
        AccountEntity accountTransfer = accountDao.getAccount(userLogin.getUserId(), bankId);
        //get account-reciever
        AccountEntity accountReciever = accountDao.getAccount(toUserId, toBankId);

        TransactionEntity transactionEntity1 = new TransactionEntity();
        TransactionEntity transactionEntity2 = new TransactionEntity();

        FeeEntity feeEntity = feeDao.getFee(accountTransfer.getBankId(), moneyTransaction);
        Long balance = (long) (accountTransfer.getBalance() - moneyTransaction - feeEntity.getMoneyFee() - moneyTransaction * feeEntity.getPercentFee() / 100);

        if (balance >= Constant.BALANCE_MIN) {

            // create account-transfer's transaction
            transactionEntity1.setDateTransaction(new Timestamp(new Date().getTime()));
            transactionEntity1.setMoneyTransaction(accountTransfer.getBalance() - balance);
            transactionEntity1.setToAccountId(accountReciever.getAccountId());
            transactionEntity1.setAccountId(accountTransfer.getAccountId());
            transactionEntity1.setTypeTransaction(2);
            transactionDao.createTransaction(transactionEntity1);

            // create account-reciever's transaction
            transactionEntity2.setDateTransaction(new Timestamp(new Date().getTime()));
            transactionEntity2.setMoneyTransaction(moneyTransaction);
            transactionEntity2.setFromAccountId(accountTransfer.getAccountId());
            transactionEntity2.setAccountId(accountReciever.getAccountId());
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
        transactionTransferDto.setAccountTransactionDto(accountDao.getAccountTransaction(accountReciever.getAccountId()));

        LOGGER.info("------transfers END--------------");
        return transactionTransferDto;
    }

}
