/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dao;

import management.model.FeeEntity;

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
public interface FeeDao {

    /**
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @param moneyTransaction
     * @return Object FeeEntity
     */
    public FeeEntity getFee(Integer bankId, Long moneyTransaction);
}
