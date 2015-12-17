/*
 *
 * Copyright 2015 magiclen.org
 * 
 * Licensed under the Apache License, 版本 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magiclen.chinesecalendarconverter;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.magiclen.chinesecalendarconverter.pages.Main;

/**
 * <p>
 * 西曆與農曆的轉換程式，提供以下幾點功能：
 * </p>
 * <ul>
 * <li>
 * 1. 西曆轉農曆
 * </li>
 * <li>
 * 2. 農曆轉西曆
 * </li>
 * </ul>
 *
 * @author Magic Len
 */
public class ChineseCalendarConverter extends Application {

    // -----類別常數-----
    private final static int WIDTH = 500;
    private final static int HEIGHT = 175;

    // -----類別變數-----
    private static Stage primaryStage;
    private static Scene primaryScene;
    private static Main main;

    // -----物件方法-----
    /**
     * 當應用程式執行的時候。
     *
     * @param primaryStage 傳入主要的舞台
     */
    @Override
    public void start(final Stage primaryStage) {
	main = new Main();
	ChineseCalendarConverter.primaryScene = new Scene(main, WIDTH, HEIGHT, Color.WHITESMOKE);
	ChineseCalendarConverter.primaryStage = primaryStage;

	primaryStage.setResizable(true);
	primaryStage.setTitle("陰陽曆轉換程式 - JavaFX版");
	primaryStage.setScene(ChineseCalendarConverter.primaryScene);

	//螢幕置中
	final Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	primaryStage.setX((screen.getWidth() - WIDTH) / 2);
	primaryStage.setY((screen.getHeight() - HEIGHT) / 2);

	primaryStage.show();
    }

    /**
     * 程式進入點。
     *
     * @param args 傳入參數(用不到)
     */
    public static void main(final String[] args) {
	launch(args);
    }

}
