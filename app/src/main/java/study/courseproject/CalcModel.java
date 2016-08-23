package study.courseproject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CalcModel implements ICalcModel {
    final int NUM_OPS=2;
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
        if(currentOp>=NUM_OPS){
            throw new ArrayIndexOutOfBoundsException("Op index is greater than array size");
        }
        if(number.contains(".") || floatMode){
            if(!floatMode){
                for(int i=0; i<currentOp; i++){
                    bFloats[i]=new BigDecimal(bInts[i]);
                }
                floatMode=true;
            }
            bFloats[currentOp]=new BigDecimal(number);

        } else {
            bInts[currentOp]=new BigInteger(number);
        }
        currentOp++;
    }

    private void performAction(){
        if(type==null){
            throw new NullPointerException("Op type is null");
        }
        switch(type){
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
            default:
                throw new IllegalArgumentException("Unknown op type "+type);
        }
        if(floatMode){
            listener.notifyResult(bFloats[0].toString());
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
