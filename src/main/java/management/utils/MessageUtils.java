/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package management.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * [OVERVIEW] Get messages from file properties.
 *
 * @author: (VNEXT)LinhDT
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/15      (VNEXT)LinhDT      Create new
*/
public class MessageUtils {

    /**
     * @author (VNEXT)DuyHV
     * @param key
     * @param locate
     * @param param
     * @return
     */
    public static String getMessage(String key, Object... param) {
        ResourceBundle rsMessages;
        // Load all message from message.properties
        rsMessages = ResourceBundle.getBundle("message");

        String message;
        try {
            // Get message from rsMessages
            message = rsMessages.getString(key);
            if (message.length() == 0) {
                return key;
            }
            // Replace param
            if (param != null && param.length > 0) {
                message = MessageFormat.format(message, param);
            }
        } catch (MissingResourceException e) {
            message = key;
        }
        // return content
        return message;
    }

}
