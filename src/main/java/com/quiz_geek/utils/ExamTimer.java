package com.quiz_geek.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ExamTimer {

    private int timeSeconds;
    private Timeline timeline;
    private Label timerLabel;
    private Runnable onTimeUp;

    public ExamTimer() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setTime(int minutes) {
        this.timeSeconds = minutes * 60;
        updateLabel();
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
        updateLabel();
    }

    public void setOnTimeUp(Runnable onTimeUp) {
        this.onTimeUp = onTimeUp;
    }

    public void start() {
        if (timeline != null) {
            timeline.stop(); // reset any previous countdown
        }

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    updateLabel();

                    if (timeSeconds <= 0) {
                        timeline.stop();
                        timeUp();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void updateLabel() {
        if (timerLabel != null) {
            int hours = timeSeconds / 3600;
            int minutes = timeSeconds / 60;
            int seconds = timeSeconds % 60;
            timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
    }

    private void timeUp() {if (onTimeUp != null) Platform.runLater(onTimeUp);}
}
