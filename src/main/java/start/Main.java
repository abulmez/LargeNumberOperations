package start;

import model.BigNumber;
import model.Pair;
import model.Sign;
import utils.FileIO;

import java.util.Scanner;

public class Main {

    private static void showResult(BigNumber result){
        if(result.getBigNumberSign() == Sign.NEGATIVE){
            System.out.print("-");
        }
        System.out.println(result.getActualNumber());
        System.out.println();
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
//        String bigNumber1String;
//        String bigNumber2String;
//        System.out.println("Dati primul numar:");
//        bigNumber1String = sc.next();
//        System.out.println("Dati al doilea numar:");
//        bigNumber2String = sc.next();
//        BigNumber bigNumber1 = BigNumberService.transformStringToBigNumber(bigNumber1String);
//        BigNumber bigNumber2 = BigNumberService.transformStringToBigNumber(bigNumber2String);

        boolean working = true;
        Pair<BigNumber,BigNumber> terms = FileIO.readBigNumbersFromFile("numbers.in");
        BigNumber bigNumber1 = terms.getFirst();
        BigNumber bigNumber2 = terms.getSecond();
        while(working){
            System.out.println("1.Adunarea numerelor");
            System.out.println("2.Scaderea numerelor");
            System.out.println("3.Inmutirea numerelor");
            System.out.println("4.Iesire");
            int x = sc.nextInt();
            switch(x){
                case 1: {
                    BigNumber result = BigNumber.add(bigNumber1, bigNumber2);
                    showResult(result);
                    FileIO.writeBigNumberToFile("result.out",result);
                    break;
                }
                case 2: {
                    BigNumber result = BigNumber.substract(bigNumber1, bigNumber2);
                    showResult(result);
                    FileIO.writeBigNumberToFile("result.out",result);
                    break;
                }
                case 3: {
                    BigNumber result = BigNumber.multiply(bigNumber1, bigNumber2);
                    showResult(result);
                    FileIO.writeBigNumberToFile("result.out",result);
                    break;
                }
                case 4: {
                    working = false;
                    break;
                }
                default: {
                    System.out.println("Optiune invalida! Mai incercati!");
                    System.out.println();
                    break;
                }

            }
        }


    }
}
