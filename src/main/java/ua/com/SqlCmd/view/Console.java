package ua.com.SqlCmd.view;

import java.util.Scanner;

/**
 * Created by Тарас on 26.01.2016.
 */
public class Console implements View {
    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
