package utils;

import model.BigNumber;
import model.Pair;
import model.Sign;
import service.BigNumberService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileIO {

    public static Pair<BigNumber, BigNumber> readBigNumbersFromFile(String fileName) {
        try {
            ClassLoader classLoader = FileIO.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            Scanner sc = new Scanner(file);
            String bigNumber1String = sc.next();
            String bigNumber2String = sc.next();
            return new Pair<BigNumber, BigNumber>(BigNumberService.transformStringToBigNumber(bigNumber1String),
                    BigNumberService.transformStringToBigNumber(bigNumber2String));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeBigNumberToFile(String fileName, BigNumber result) {
        try {
            File outputFile = new File(fileName);
            outputFile.createNewFile();
            PrintWriter writer = new PrintWriter(outputFile);
            if(result.getBigNumberSign() == Sign.NEGATIVE){
                writer.print("-");
            }
            writer.println(result.getActualNumber());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
