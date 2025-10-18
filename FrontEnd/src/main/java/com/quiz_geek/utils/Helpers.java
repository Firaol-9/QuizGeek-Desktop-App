package com.quiz_geek.utils;

import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Helpers {
    public static void nodeVisibility(Node node, boolean visible){
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
