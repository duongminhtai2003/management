/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import management.dao.AccountDao;
import management.model.AccountEntity;
import management.service.AccountService;
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
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao dao;

    @Override
    public AccountEntity getAccountById(Integer id) {
        AccountEntity entity = dao.getAccountById(id);
        return entity;
    }

    @Override
    public void createAccount(AccountEntity entity) throws ApiValidateException {
        if (entity == null) {
            throw new ApiValidateException("404", "Please enter all field");
        }
        entity.setBalance(0L);
        dao.createAccount(entity);
    }

    @Override
    public Long checkBalance(Integer id) {
        AccountEntity entity = dao.getAccountById(id);
        Long balance = entity.getBalance();
        return balance;
    }

}
