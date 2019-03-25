package service;

import model.BigNumber;
import model.Sign;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BigNumberService {

    public static BigNumber add(BigNumber bigNumber1, BigNumber bigNumber2) {
        ArrayList<Byte> result = new ArrayList<>();
        int bigNumber1Length = bigNumber1.getActualNumber().length;
        int bigNumber2Length = bigNumber2.getActualNumber().length;
        int rest = 0;
        for (int i = 1; i <= Math.min(bigNumber1Length, bigNumber2Length); i++) {
            int tempResult = bigNumber1.getActualNumber()[bigNumber1Length - i] +
                    bigNumber2.getActualNumber()[bigNumber2Length - i] + rest;
            rest = 0;
            if (tempResult >= 10) {
                rest = tempResult / 10;
                tempResult = tempResult - 10;
            }
            result.add(0, (byte) tempResult);
        }
        if (bigNumber1Length != bigNumber2Length) {
            if (bigNumber1Length > bigNumber2Length) {
                copyRemainingNumberPart(bigNumber1, result, rest, bigNumber2Length, bigNumber1Length);
            } else {
                copyRemainingNumberPart(bigNumber2, result, rest, bigNumber1Length, bigNumber2Length);
            }
        }
        return new BigNumber(arrayListToArray(result));
    }

    private static byte[] arrayListToArray(ArrayList<Byte> arrayList) {
        byte[] resultAsArray = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            resultAsArray[i] = arrayList.get(i);
        }
        return resultAsArray;
    }

    private static ArrayList<Byte> arrayToArrayList(byte[] array) {
        ArrayList<Byte> arrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayList.add(array[i]);
        }
        return arrayList;
    }

    private static void copyRemainingNumberPart(BigNumber bigNumber, ArrayList<Byte> result, int rest, int bigNumber1Length, int bigNumber2Length) {
        for (int i = 1 + bigNumber1Length; i <= bigNumber2Length; i++) {
            int tempResult = bigNumber.getActualNumber()[bigNumber2Length - i] + rest;
            if (!(i == bigNumber2Length && tempResult == 0)) {
                if (tempResult == 10) {
                    tempResult = 0;
                } else {
                    rest = 0;
                }
                result.add(0, (byte) tempResult);
            }
        }
        if (rest == 1) {
            result.add(0, (byte) 1);
        }
    }

    public static BigNumber substract(BigNumber bigNumber1, BigNumber bigNumber2) {
        ArrayList<Byte> result = new ArrayList<>();
        int borrow = 0;
        BigNumber leftTerm;
        BigNumber rightTerm;
        if (bigNumber1.getActualNumber().length > bigNumber2.getActualNumber().length) {
            leftTerm = bigNumber1;
            rightTerm = bigNumber2;
        } else {
            leftTerm = bigNumber2;
            rightTerm = bigNumber1;
        }
        int leftTermLength = leftTerm.getActualNumber().length;
        int rightTermLength = rightTerm.getActualNumber().length;
        for (int i = 1; i <= rightTermLength; i++) {
            int tempResult = leftTerm.getActualNumber()[leftTermLength - i] -
                    rightTerm.getActualNumber()[rightTermLength - i] - borrow;
            if (tempResult < 0) {
                tempResult += 10;
                borrow = 1;
            }
            result.add(0, (byte) tempResult);
        }
        if (leftTermLength != rightTermLength) {
            copyRemainingNumberPart(leftTerm, result, borrow * (-1), rightTermLength, leftTermLength);
        } else {
            if (borrow == 1) {

                return new BigNumber(Sign.NEGATIVE, arrayListToArray(result));
            }
        }
        if (bigNumber1.getActualNumber().length > bigNumber2.getActualNumber().length) {
            return new BigNumber(leftTerm.getBigNumberSign(), arrayListToArray(result));
        } else return new BigNumber(Sign.NEGATIVE, arrayListToArray(result));

    }

    public static BigNumber multiply(BigNumber bigNumber1, BigNumber bigNumber2) {
        BigNumber result = null;
        int count = 0;
        for (int i = bigNumber2.getActualNumber().length - 1; i >= 0; i--) {
            BigNumber temporaryResult = singleDigitMultiplication(bigNumber1.getActualNumber(),
                    bigNumber2.getActualNumber()[i]);
            if (i == bigNumber2.getActualNumber().length - 1) {
                result = temporaryResult;
            } else {
                ArrayList<Byte> actualTemporaryResult = arrayToArrayList(temporaryResult.getActualNumber());
                for (int j = 0; j < count; j++) {
                    actualTemporaryResult.add((byte) 0);
                }
                temporaryResult.setActualNumber(arrayListToArray(actualTemporaryResult));
                result = add(result, temporaryResult);
            }
            count++;

        }
        return result;
    }

    private static BigNumber singleDigitMultiplication(byte[] actualBigNumber, byte digit) {
        ArrayList<Byte> result = new ArrayList<>();
        if (digit == '0') {
            byte[] zero = new byte[1];
            return new BigNumber(zero);
        } else {
            int overflow = 0;
            for (int i = actualBigNumber.length - 1; i >= 0; i--) {
                int tempResult = actualBigNumber[i] * digit + overflow;
                overflow = tempResult / 10;
                tempResult = tempResult % 10;
                result.add(0, (byte) tempResult);
            }
            if (overflow > 0) {
                result.add(0, (byte) overflow);
            }

        }
        return new BigNumber(arrayListToArray(result));
    }

    private static byte[] stringToByteArray(String unprocessedBigNumberString) {
        byte[] actualNumber = new byte[unprocessedBigNumberString.length()];
        for (int i = 0; i < unprocessedBigNumberString.length(); i++) {
            actualNumber[i] = (byte) (unprocessedBigNumberString.charAt(i) - 48);
        }
        return actualNumber;
    }

    public static BigNumber transformStringToBigNumber(String unprocessedBigNumber) {
        if (unprocessedBigNumber.charAt(0) == '+') {

            return new BigNumber(stringToByteArray(unprocessedBigNumber.substring(1)));
        } else if (unprocessedBigNumber.charAt(0) == '-') {
            return new BigNumber(Sign.NEGATIVE, stringToByteArray(unprocessedBigNumber.substring(1)));
        } else return new BigNumber(stringToByteArray(unprocessedBigNumber));
    }
}
