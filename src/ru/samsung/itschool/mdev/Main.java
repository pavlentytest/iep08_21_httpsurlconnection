package ru.samsung.itschool.mdev;

import com.google.gson.Gson;
import ru.samsung.itschool.mdev.model.Answer;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // https://aztro.sameerkumar.website/?sign=taurus&day=today

        String sign = "taurus";
        String day = "today";
        String url = "https://aztro.sameerkumar.website/?sign="+sign+"&day="+day;

        HttpsURLConnection connection;
        URL u = new URL(url);
        connection = (HttpsURLConnection) u.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(10000);
        connection.connect();

        // http responce codes

        int code = connection.getResponseCode();
        System.out.println(code);

        ArrayList<String> lines = new ArrayList<>();
        if(code == 200) {
            Scanner scan = new Scanner(connection.getInputStream());
            while(scan.hasNext()) {
                lines.add(scan.nextLine());
            }
        }

        String savepath = "result.txt";
        Path path = Path.of(savepath);
        //Files.createFile(path);
        Files.write(path,lines);

        List<String> readlines = Files.readAllLines(path);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<readlines.size();i++) {
            stringBuilder.append(readlines.get(i));
        }
        Gson gson = new Gson();
        Answer answer = gson.fromJson(stringBuilder.toString(),Answer.class);
        System.out.println(answer.getDescription());



    }
}
