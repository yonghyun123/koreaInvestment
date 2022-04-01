package koreainvest.utils;

import java.util.Objects;
import java.util.Scanner;

public class Console {

    /**
     * 자바 표준 입력 api
     */

    private static Scanner scanner = getScanner();

    //생성 불가
    private Console(){}

    private static Scanner getScanner(){
        return new Scanner(System.in);
    }

    public static String readLine(){
        newScannerIfAbsent();
        return scanner.nextLine();
    }

    private static void newScannerIfAbsent(){
        if(Objects.isNull(scanner)){
            scanner = getScanner();
        }
    }
}