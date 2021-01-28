package utils;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaneResizeHandler {
    public static class WidthHandler implements ChangeListener<Number> {
        private Pane pane;
        private Label lblMain;
        private Button btnSpin;
        private Button btnExit;
        private Stage window;
        public WidthHandler(Pane pane, Stage window) {
            this.pane = pane;
            this.lblMain = (Label) pane.lookup("#lblMain");
            this.btnSpin = (Button) pane.lookup("#btnSpin");
            this.btnExit = (Button) pane.lookup("#btnExit");
            this.window = window;
        }

		@Override
        public void changed(ObservableValue<? extends Number> v, Number oldSize, Number newSize) {
            double delta = newSize.doubleValue() - oldSize.doubleValue();
            pane.setPrefWidth(pane.getPrefWidth() + delta);
            double width = window.getWidth();
            lblMain.setLayoutX(width / 4);
            btnSpin.setLayoutX(width / 2.4);
            btnExit.setLayoutX(width / 2.4);
        }

    }

    public static class HeightHandler implements ChangeListener<Number> {
        private Pane pane;
        private Label lblMain;
        private Button btnSpin;
        private Stage window;
        
        public HeightHandler(Pane pane, Stage window) {
            this.pane = pane;
            this.lblMain = (Label) pane.lookup("#lblMain");
            this.btnSpin = (Button) pane.lookup("#btnSpin");
            this.window = window;
        }

		@Override
        public void changed(ObservableValue<? extends Number> v, Number oldSize, Number newSize) {
            double delta = newSize.doubleValue() - oldSize.doubleValue();
            pane.setPrefHeight(pane.getPrefHeight() + delta);
            double height = window.getHeight();
            lblMain.setLayoutY(height / 4);
            btnSpin.setLayoutY(height / 2);
        }
    }
}
