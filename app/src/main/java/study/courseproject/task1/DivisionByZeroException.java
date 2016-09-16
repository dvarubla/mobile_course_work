package study.courseproject.task1;

//ошибка деления на 0
class DivisionByZeroException extends ArithmeticException{
    DivisionByZeroException(String str){
        super(str);
    }
}
