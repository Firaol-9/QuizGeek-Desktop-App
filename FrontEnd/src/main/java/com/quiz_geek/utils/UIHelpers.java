package com.quiz_geek.utils;

import javafx.scene.Node;

public class UIHelpers {
    public static void nodeVisibility(Node node, boolean visible){
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
