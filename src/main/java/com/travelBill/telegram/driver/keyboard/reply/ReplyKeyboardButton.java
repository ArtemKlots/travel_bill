package com.travelBill.telegram.driver.keyboard.reply;

import com.travelBill.telegram.driver.keyboard.Button;

/* @see https://core.telegram.org/bots/api#keyboardbutton
 * Button under the message textarea
 */
public class ReplyKeyboardButton extends Button {

    public ReplyKeyboardButton setText(String text) {
        this.text = text;
        return this;
    }
}
