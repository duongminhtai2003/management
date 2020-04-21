/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service;

import java.util.List;

import management.dto.TransactionDto;
import management.dto.TransactionTransferDto;
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
public interface TransactionService {

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return
     */
    public List<TransactionTransferDto> getTransactionByDate(String json);

    /**
     * get account's list-transaction
     * @author: (VNEXT) TaiDM
     * @param accountId
     * @return
     */
    public List<TransactionTransferDto> getListTransactionByAccountId(Integer accountId);

    /**
     * @author: (VNEXT) TaiDM
     * @param id
     * @return
     */
    public String outputTransactionToCSV(Integer accountId);

    /**
     * @author: (VNEXT) TaiDM
     * @param Id
     * @param json
     * @return
     * @throws ApiValidateException 
     */
    public TransactionDto withdrawal(Integer accountId, String json) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param Id
     * @param json
     * @return
     */
    public TransactionDto payin(Integer accountId, String json);

    /**
     * @author: (VNEXT) TaiDM
     * @param Id
     * @param json
     * @return
     * @throws ApiValidateException 
     */
    public TransactionTransferDto transfers(Integer accountId, String json) throws ApiValidateException;

}
