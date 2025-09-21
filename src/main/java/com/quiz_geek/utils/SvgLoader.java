package com.quiz_geek.utils;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.animation.AnimationElement;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;

public class SvgLoader {

    public static ImageView loadSvg(String resourcePath, double width, double height, Color fxFillColor) throws Exception {
        // Load the SVG
        SVGUniverse universe = new SVGUniverse();
        InputStream stream = SvgLoader.class.getResourceAsStream(resourcePath);

        if (stream == null) {
            throw new IllegalArgumentException("SVG not found: " + resourcePath);
        }

        URI uri = universe.loadSVG(stream, resourcePath);
        SVGDiagram diagram = universe.getDiagram(uri);

        // Convert JavaFX Color → CSS hex (#RRGGBB)
        String fillHex = toHex(fxFillColor);

        // Apply new fill color to every element
        recolorElements(diagram.getRoot(), fillHex);

        // Update diagram so new attributes are applied
        diagram.updateTime(0f);

        // Render into a BufferedImage
        BufferedImage bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        diagram.render(bufferedImage.createGraphics());

        // Convert BufferedImage → JavaFX Image
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

        // Wrap in ImageView for JavaFX
        ImageView imageView = new ImageView(fxImage);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }

    // Recursive recoloring
    private static void recolorElements(SVGElement element, String fillHex) {
        try {
            element.addAttribute("fill", AnimationElement.AT_XML, fillHex);
        } catch (SVGException ignored) {}

        var children = element.getChildren(null);
        if (children != null) {
            for (Object child : children) {
                if (child instanceof SVGElement svgChild) {
                    recolorElements(svgChild, fillHex);
                }
            }
        }
    }

    // Helper: JavaFX Color → #RRGGBB
    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
