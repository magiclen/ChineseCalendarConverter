/*
 *
 * Copyright 2015-2018 magiclen.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.magiclen.chinesecalendarconverter.pages;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.magiclen.農曆.農曆;

/**
 * 主頁面。
 *
 * @author Magic Len
 */
public class Main extends FlowPane {

    // -----類別常數-----
    private static final int GAP = 5;
    private static final String[] YEARS, MONTHS, DAYS;
    private static final int MIN_YEAR = 1901, MAX_YEAR = 2100;

    // -----類別初始-----
    static {
        // 初始化年、月、日字串
        YEARS = new String[MAX_YEAR - MIN_YEAR + 1];
        for (int i = MIN_YEAR; i <= MAX_YEAR; ++i) {
            YEARS[i - MIN_YEAR] = String.valueOf(i);
        }
        MONTHS = new String[12];
        for (int i = 1; i <= 12; ++i) {
            MONTHS[i - 1] = String.valueOf(i);
        }
        DAYS = new String[31];
        for (int i = 1; i <= 31; ++i) {
            DAYS[i - 1] = String.valueOf(i);
        }
    }

    // -----類別變數-----
    // -----物件變數-----
    private final Insets insets;
    private final Label lYear, lYears, lMonths, lDays, lResult, lChineseYear, lChineseYears, lChineseMonths, lChineseDays, lChineseResult;
    private final ComboBox<String> cbYears, cbMonths, cbDays, cbChineseYears, cbChineseMonths, cbChineseDays;
    private final CheckBox cbChineseLeapMonth;
    private final HBox hbDate, hbChineseDate;
    private final Label lAuthor;

    // -----建構子-----
    /**
     * 建構子，初始化容器。
     */
    public Main() {
        insets = new Insets(GAP, GAP, GAP, GAP);

        // GUI元件
        lYear = new Label("西元");
        lYears = new Label("年");
        lMonths = new Label("月");
        lDays = new Label("日");
        cbYears = new ComboBox(FXCollections.observableArrayList(YEARS));
        cbMonths = new ComboBox(FXCollections.observableArrayList(MONTHS));
        cbDays = new ComboBox(FXCollections.observableArrayList(DAYS));

        final LocalDate currentPoint = LocalDate.now();
        final int year = currentPoint.getYear();
        if (year >= MIN_YEAR && year <= MAX_YEAR) {
            cbYears.getSelectionModel().select(String.valueOf(year));
            cbMonths.getSelectionModel().select(String.valueOf(currentPoint.getMonthValue()));
            cbDays.getSelectionModel().select(String.valueOf(currentPoint.getDayOfMonth()));
        } else {
            cbYears.getSelectionModel().select("1901");
            cbMonths.getSelectionModel().select("2");
            cbDays.getSelectionModel().select("19");
        }

        final EventHandler<ActionEvent> cbEvent = (e) -> {
            convertToChineseCalendar();
        };
        cbYears.setOnAction(cbEvent);
        cbMonths.setOnAction(cbEvent);
        cbDays.setOnAction(cbEvent);

        cbYears.setMaxWidth(Integer.MAX_VALUE);
        cbMonths.setMaxWidth(Integer.MAX_VALUE);
        cbDays.setMaxWidth(Integer.MAX_VALUE);

        hbDate = new HBox();
        hbDate.setMaxWidth(Integer.MAX_VALUE);
        HBox.setHgrow(lYear, Priority.NEVER);
        HBox.setHgrow(cbYears, Priority.ALWAYS);
        HBox.setHgrow(lYears, Priority.NEVER);
        HBox.setHgrow(cbMonths, Priority.ALWAYS);
        HBox.setHgrow(lMonths, Priority.NEVER);
        HBox.setHgrow(cbDays, Priority.ALWAYS);
        HBox.setHgrow(lDays, Priority.NEVER);
        HBox.setMargin(lYear, insets);
        HBox.setMargin(cbYears, insets);
        HBox.setMargin(lYears, insets);
        HBox.setMargin(cbMonths, insets);
        HBox.setMargin(lMonths, insets);
        HBox.setMargin(cbDays, insets);
        HBox.setMargin(lDays, insets);
        hbDate.setAlignment(Pos.BASELINE_CENTER);
        hbDate.getChildren().addAll(lYear, cbYears, lYears, cbMonths, lMonths, cbDays, lDays);

        lResult = new Label();
        lResult.setAlignment(Pos.BASELINE_CENTER);
        lResult.setMaxWidth(Integer.MAX_VALUE);

        lChineseYear = new Label("農曆");
        lChineseYears = new Label("年");
        lChineseMonths = new Label("月");
        lChineseDays = new Label("日");
        cbChineseLeapMonth = new CheckBox("閏月");
        cbChineseYears = new ComboBox(FXCollections.observableArrayList(YEARS));
        cbChineseMonths = new ComboBox(FXCollections.observableArrayList(MONTHS));
        cbChineseDays = new ComboBox(FXCollections.observableArrayList(DAYS));

        cbChineseYears.getSelectionModel().selectFirst();
        cbChineseMonths.getSelectionModel().selectFirst();
        cbChineseDays.getSelectionModel().selectFirst();

        final EventHandler<ActionEvent> cbChineseEvent = (e) -> {
            convertToCalendar();
        };
        cbChineseYears.setOnAction(cbChineseEvent);
        cbChineseMonths.setOnAction(cbChineseEvent);
        cbChineseDays.setOnAction(cbChineseEvent);
        cbChineseLeapMonth.setOnAction(cbChineseEvent);

        cbChineseYears.setMaxWidth(Integer.MAX_VALUE);
        cbChineseMonths.setMaxWidth(Integer.MAX_VALUE);
        cbChineseDays.setMaxWidth(Integer.MAX_VALUE);

        hbChineseDate = new HBox();
        hbChineseDate.setMaxWidth(Integer.MAX_VALUE);
        HBox.setHgrow(lChineseYear, Priority.NEVER);
        HBox.setHgrow(cbChineseYears, Priority.ALWAYS);
        HBox.setHgrow(lChineseYears, Priority.NEVER);
        HBox.setHgrow(cbChineseMonths, Priority.ALWAYS);
        HBox.setHgrow(lChineseMonths, Priority.NEVER);
        HBox.setHgrow(cbChineseLeapMonth, Priority.NEVER);
        HBox.setHgrow(cbChineseDays, Priority.ALWAYS);
        HBox.setHgrow(lChineseDays, Priority.NEVER);
        HBox.setMargin(lChineseYear, insets);
        HBox.setMargin(cbChineseYears, insets);
        HBox.setMargin(lChineseYears, insets);
        HBox.setMargin(cbChineseMonths, insets);
        HBox.setMargin(lChineseMonths, insets);
        HBox.setMargin(cbChineseLeapMonth, insets);
        HBox.setMargin(cbChineseDays, insets);
        HBox.setMargin(lChineseDays, insets);
        hbChineseDate.setAlignment(Pos.BASELINE_CENTER);
        hbChineseDate.getChildren().addAll(lChineseYear, cbChineseYears, lChineseYears, cbChineseMonths, lChineseMonths, cbChineseLeapMonth, cbChineseDays, lChineseDays);

        lChineseResult = new Label();
        lChineseResult.setAlignment(Pos.BASELINE_CENTER);
        lChineseResult.setMaxWidth(Integer.MAX_VALUE);

        lAuthor = new Label("Powered by magiclen.org");
        lAuthor.setAlignment(Pos.BASELINE_RIGHT);
        lAuthor.setMaxWidth(Integer.MAX_VALUE);
        lAuthor.setOnMouseClicked((e) -> {
            final URI uri = URI.create("https://magiclen.org/");
            new Thread(() -> {
                try {
                    Desktop.getDesktop().browse(uri);
                } catch (final IOException ex) {
                    //應該不會有例外才對
                }
            }).start();
        });

        final Tooltip tipAuthor = new Tooltip("Magic Len");
        Tooltip.install(lAuthor, tipAuthor);

        FlowPane.setMargin(hbDate, insets);
        FlowPane.setMargin(lResult, insets);
        FlowPane.setMargin(hbChineseDate, insets);
        FlowPane.setMargin(lChineseResult, insets);
        FlowPane.setMargin(lAuthor, insets);
        this.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.setOrientation(Orientation.VERTICAL);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(hbDate, lResult, hbChineseDate, lChineseResult, lAuthor);

        convertToChineseCalendar();
        convertToCalendar();
    }

