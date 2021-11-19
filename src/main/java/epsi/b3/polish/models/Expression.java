package epsi.b3.polish.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Expression {
    int minLength;
    int maxLenght;
    ArrayList<String> operationList;
    ArrayList<ArrayList<String>> expressionsList;
    ArrayList<Integer> resultList;

    //exemple : minLenght=5 / maxLenght=7
    public Expression(int minLength, int maxLenght){
        this.minLength = minLength;
        this.maxLenght = maxLenght;
        this.operationList = new ArrayList<>(Arrays.asList("+","-","*","/","inv","rac"));
        this.expressionsList = new ArrayList<>();
        this.resultList = new ArrayList<>();
        //générer 10 expressions
        for (int numberOfExpressions = 0 ; numberOfExpressions<10; numberOfExpressions++ ){
            //créer une longueur variable pour chaque expression : range entre minLength et maxLenght
            double lenghtExpression = (int)(Math.random()*( (maxLenght-minLength)+1) )+minLength;
            //loop pour générer l'expression
            int numberOfOperation = (int)Math.floor(lenghtExpression/2);
            int numberOfNumbers = (int)Math.ceil(lenghtExpression/2);

            //on génére les opératioins
            ArrayList<String> expressionList = new ArrayList<>();
            for(int currentOp=0;currentOp<numberOfOperation;currentOp++){
                int index = (int)(Math.random()*( (5+0)+1) )+0;
                String currentOpValue = operationList.get(index);
                expressionList.add(currentOpValue);
            }
            //on génére les nombres
            ArrayList<Integer> numberList = new ArrayList<>();
            for(int currentNumber=0;currentNumber<numberOfNumbers;currentNumber++){
                int randomNumber = (int)(Math.random()*( (9+0)+1) )+0;
                numberList.add(randomNumber);
            }

            //on génére les expression
            for (int  currentLenghtExpression=0 ;  currentLenghtExpression<lenghtExpression;  currentLenghtExpression++){

            }
        }
    }
}

//généré alétatoirement les expressions
//et leurs résultats
