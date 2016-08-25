package study.courseproject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CalcModel implements ICalcModel {
    final int NUM_OPS=2;
    final int SCALE=10;
    private ICalcModelListener listener;
    private BigInteger bInts[];
    private BigDecimal bFloats[];
    private int currentOp;
    private boolean floatMode;
    private CalcOpTypes.OpType type;

    CalcModel(){
        bInts=new BigInteger[NUM_OPS];
        bFloats=new BigDecimal[NUM_OPS];
        currentOp=0;
        floatMode=false;
    }

    @Override
    public void setListener(ICalcModelListener listener){
        this.listener=listener;
    }

    private void checkNullListener(){
        if(listener==null){
            throw new NullPointerException("You forgot to set listener");
        }
    }

    @Override
    public void addNumber(String number){
        checkNullListener();
        addNumberWithIndex(number, currentOp);
        currentOp++;
    }

    @Override
    public void replaceNumber(String number){
        checkNullListener();
        addNumberWithIndex(number, currentOp-1);
    }

    private void addNumberWithIndex(String number, int index){
        if(index>=NUM_OPS){
            throw new ArrayIndexOutOfBoundsException("Op index is greater than array size");
        }
        if(number.contains(".") || floatMode){
            if(number.equals(".")){
                number="0";
            }
            if(!floatMode){
                for(int i=0; i<index; i++){
                    bFloats[i]=new BigDecimal(bInts[i]);
                }
                floatMode=true;
            }
            bFloats[index]=new BigDecimal(number);

        } else {
            bInts[index]=new BigInteger(number);
        }
    }

    private boolean checkDivZero(){
        if(
                (floatMode && bFloats[1].equals(BigDecimal.ZERO))
                || bInts[1].equals(BigInteger.ZERO)
                ){
            listener.notifyError(new DivisionByZeroException("aa"));
            return true;
        }
        return false;
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
                if(checkDivZero()){
                    return;
                }
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
            default:
                throw new IllegalArgumentException("Unknown op type "+type);
        }
        if(floatMode){
            listener.notifyResult(bFloats[0].stripTrailingZeros().toPlainString());
        } else {
            listener.notifyResult(bInts[0].toString());
        }
    }

    @Override
    public void addOperator(CalcOpTypes.OpType type){
        checkNullListener();
        if(currentOp==NUM_OPS){
            performAction();
        }
        this.type=type;
    }
}
