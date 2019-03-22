package Procedure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

@SuppressWarnings("Duplicates")
public class WorksMessage {
    public String title;

    private String fileNameSimple  = "";
    private String fileNameCoded  = "";

    public void Preprocess(List<String> message, int send, int rec) {
        try {
            fileNameSimple = "D:\\myprojects\\Encrypto\\Verification\\Messages\\Simple\\" + send + "_" + rec + "_" + title + ".txt";
            PrintWriter simple = new PrintWriter(new BufferedWriter(new FileWriter(fileNameSimple)));
            for (String x : message) {
                simple.print(x + " ");
                simple.println();
            }
            simple.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Postprocess(List<String> code, int send, int rec) {
        try {
            fileNameCoded = "D:\\myprojects\\Encrypto\\Verification\\Messages\\Coded\\" + send + "_" + rec + "_" + title + ".txt";
            PrintWriter simple = new PrintWriter(new BufferedWriter(new FileWriter(fileNameCoded)));
            for (String y : code)
                simple.print(y + " ");
            simple.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddData(rec);
    }

    private void AddData(int rec) {
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");
            PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO BKDOORM(USERID, TITLE, SIMPLE, CODED) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, Integer.toString(rec));
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
