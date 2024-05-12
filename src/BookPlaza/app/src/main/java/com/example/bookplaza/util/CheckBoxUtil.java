package com.example.bookplaza.util;

import android.widget.CheckBox;

import java.util.List;

/**
 * Utility class for working with checkboxes.
 *
 * @author Xiangji Kong
 */
public class CheckBoxUtil {

    /**
     * Unchecks a list of checkboxes to ensure only one option is chosen
     *
     * @param checkBoxList The list of checkboxes to uncheck.
     */
    public static void unCheck(List<CheckBox> checkBoxList) {
        for (CheckBox chb : checkBoxList) {
            chb.setChecked(false);
        }
    }

    /**
     * Gets the text value of the selected checkbox
     *
     * @param checkBoxList The list of checkboxes to search for a selected checkbox.
     * @return The text value of the selected checkbox, or an empty string if none is selected.
     */
    public static String getOne(List<CheckBox> checkBoxList) {
        String text = "";
        for (CheckBox chb : checkBoxList) {
            if (chb.getText() == null) {
                continue;
            }
            if (chb.isChecked()) {
                text = chb.getText().toString().replace(" ","");
                break;
            }
        }
        return text;
    }

}
