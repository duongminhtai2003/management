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
     * @return List TransactionTransferDto
     */
    public List<TransactionTransferDto> getTransactionUser();

    /**
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @return List TransactionTransferDto
     */
    public List<TransactionTransferDto> getListTransactionBank(Integer bankId);

    /**
     * @author: (VNEXT) TaiDM
     * @return String file
     */
    public String outputTransactionToCSV();

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionDto
     * @throws ApiValidateException
     */
    public TransactionDto withdrawal(String json) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionDto
     * @throws ApiValidateException
     */
    public TransactionDto payin(String json) throws ApiValidateException;

    /**
     * @author: (VNEXT) TaiDM
     * @param json
     * @return Object TransactionTransferDto
     * @throws ApiValidateException
     */
    public TransactionTransferDto transfers(String json) throws ApiValidateException;

}
