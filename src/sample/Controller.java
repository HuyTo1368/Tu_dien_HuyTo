package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller extends DictionaryManagement{
    ArrayList<Word> tudien;
    {
        try {
            tudien = super.insertFromCommandline();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField tu;
    @FXML
    private TextArea tu1;


    @FXML
    public void Submit (ActionEvent event) {
        String wordTarget = tu.getText().toLowerCase().replaceAll("[\\t\\n\\s]", "");
        String wordEplain = null;


        for (int i = 0; i<tudien.size(); i++) {
            if (wordTarget.equals(tudien.get(i).getWord_target())) {
                wordEplain = wordTarget + "\n"
                        + tudien.get(i).getWord_type() + "\n"
                        + tudien.get(i).getWord_pronounce() +"\n"
                        + tudien.get(i).getWord_explain();
            }
        }
        tu1.setText(wordEplain);
    } // Ham tra tu

    public void EditWord(ActionEvent event) {
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setHeaderText("Sửa từ");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        TextField wordaddTarget = new TextField();
        wordaddTarget.setPromptText("Nhập từ ");
        TextField wordaddType = new TextField();
        wordaddType.setPromptText("Nhập loai từ");
        TextField wordaddPronounce = new TextField();
        wordaddPronounce.setPromptText("Nhập phát âm");
        TextField wordaddExplain = new TextField();
        wordaddExplain.setPromptText("Nhập nghĩa");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Từ tiếng anh cần sửa: "), 0, 0);
        grid.add(wordaddTarget, 1, 0);
        grid.add(new Label("Loại của từ cần sửa: "), 0, 1);
        grid.add(wordaddType, 1, 1);
        grid.add(new Label("Phát âm của từ cần sửa: "), 0, 2);
        grid.add(wordaddPronounce, 1, 2);
        grid.add(new Label("Nghĩa của từ cần sửa: "), 0, 3);
        grid.add(wordaddExplain, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.close();
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(response -> {
            if (response == ButtonType.OK) {
                int check = 0;
                String wordTarget = wordaddTarget.getText();
                String wordType = wordaddType.getText();
                String wordPronounce = wordaddPronounce.getText();
                String wordEplain = wordaddExplain.getText();
                for (int i = 0; i<tudien.size(); i++) {
                    if (wordTarget.equals(tudien.get(i).getWord_target())) {
                        check = 1;
                        tudien.get(i).setWord_type(wordType);
                        tudien.get(i).setWord_pronounce(wordPronounce);
                        tudien.get(i).setWord_explain(wordEplain);

                        try {
                            FileWriter fileWriter = new FileWriter("D:\\Tu_dien_HuyTo\\tudien.txt");
                            for (int j = 0; j<tudien.size(); j++) {
                                String word = tudien.get(j).getWord_target() + "\t"
                                        + tudien.get(j).getWord_type() + "\t"
                                        + tudien.get(j).getWord_pronounce() + "\t"
                                        + tudien.get(j).getWord_explain() + "\n";
                                fileWriter.write(word);
                            }
                            fileWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

                if (check == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Sua tu");
                    alert.setContentText("Sua tu thanh cong !!!");

                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Sua");
                    alert.setContentText("Khong tim thay tu can sua !!!");

                    alert.showAndWait();
                }
            }
        });
    } // Ham sua tu

    public void DeleteWord(ActionEvent event) {
        int check = 0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Xóa Từ");
        dialog.setContentText("Nhập Từ muốn xóa: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            for (int i = 0; i<tudien.size(); i++) {
                if ((result.get()).equals(tudien.get(i).getWord_target())) {
                    tudien.remove(i);
                    check = 1;

                    try {
                        FileWriter fileWriter = new FileWriter("D:\\Tu_dien_HuyTo\\tudien.txt");
                        for (int j = 0; j<tudien.size(); j++) {
                            String word = tudien.get(j).getWord_target() + "\t"
                                    + tudien.get(j).getWord_type() + "\t"
                                    + tudien.get(j).getWord_pronounce() + "\t"
                                    + tudien.get(j).getWord_explain() + "\n";
                            fileWriter.write(word);
                        }
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            if (check == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Results:");
                alert.setContentText("Xoa tu thanh cong !!!");

                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Results:");
                alert.setContentText("Tu can xoa khong ton tai !!!");

                alert.showAndWait();
            }
        }
    } // Ham xoa tu

    public void addWord(ActionEvent event) {
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setHeaderText("Thêm từ");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        TextField wordaddTarget = new TextField();
        wordaddTarget.setPromptText("Nhập từ ");
        TextField wordaddType = new TextField();
        wordaddType.setPromptText("Nhập loai từ");
        TextField wordaddPronounce = new TextField();
        wordaddPronounce.setPromptText("Nhập phát âm");
        TextField wordaddExplain = new TextField();
        wordaddExplain.setPromptText("Nhập nghĩa");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Từ tiếng anh cần thêm: "), 0, 0);
        grid.add(wordaddTarget, 1, 0);
        grid.add(new Label("Loại của từ cần thêm: "), 0, 1);
        grid.add(wordaddType, 1, 1);
        grid.add(new Label("Phát âm của từ cần thêm: "), 0, 2);
        grid.add(wordaddPronounce, 1, 2);
        grid.add(new Label("Nghĩa của từ cần thêm: "), 0, 3);
        grid.add(wordaddExplain, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.close();
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(response -> {
            if (response == ButtonType.OK) {
                String wordTarget = wordaddTarget.getText();
                String wordType = wordaddType.getText();
                String wordPronounce = wordaddPronounce.getText();
                String wordEplain = wordaddExplain.getText();
                Word word = new Word(wordTarget, wordType, wordPronounce , wordEplain);
                tudien.add(word);

                BufferedWriter bw = null;
                FileWriter fw = null;
                try {
                    String string = "\n" + wordTarget + "\t" + wordType + "\t" + wordPronounce + "\t" + wordEplain;
                    File file = new File("D:\\Tu_dien_HuyTo\\tudien.txt");

                    fw = new FileWriter(file.getAbsoluteFile(), true);
                    bw = new BufferedWriter(fw);
                    
                    bw.write(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Thêm từ");
                alert.setContentText("Thêm tu thanh cong !!!");

                alert.showAndWait();
            }
        });
    } // Ham them tu

    public void suggestWord() {
        ArrayList<String> words = new ArrayList<String>();
        String charSuggest = tu.getText();
        for (int i = 0; i < tudien.size(); i++) {
            if(tudien.get(i).getWord_target().indexOf(charSuggest) >= 0) {
                words.add(tudien.get(i).getWord_target());
            }
        }
        TextFields.bindAutoCompletion(tu, words);
    } // Ham tu goi y.

    public void audio(ActionEvent event) {
        Audio.speech(tu.getText());
    } // Ham phat am.
}
