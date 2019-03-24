package service;

import model.BigNumber;
import model.Sign;

public class BigNumberService {

    public static BigNumber add(BigNumber bigNumber1, BigNumber bigNumber2) {
        StringBuilder result = new StringBuilder();
        int rest = 0;
        int bigNumber1Length = bigNumber1.getActualNumber().length();
        int bigNumber2Length = bigNumber2.getActualNumber().length();
        for (int i = 1; i <= Math.min(bigNumber1Length, bigNumber2Length); i++) {
            int tempResult = bigNumber1.getActualNumber().charAt(bigNumber1Length - i) +
                    bigNumber2.getActualNumber().charAt(bigNumber2Length - i) - 96 + rest;
            rest = 0;
            if (tempResult >= 10) {
                rest = tempResult / 10;
                tempResult = tempResult - 10;
            }
            result.insert(0, tempResult);
        }
        if (bigNumber1Length != bigNumber2Length) {
            if (bigNumber1Length > bigNumber2Length) {
                copyRemainingNumberPart(bigNumber1, result, rest, bigNumber2Length, bigNumber1Length);
            } else {
                copyRemainingNumberPart(bigNumber2, result, rest, bigNumber1Length, bigNumber2Length);
            }
        }
        return new BigNumber(result.toString());
    }

    private static void copyRemainingNumberPart(BigNumber bigNumber, StringBuilder result, int rest, int bigNumber1Length, int bigNumber2Length) {
        for (int i = 1 + bigNumber1Length; i <= bigNumber2Length; i++) {
            int tempResult = bigNumber.getActualNumber().charAt(bigNumber2Length - i) - 48 + rest;
            if (!(i == bigNumber2Length && tempResult == 0)) {
                if (tempResult == 10) {
                    tempResult = 0;
                } else {
                    rest = 0;
                }
                result.insert(0, tempResult);
            }
        }
        if (rest == 1) {
            result.insert(0, 1);
        }
    }

    public static BigNumber substract(BigNumber bigNumber1, BigNumber bigNumber2) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        BigNumber leftTerm;
        BigNumber rightTerm;
        if (bigNumber1.getActualNumber().length() > bigNumber2.getActualNumber().length()) {
            leftTerm = bigNumber1;
            rightTerm = bigNumber2;
        } else {
            leftTerm = bigNumber2;
            rightTerm = bigNumber1;
        }
        int leftTermLength = leftTerm.getActualNumber().length();
        int rightTermLength = rightTerm.getActualNumber().length();
        for (int i = 1; i <= rightTermLength; i++) {
            int tempResult = leftTerm.getActualNumber().charAt(leftTermLength - i) -
                    rightTerm.getActualNumber().charAt(rightTermLength - i) - borrow;
            if (tempResult < 0) {
                tempResult += 10;
                borrow = 1;
            }
            result.insert(0, tempResult);
        }
        if (leftTermLength != rightTermLength) {
            copyRemainingNumberPart(leftTerm, result, borrow * (-1), rightTermLength, leftTermLength);
        } else {
            if (borrow == 1) {
                return new BigNumber(Sign.NEGATIVE, result.toString());
            }
        }
        if (bigNumber1.getActualNumber().length() > bigNumber2.getActualNumber().length()) {
            return new BigNumber(leftTerm.getBigNumberSign(), result.toString());
        } else return new BigNumber(Sign.NEGATIVE, result.toString());

    }

    public static BigNumber multiply(BigNumber bigNumber1, BigNumber bigNumber2) {
        BigNumber result = null;
        int count = 0;
        for (int i = bigNumber2.getActualNumber().length() - 1; i >= 0; i--) {
            BigNumber temporaryResult = singleDigitMultiplication(bigNumber1.getActualNumber(),
                    bigNumber2.getActualNumber().charAt(i));
            if(i == bigNumber2.getActualNumber().length() - 1){
                result = temporaryResult;
            }
            else{
                StringBuilder actualTemporaryResult = new StringBuilder(temporaryResult.getActualNumber());
                for(int j=0;j<count;j++){
                    actualTemporaryResult.append("0");
                }
                temporaryResult.setActualNumber(actualTemporaryResult.toString());
                result = add(result,temporaryResult);
            }
            count++;

        }
        return result;
    }

    private static BigNumber singleDigitMultiplication(String actualBigNumber, char digit) {
        StringBuilder result = new StringBuilder();
        if (digit == '0') {
            return new BigNumber("0");
        } else {
            int overflow = 0;
            for (int i = actualBigNumber.length() - 1; i >= 0; i--) {
                int tempResult = (actualBigNumber.charAt(i) - 48) * (digit - 48) + overflow;
                overflow = tempResult / 10;
                tempResult = tempResult % 10;
                result.insert(0, tempResult);
            }
            if (overflow > 0) {
                result.insert(0, overflow);
            }

        }
        return new BigNumber(result.toString());
    }

    public static BigNumber transformStringToBigNumber(String unprocessedBigNumber) {
        if (unprocessedBigNumber.charAt(0) == '+') {
            return new BigNumber(unprocessedBigNumber.substring(1));
        } else if (unprocessedBigNumber.charAt(0) == '-') {
            return new BigNumber(Sign.NEGATIVE, unprocessedBigNumber.substring(1));
        } else return new BigNumber(unprocessedBigNumber);
    }
}
