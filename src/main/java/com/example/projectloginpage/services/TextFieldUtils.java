package com.example.projectloginpage.services;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TextFieldUtils {

    public static void makeNumeric(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }
}

