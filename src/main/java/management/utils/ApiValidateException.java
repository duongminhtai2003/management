/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.utils;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) TaiDM
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/21      (VNEXT) TaiDM       Create new
*/

public class ApiValidateException extends Exception {
    private static final long serialVersionUID = 1L;
    private String code;
    private String field;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiValidateException(String code, String field, String message) {
        super();
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public ApiValidateException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

}
