package com.llat.views.menu.items;

import com.llat.controller.Controller;
import com.llat.main.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportLatexItem {
    Controller controller;
    Stage stage;
    MenuItem latexItem;

    private static final String LATEX_FILE_PATH = "src/main/resources/tex_truth_tree_template.tex";
    private String sampleText;

    public ExportLatexItem(Controller controller) {
        this.controller = controller;
        this.stage = controller.getStage();
        this.latexItem = new MenuItem("Latex");
        this.sampleText = getLatexText();
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("logicTree.tex");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));

        //Adding action on the menu item
        latexItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Opening a dialog box
                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    saveTextToFile(sampleText, file);
                }
            }
        });

    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getLatexText() {
        String data = null;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(LATEX_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            data = sb.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            File myObj = new File(LATEX_FILE_PATH);
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                data = myReader.nextLine();
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

        return data;
    }

    public MenuItem getItem() {
        return latexItem;
    }
}
