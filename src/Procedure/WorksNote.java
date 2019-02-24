package Procedure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class WorksNote {
    private String fileNameSimple  = "";
    private String fileNameCoded  = "";
    public String title = "";

    public WorksNote() { }

    public void Preprocess(List<String> message, int id) {
        try {
            fileNameSimple = "D:\\myprojects\\Encrypto\\Verification\\Notes\\Simple\\" + id + "_" + title + ".txt";
            PrintWriter simple = new PrintWriter(new BufferedWriter(new FileWriter(fileNameSimple)));
            for (String x : message)
                simple.print(x + " ");
            simple.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Postprocess(List<String> code, int id) {
        try {
            fileNameCoded = "D:\\myprojects\\Encrypto\\Verification\\Notes\\Coded\\" + id + "_" + title + ".txt";
            PrintWriter simple = new PrintWriter(new BufferedWriter(new FileWriter(fileNameCoded)));
            for (String y : code)
                simple.print(y + " ");
            simple.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddData(id);
    }

    private void AddData(int id) {
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");
            PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO BKDOOR(USERID, TITLE, SIMPLE, CODED) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, fileNameSimple);
            preparedStatement.setString(4, fileNameCoded);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
