package model;

import service.BigNumberService;

public class BigNumber {

    private Sign bigNumberSign;
    private String actualNumber;

    public BigNumber(Sign bigNumberSign, String actualNumber) {
        this.bigNumberSign = bigNumberSign;
        this.actualNumber = actualNumber;
    }

    public BigNumber(String actualNumber) {
        this.bigNumberSign = Sign.POSITIVE;
        this.actualNumber = actualNumber;
    }

    public Sign getBigNumberSign() {
        return bigNumberSign;
    }

    public void setBigNumberSign(Sign bigNumberSign) {
        this.bigNumberSign = bigNumberSign;
    }

    public String getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    public static BigNumber add(BigNumber bigNumber1, BigNumber bigNumber2) {
        BigNumber result;
        if(bigNumber1.getBigNumberSign() == Sign.NEGATIVE && bigNumber2.getBigNumberSign() == Sign.POSITIVE ||
                bigNumber1.getBigNumberSign() == Sign.POSITIVE && bigNumber2.getBigNumberSign() == Sign.NEGATIVE ){
            result = BigNumberService.substract(bigNumber1,bigNumber2);
        }
        else if(bigNumber1.getBigNumberSign() == Sign.NEGATIVE && bigNumber2.getBigNumberSign() == Sign.NEGATIVE){
            result = BigNumberService.add(bigNumber1,bigNumber2);
            result.setBigNumberSign(Sign.NEGATIVE);
        }
        else{
            result = BigNumberService.add(bigNumber1,bigNumber2);
        }
        return result;
    }

    public static BigNumber substract(BigNumber bigNumber1, BigNumber bigNumber2){
        BigNumber result;
        if(bigNumber1.getBigNumberSign() == Sign.NEGATIVE && bigNumber2.getBigNumberSign() == Sign.POSITIVE ){
            result = BigNumberService.add(bigNumber1,bigNumber2);
            result.setBigNumberSign(Sign.NEGATIVE);
        }
        else if(bigNumber1.getBigNumberSign() == Sign.POSITIVE && bigNumber2.getBigNumberSign() == Sign.NEGATIVE){
            bigNumber2.setBigNumberSign(Sign.POSITIVE);
            result = BigNumberService.add(bigNumber1,bigNumber2);
            bigNumber2.setBigNumberSign(Sign.NEGATIVE);

        }
        else{
            result = BigNumberService.substract(bigNumber1,bigNumber2);
        }
        return result;
    }

    public static BigNumber multiply(BigNumber bigNumber1, BigNumber bigNumber2){
        BigNumber result = BigNumberService.multiply(bigNumber1,bigNumber2);
        if(bigNumber1.getBigNumberSign() != bigNumber2.getBigNumberSign()){
            result.setBigNumberSign(Sign.NEGATIVE);
        }
        return result;
    }
}
