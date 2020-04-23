package com.example.embedded3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button calcButton;
    EditText numEdit;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcButton = findViewById(R.id.calculate_button);
        numEdit = findViewById(R.id.number_edit_text);
        outputText = findViewById(R.id.result_text);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(numEdit.getText().toString());
                if (isPrime(number)) {
                    outputText.setText("Ви ввели просте число.");
                } else {
                    long start = System.nanoTime();
                    ArrayList<Integer> resultArray = fermaFactorization(number);
                    long end = System.nanoTime();
                    output(resultArray, end-start);
                }
            }
        });
    }

    private ArrayList<Integer> fermaFactorization(int num) {
        ArrayList<Integer> resultArray = new ArrayList<>();
        int s = (int)Math.sqrt(num);
        int y;
        double radical;
        for (int k = 0; k < num; k++) {
            y = (int)Math.pow(s + k, 2) - num;
            radical = Math.sqrt(y);
            if (radical % 1 == 0.0) {
                Integer mul1 = (int)(s + k + radical);
                Integer mul2 = (int)(s + k - radical);
                if (isPrime(mul1)) {
                    resultArray.add(mul1);
                } else {
                    resultArray.addAll(fermaFactorization(mul1));
                }
                if (isPrime(mul2)) {
                    resultArray.add(mul2);
                } else {
                    resultArray.addAll(fermaFactorization(mul2));
                }
                break;
            }
        }
        return resultArray;
    }


    private boolean isPrime(int n) {
        if (n == 2) return true;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    private void output(ArrayList<Integer> resultArray, long time) {
        StringBuilder temp = new StringBuilder();
        temp.append("Прості множники введеного числа: ");
        temp.append(resultArray.toString());
        temp.append("\nЗАТРАЧЕНО ЧАСУ: ");
        temp.append(time/1000);
        temp.append(" мкс.");
        outputText.setText(temp);
    }
}