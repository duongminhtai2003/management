/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import management.dao.AccountDao;
import management.dao.BankDao;
import management.dao.UserDao;
import management.model.AccountEntity;
import management.model.UserEntity;
import management.service.AccountService;
import management.utils.ApiValidateException;
import management.utils.Constant;
import management.utils.DataUtils;
import management.utils.MessageUtils;

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
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BankDao bankDao;

    @Autowired
    private UserDao userDao;

    /**
     * @author: (VNEXT) TaiDM
     * @param accountEntity
     * @return Object AccountEntity
     * @throws ApiValidateException
     */
    @Override
    public AccountEntity getAccount(AccountEntity accountEntity) throws ApiValidateException {
        LOGGER.info("------getAccount START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());

        // check bankId đã đăng ký cho userId chưa
        if (accountDao.getAccount(userLogin.getUserId(), accountEntity.getBankId()) == null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR2", new Object[] { "Ngân hàng2" }));
        }

        AccountEntity entity = accountDao.getAccount(userLogin.getUserId(), accountEntity.getBankId());

        LOGGER.info("------getAccount END--------------");
        return entity;
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     * @throws ApiValidateException
     */
    @Override
    public void createAccount(AccountEntity accountEntity) throws ApiValidateException {
        LOGGER.info("------createAccount START--------------");

        UserEntity userLogin = userDao.getUserByPhone(DataUtils.getPhoneByToken());

        // check id bank có tồn tại hay không
        if (bankDao.getBankById(accountEntity.getBankId()) == null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR2", new Object[] { "Ngân hàng" }));
        }

        // check bankId đã đăng ký cho userId chưa
        if (accountDao.getAccount(userLogin.getUserId(), accountEntity.getBankId()) != null) {
            throw new ApiValidateException(Constant.NOT_IMPLEMENTED, MessageUtils.getMessage("ERROR1", new Object[] { "Ngân hàng" }));
        }

        accountEntity.setUserId(userLogin.getUserId());
        accountEntity.setBankId(accountEntity.getBankId());
        accountEntity.setBalance(0L);
        accountDao.createAccount(accountEntity);

        LOGGER.info("------createAccount END--------------");
    }

    /**
     * @author: (VNEXT) TaiDM
     * @param accountEntity
     * @return Long balance
     * @throws ApiValidateException
     */
    @Override
    public Long checkBalance(AccountEntity accountEntity) throws ApiValidateException {
        LOGGER.info("------checkBalance START--------------");

        Long balance = getAccount(accountEntity).getBalance();

        LOGGER.info("------checkBalance END--------------");
        return balance;
    }

}
