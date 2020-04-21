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
     * @param json
     * @return
     */
    public AccountEntity getAccountById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @throws ApiValidateException 
     */
    public void createAccount(AccountEntity entity) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public Long checkBalance(Integer id);

    

}
