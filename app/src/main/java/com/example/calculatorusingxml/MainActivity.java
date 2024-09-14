package com.example.calculatorusingxml;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonDEL, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.c);
        assignId(buttonBrackOpen, R.id.bracket1);
        assignId(buttonBrackClose, R.id.bracket2);
        assignId(buttonDivide, R.id.slash);
        assignId(buttonMultiply, R.id.multiply);
        assignId(buttonPlus, R.id.add);
        assignId(buttonMinus, R.id.subtract);
        assignId(buttonEquals, R.id.equal);
        assignId(button0, R.id.zero);
        assignId(button1, R.id.one);
        assignId(button2, R.id.two);
        assignId(button3, R.id.three);
        assignId(button4, R.id.four);
        assignId(button5, R.id.five);
        assignId(button6, R.id.six);
        assignId(button7, R.id.seven);
        assignId(button8, R.id.eight);
        assignId(button9, R.id.nine);
        assignId(buttonDEL, R.id.del);
        assignId(buttonDot, R.id.point);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("DEL")){
            if(dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else if(buttonText.equals("=")){
            String finalResult = getResult(dataToCalculate);
            if(!finalResult.equals("Err")) {
                resultTv.setText(finalResult);
            }
            solutionTv.setText(finalResult); // Update solutionTv with final result
            return;
        } else if(buttonText.equals("C")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        } else {
            dataToCalculate += buttonText;
        }

        solutionTv.setText(dataToCalculate);
    }

    String getResult(String data){
        try {
            // Replace 'x' with '*' for multiplication
            data = data.replace("x", "*");

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            Context.exit();
            return finalResult;
        } catch (Exception e) {
            e.printStackTrace();
            return "Err";
        }
    }
}
