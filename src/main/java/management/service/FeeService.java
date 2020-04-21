/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.service;

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
public interface FeeService {

    /**
     * get bank's fee
     * @author: (VNEXT) TaiDM
     * @param bankId
     * @param level
     * @return
     */
    public FeeEntity getFee(Integer bankId, Long level);
}
