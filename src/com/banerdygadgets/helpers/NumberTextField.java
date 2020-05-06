package com.banerdygadgets.helpers;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {
    public NumberTextField() {
        this.setPromptText("Alleen nummers invoeren");
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        if(s.matches("[0-9]") || s.isEmpty()) {
            super.replaceText(i, i1, s);
        }
    }
}
