/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {
    private String bankName;
    private Long balance;
}
