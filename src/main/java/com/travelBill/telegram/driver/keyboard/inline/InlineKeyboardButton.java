package com.travelBill.telegram.driver.keyboard.inline;

import com.travelBill.telegram.driver.keyboard.Button;

/* @link https://core.telegram.org/bots/api#inlinekeyboardbutton
 * Button in the messages area
 */
public class InlineKeyboardButton extends Button {
    public String url;
    public String callbackData;
    public LoginUrl loginUrl;

    public InlineKeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public InlineKeyboardButton setUrl(String url) {
        this.url = url;
        return this;
    }

    public InlineKeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }
}
