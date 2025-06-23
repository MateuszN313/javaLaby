package org.example.kolokwium2_2022.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class Controller {
    @FXML
    private ListView<String> wordList;
    @FXML
    private TextField filterField;
    @FXML
    private Label wordCountLabel;

    private ObservableList<String> items;
    private ObservableList<String> filtered;
    private int wordCounter;

    @FXML
    public void initialize() {
        this.items = FXCollections.observableArrayList();
        this.filtered = FXCollections.observableArrayList();
        this.wordList.setItems(this.items);
        this.wordCounter = 0;

        this.filterField.textProperty().addListener(
                (obs, oldText, newText) -> filterListener(newText)
        );
    }

    public void addWord(String word) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String item = LocalDateTime.now().format(formatter) + ' ' + word;

        this.items.add(item);
        sortWordList(items);
        addWordCount();
    }
    public void sortWordList(List<String> list) {
        list.sort(Comparator.comparing((line) -> line.substring(line.indexOf(' ') + 1)));
    }
    public void addWordCount() {
        this.wordCounter++;
        this.wordCountLabel.setText(Integer.toString(wordCounter));
    }

    public void filterListener(String Text) {
        this.filtered.clear();

        if(Text.isEmpty()) {
            this.wordList.setItems(this.items);
        }else {
            for(String item : this.items) {
                String word = item.substring(item.indexOf(' ') + 1);

                if(word.startsWith(Text) && !this.filtered.contains(item)){
                    this.filtered.add(item);
                }
            }
            //sortWordList(this.filtered);
            this.wordList.setItems(this.filtered);
        }
    }
}
