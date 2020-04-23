/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao;

import java.util.List;

import management.dto.TransactionDto;
import management.model.TransactionEntity;

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
public interface TransactionDao {

    /**
     * @author: (VNEXT) TaiDM
     * @param entity
     */
    public void createTransaction(TransactionEntity entity);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return Object TransactionDto
     */
    public TransactionDto getTransactionById(Integer id);

    /**
     * @author: (VNEXT) TaiDM
     * @param accountId
     * @return List TransactionEntity
     */
    public List<TransactionEntity> getAllTransactionBank(Integer accountId);

    /**
     * @author: (VNEXT) TaiDM
     * @param accountId
     * @return List TransactionEntity
     */
    public List<TransactionEntity> getAllTransactionUser(Integer userId);

}
