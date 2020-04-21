/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao;

import java.util.List;

import management.dto.AccountDto;
import management.dto.AccountTransactionDto;
import management.model.AccountEntity;

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
public interface AccountDao {

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    public void createAccount(AccountEntity entity);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public AccountEntity getAccountById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    public void updateAccount(AccountEntity entity);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public List<AccountDto> getListAccountByUserId(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public AccountTransactionDto getAccountTransaction(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param userId
     * @param bankId
     * @return
     */
    AccountEntity checkAccount(Integer userId, Integer bankId);

}
