package study.courseproject.task1;

import java.math.BigDecimal;
import java.math.BigInteger;

class CalcModel implements ICalcModel {
    private final int NUM_OPS=2;
    private final int SCALE=10;
    private ICalcModelListener listener;
    private BigInteger bInts[];
    private BigDecimal bFloats[];
    private int currentOp;
    private boolean floatMode;
    private CalcOpType type;

    CalcModel(){
        bInts=new BigInteger[NUM_OPS];
        bFloats=new BigDecimal[NUM_OPS];
        reset();
    }

    public void setListener(ICalcModelListener listener){
        this.listener=listener;
    }

    @Override
    public void reset(){
        currentOp=0;
        floatMode=false;
    }

    private void processError(Exception exc){
        reset();
        listener.notifyError(exc);
    }

    @Override
    public void addNumber(String number){
        try {
            addNumberWithIndex(number, currentOp);
            currentOp++;
        } catch(Exception e){
            processError(e);
        }
    }

    @Override
    public void replaceNumber(String number){
        try {
            addNumberWithIndex(number, currentOp - 1);
        } catch(Exception e){
            processError(e);
        }
    }

    private void addNumberWithIndex(String number, int index){
        if(index>=NUM_OPS){
            throw new ArrayIndexOutOfBoundsException("Op index is greater than array size");
        }

        try {
            if(number.contains(".") || floatMode){
                if(number.equals(".")){
                    number="0";
                }
                if(!floatMode){
                    for(int i=0; i<index; i++){
                        bFloats[i] = new BigDecimal(bInts[i]);
                    }
                    floatMode=true;
                }
                bFloats[index] = new BigDecimal(number);
            } else {
                bInts[index]=new BigInteger(number);
            }
        } catch(NumberFormatException e){
            throw new SyntaxError("Number: "+number+" index: "+index);
        }

    }

    private void checkDivZero(){
        if(
                (floatMode && bFloats[1].equals(BigDecimal.ZERO))
                || (!floatMode && bInts[1].equals(BigInteger.ZERO))
                ){
            throw new DivisionByZeroException("FloatMode:"+floatMode+", type:"+type);
        }
    }

    private void performAction(){
        if(type==null){
            throw new NullPointerException("Op type is null");
        }
        switch(type){
            case EQ:
                if(floatMode){
                    bFloats[0]=bFloats[1];
                } else {
                    bInts[0]=bInts[1];
                }
                currentOp=1;
                break;
            case PLUS:
                if(floatMode){
                    bFloats[0]=bFloats[0].add(bFloats[1]);
                } else {
                    bInts[0]=bInts[0].add(bInts[1]);
                }
                currentOp=1;
                break;
            case MINUS:
                if(floatMode){
                    bFloats[0]=bFloats[0].subtract(bFloats[1]);
                } else {
                    bInts[0]=bInts[0].subtract(bInts[1]);
                }
                currentOp=1;
                break;
            case MUL:
                if(floatMode){
                    bFloats[0]=bFloats[0].multiply(bFloats[1]);
                } else {
                    bInts[0]=bInts[0].multiply(bInts[1]);
                }
                currentOp=1;
                break;
            case FLOAT_DIV:
                currentOp=1;
                checkDivZero();
                if(!floatMode){
                    BigInteger tInts[]=bInts[0].divideAndRemainder(bInts[1]);
                    if(tInts[1].equals(BigInteger.ZERO)){
                        bInts[0]=tInts[0];
                    } else {
                        floatMode=true;
                        for(int i=0; i<NUM_OPS; i++){
                            bFloats[i]=new BigDecimal(bInts[i]);
                        }
                    }
                }
                if(floatMode){
                    bFloats[0]=bFloats[0].divide(bFloats[1], SCALE, BigDecimal.ROUND_HALF_UP);
                }
                break;
            case MOD:
                currentOp=1;
                checkDivZero();
                if(floatMode){
                    floatMode=false;
                    convertNumbersToInteger();
                }
                bInts[0]=bInts[0].remainder(bInts[1]);
                break;
            case DIV:
                currentOp=1;
                checkDivZero();
                if(floatMode){
                    floatMode=false;
                    convertNumbersToInteger();
                }
                bInts[0]=bInts[0].divide(bInts[1]);
                break;
            default:
                throw new IllegalArgumentException("Unknown op type "+type);
        }
        if(floatMode){
            listener.notifyResult(bFloats[0].stripTrailingZeros().toPlainString());
        } else {
            listener.notifyResult(bInts[0].toString());
        }
    }

    private void convertNumbersToInteger(){
        for(int i=0; i<NUM_OPS; i++){
            bInts[i]=bFloats[i].setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger();
        }
    }

    @Override
    public void addOperator(CalcOpType type){
        try {
            if (currentOp == NUM_OPS) {
                performAction();
            }
            this.type = type;
        } catch(Exception e){
            processError(e);
        }
    }
}
