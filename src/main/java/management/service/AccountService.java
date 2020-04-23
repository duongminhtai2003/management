/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service;

import management.model.AccountEntity;
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
public interface AccountService {

    /**
     * @author: (VNEXT) TaiDM
     * @param accountEntity
     * @return Object AccountEntity
     * @throws ApiValidateException
     */
    public AccountEntity getAccount(AccountEntity accountEntity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     * @throws ApiValidateException
     */
    public void createAccount(AccountEntity entity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param accountEntity
     * @return Long balance
     * @throws ApiValidateException
     */
    public Long checkBalance(AccountEntity accountEntity) throws ApiValidateException;

}
