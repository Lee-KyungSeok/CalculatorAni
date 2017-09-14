package com.example.kyung.basiccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// View.OnClickListener를 상속 받는 것으로 이번에는 한다.
public class CaculatorActivity extends AppCompatActivity
        implements  View.OnClickListener{

    String preViewText="";
    String resultViewText="";
    String preInput="";
    int leftParenthesesCount=0;
    int rightParenthesesCount=0;

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button btnSum,btnSubtracttion,btnMultiplication,btnDivison, btnClear, btnRun, btnLeftParentheses, btnRightParentheses;
    Button btnDot;

    TextView textViewPreView, textViewResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);

        initView();
        initListener();

    }

    // 이렇게 하면 this를 할때 MainActivity.this 라고 안해도 된다.
    // 이외는 차이점이 없다.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn0:
                inputPreView("0");
                break;
            case R.id.btn1:
                inputPreView("1");
                break;
            case R.id.btn2:
                inputPreView("2");
                break;
            case R.id.btn3:
                inputPreView("3");
                break;
            case R.id.btn4:
                inputPreView("4");
                break;
            case R.id.btn5:
                inputPreView("5");
                break;
            case R.id.btn6:
                inputPreView("6");
                break;
            case R.id.btn7:
                inputPreView("7");
                break;
            case R.id.btn8:
                inputPreView("8");
                break;
            case R.id.btn9:
                inputPreView("9");
                break;
            case R.id.btnDot:
                inputPreView(".");
                break;
            case R.id.btnSum:
                inputPreView("+");
                break;
            case R.id.btnSubtraction:
                inputPreView("-");
                break;
            case R.id.btnMultiplication:
                inputPreView("*");
                break;
            case R.id.btnDivision:
                inputPreView("/");
                break;
            case R.id.btnClear:
                preViewText="입력창";
                resultViewText="결과창";
                preInput="=";
                textViewPreView.setText(preViewText);
                textViewResultView.setText(resultViewText);
                break;
            case R.id.btnLeftParentheses:
                inputPreView("(");
                break;
            case R.id.btnRightParentheses:
                inputPreView(")");
                break;
            case R.id.btnRun:
                if(preViewText.equals("입력창")){break;}
                double result = calc(preViewText);
                inputResultView(""+result);
                break;
        }
    }
    private double calc(String preViewText){
        double result;
        // 34 + 15 * 3 - 5 / 10
        String reg = "(?<=[*/+-])|(?=[*/+-])|(?<=[\\(\\)])|(?=[\\(\\)])";
        String splittedText[] = preViewText.split(reg);

        //for 문으로 * 와 / 를 먼저 처리하고
        // + - 나중에 처리

        // #1
        ArrayList<String> tempCalculation = new ArrayList<>();

        for(String item : splittedText){
            System.out.println("무냐=========="+item);
            tempCalculation.add(item);
        }


        while(true){

            if(preViewText.equals("")) {break;}
            boolean checkMDOperation=false;
            int leftParenthesis=0;
            int rightParenthesis=0;
            String tempRec ="";

            for(int i=0; i<tempCalculation.size() ; i++) {
                if(tempCalculation.get(i).equals(")")) {
                    rightParenthesis=i;
                    break;
                }
            }

            for(int i=rightParenthesis; i>=0 ; i--) {
                if(tempCalculation.get(i).equals("(")) {
                    leftParenthesis=i;
                    break;
                }
            }
            //Log.d("1차 결과값", "size2:"+tempCalculation.size()+"");
            for(int i=leftParenthesis+1 ; i<rightParenthesis ;i++) {
                tempRec=tempRec+tempCalculation.get(i);
            }
            Log.e("괄호식", tempRec);

            if(!tempRec.equals("")) {
                Log.d("1차 결과값", "size2:"+tempCalculation.size()+"");
                double calParenthesis = calc(tempRec);
                Log.e("1차 결과값", calParenthesis+"");
                tempCalculation.set(leftParenthesis,calParenthesis+"");
                for(int i =0 ; i<rightParenthesis-leftParenthesis ; i++) {
                    tempCalculation.remove(leftParenthesis+1);
                }
                Log.d("1차 결과값", "size2:"+tempCalculation.size()+"");
               for(int i=0; i<tempCalculation.size(); i++){
                   Log.e("temCalculation 인덱스", i+"");
                   Log.e("temCalculation 요소", "i:"+i+", "+ tempCalculation.get(i));
               }
                continue;
            }

            for(int i=0 ; i<tempCalculation.size() ; i++) {
                if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                    checkMDOperation=true;
                }
            }
            for(int i=0 ; i<tempCalculation.size() ; i++){
                double tempResult=0;
                if(tempCalculation.get(i).equals("*") || tempCalculation.get(i).equals("/")){
                    if(tempCalculation.get(i).equals("*")) {
                        tempResult = Double.parseDouble(tempCalculation.get(i-1)) * Double.parseDouble(tempCalculation.get(i+1));
                    } else{
                        tempResult = Double.parseDouble(tempCalculation.get(i-1)) / Double.parseDouble(tempCalculation.get(i+1));
                    }

                    tempCalculation.set(i,tempResult+"");
                    tempCalculation.remove(i-1);
                    tempCalculation.remove(i);
                    break;
                }
                if(checkMDOperation == false && (tempCalculation.get(i).equals("+") || tempCalculation.get(i).equals("-"))){
                    if(tempCalculation.get(i).equals("+")) {
                        tempResult = Double.parseDouble(tempCalculation.get(i - 1)) + Double.parseDouble(tempCalculation.get(i+1));
                    } else{
                        tempResult = Double.parseDouble(tempCalculation.get(i - 1)) - Double.parseDouble(tempCalculation.get(i+1));
                    }
                    tempCalculation.set(i,tempResult+"");
                    tempCalculation.remove(i-1);
                    tempCalculation.remove(i);
                    break;
                }
            }
            if(tempCalculation.size()==1){
                break;
            }
        }
        result = Double.parseDouble(tempCalculation.get(0));
        return result;
    }

    public void inputPreView(String value){
        //연산자가 처음에 오면 오류
        if(textViewPreView.getText().toString().equals("입력창") || preInput.equals("=")) {
            if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")) {
                Toast.makeText(this, "0 보다 큰 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            preViewText="";
        }

        // 0이 처음에 연속해서 두번 나올 경우
        if(preViewText.equals("0") && value.equals("0")){
            Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        //연속된 연산자 오류
        if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")){
            if(value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")){
                Toast.makeText(this, "연속해서 연산자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(value.equals("0")) {
                Toast.makeText(this, "0을 처음에 넣을 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // dot을 연속해서 쓰는 경우
        if(value.equals(".") && preInput.equals(".")){
            Toast.makeText(this, "연속해서 dot을 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 숫자 뒤에 과로가 나올 경우 자동 곱하기 계산
        if((!preInput.equals("+")) || (!preInput.equals("-")) || (!preInput.equals("*")) || (!preInput.equals("/")) || (!preInput.equals("="))
                || (!textViewPreView.getText().toString().equals("입력창")) ){
            if(value.equals("(")){
                preViewText=preViewText+"*";
            }
        }

        //결과 다음 입력시 preView 초기화
        if(preInput.equals("=")){
            preViewText="";
            resultViewText="결과창";
            textViewResultView.setText(resultViewText);
        }

        // 처음 숫자가 dot인 경우 0을 써줌
        if((preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/")
                || preInput.equals("=") || textViewPreView.getText().toString().equals("입력창")) && value.equals(".")){
            preViewText=preViewText+"0";
        }

        //과로 입력시 카운트 추가
        if(value.equals("(")){
            leftParenthesesCount++;
        }
        if(value.equals(")")){
            rightParenthesesCount++;
        }



        preViewText=preViewText+value;
        preInput=value;
        textViewPreView.setText(preViewText);
    }

    public void inputResultView(String total){
        if(preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/") || preInput.equals("=")){
            Toast.makeText(this,"잘못된 입력 형식입니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        textViewResultView.setText(total);
        preInput="=";
    }


    private void initView(){
        btn0 = (Button) findViewById(R.id.btn1);
        btn1 = (Button) findViewById(R.id.btn2);
        btn2 = (Button) findViewById(R.id.btn3);
        btn3 = (Button) findViewById(R.id.btn4);
        btn4 = (Button) findViewById(R.id.btn5);
        btn5 = (Button) findViewById(R.id.btn6);
        btn6 = (Button) findViewById(R.id.btn7);
        btn7 = (Button) findViewById(R.id.btn8);
        btn8 = (Button) findViewById(R.id.btn9);
        btn9 = (Button) findViewById(R.id.btn0);
        btnSum = (Button) findViewById(R.id.btnSum);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnSubtracttion = (Button) findViewById(R.id.btnSubtraction);
        btnMultiplication = (Button) findViewById(R.id.btnMultiplication);
        btnDivison = (Button) findViewById(R.id.btnDivision);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnRun = (Button) findViewById(R.id.btnRun);
        btnLeftParentheses = (Button) findViewById(R.id.btnLeftParentheses);
        btnRightParentheses = (Button) findViewById(R.id.btnRightParentheses);

        textViewPreView = (TextView) findViewById(R.id.textViewPreView);
        textViewResultView = (TextView) findViewById(R.id.textViewResultView);
    }
    private void initListener(){
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnSum.setOnClickListener(this);
        btnSubtracttion.setOnClickListener(this);
        btnMultiplication.setOnClickListener(this);
        btnDivison.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnRun.setOnClickListener(this);
        btnLeftParentheses.setOnClickListener(this);
        btnRightParentheses.setOnClickListener(this);
    }
}