    /**
     * 農曆轉西曆。
     */
    private void convertToCalendar() {
        final int year = Integer.parseInt(cbChineseYears.getSelectionModel().getSelectedItem());
        final int month = Integer.parseInt(cbChineseMonths.getSelectionModel().getSelectedItem());
        final int day = Integer.parseInt(cbChineseDays.getSelectionModel().getSelectedItem());
        if (day == 31) {
            lChineseResult.setText("農曆日期最大只有30日！");
            return;
        }
        final 農曆.月 chineseMonth = 農曆.月.values()[month - 1];
        final 農曆.日 chineseDay = 農曆.日.values()[day - 1];
        boolean leap = cbChineseLeapMonth.isSelected();
        final 農曆 chineseCalendar = 農曆.建立(year, chineseMonth, leap, chineseDay);
        if (chineseCalendar != null) {
            lChineseResult.setText("西曆：" + chineseCalendar.取得西曆());
        } else {
            lChineseResult.setText("錯誤或不支援的農曆日期，無法轉換成西曆");
        }

    }

    /**
     * 西曆轉農曆。
     */
    private void convertToChineseCalendar() {
        final int year = Integer.parseInt(cbYears.getSelectionModel().getSelectedItem());
        final int month = Integer.parseInt(cbMonths.getSelectionModel().getSelectedItem());
        final int day = Integer.parseInt(cbDays.getSelectionModel().getSelectedItem());
        final 農曆 chineseCalendar = 農曆.建立(year, month, day);
        if (chineseCalendar != null) {
            lResult.setText("農曆：" + chineseCalendar.取得農曆());
        } else {
            lResult.setText("錯誤或不支援的西曆日期，無法轉換成農曆");
        }
    }
}
