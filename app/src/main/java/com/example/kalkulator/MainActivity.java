
package com.example.kalkulator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {

    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MUL = '*';
    private static final char DIV = '/';


    //'0' pomeni "ni nobene operacije". Ne more biti kar prazen '', ker char vedno potrebuje en znak. '0'


    private char currentOp = '0';

    private double firstValue = Double.NaN;
    private boolean lastWasEqual = false;

    private TextView screen1Display, screen2Display;


    private DecimalFormat formatter;



    // za sestavljanje matematičnega izraza
    private StringBuilder expression = new StringBuilder();



    @Override


    protected void onCreate(Bundle savedInstanceState) {


        //skrije status bar
         getWindow().getDecorView().setSystemUiVisibility(
               View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_main);


        //DecimalFormat-- Oblikovanje števil
        //Ustvarimo objekt DecimalFormatSymbols--Ta objekt hrani nastavitve za oblikovanje števil.
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        //Nastavi ločilo za tisočice
        symbols.setGroupingSeparator('.');


        symbols.setDecimalSeparator(',');


        //določa, kako se številke prikažejo v kalkulatorju.
        formatter = new DecimalFormat("#,###.##########", symbols);





        screen1Display = findViewById(R.id.screen1);

        screen2Display = findViewById(R.id.screen2);


        //int[] je polje (array) celih števil Lahko shrani več vrednosti istega tipa
        // Definiraš polje z ID-ji

        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };














        View.OnClickListener numberListener = v -> {

            // "“Spremenljivka v predstavlja kliknjen element. Pretvorim jo v Button, da lahko preberem besedilo gumba, npr. ‘5’.”
            Button b = (Button) v;

            if (lastWasEqual) {
                screen2Display.setText("");
                screen1Display.setText("");
                lastWasEqual = false;
            }

            String text = screen2Display.getText().toString();


            String raw = text.replace(".", "").replace(",", "");

            if (raw.length() >= 10) return;

            // da ne doda nove vejce
            if (text.contains(",")) {
                screen2Display.setText(text + b.getText().toString());
                return;
            }


            String clean = text.replace(".", "") + b.getText().toString();


            //Poskusi formatirati in prikazati
            try {
                long value = Long.parseLong(clean);// “Poskuša pretvoriti niz v celo številko.

                screen2Display.setText(formatter.format(value));

                //prikaže bres formatiranja(2,3)
            } catch (Exception e) {
                screen2Display.setText(clean);


            }
        };


        for (int id : numberButtons) {

               //am ugotovi, kateri gumb je bil pritisnjen
            findViewById(id).setOnClickListener(numberListener);
        }











        findViewById(R.id.btnPoint).setOnClickListener(v -> {
            if (lastWasEqual) {
                screen1Display.setText("");
                screen2Display.setText("");
                lastWasEqual = false;
            }

            String text = screen2Display.getText().toString();
            if (!text.contains(",")) {

                screen2Display.setText(text.isEmpty() ? "0," : text + ",");
            }
        });



        findViewById(R.id.add).setOnClickListener(v -> setOperator(ADD, "+"));
        findViewById(R.id.subtract).setOnClickListener(v -> setOperator(SUB, "-"));
        findViewById(R.id.multiply).setOnClickListener(v -> setOperator(MUL, "×"));
        findViewById(R.id.division).setOnClickListener(v -> setOperator(DIV, "÷"));
        findViewById(R.id.percent).setOnClickListener(v -> applyPercent());







        Button buttonBack = findViewById(R.id.backspace);

        buttonBack.setOnClickListener(v -> {

            String text = screen2Display.getText().toString();


            if (text.length() > 1) {

                screen2Display.setText(text.substring(0, text.length() - 1));
            } else {

                screen2Display.setText("");
            }
        });






        findViewById(R.id.clear).setOnClickListener(v -> {

            firstValue = Double.NaN;
            currentOp = '0';
            lastWasEqual = false;
            screen2Display.setText("");
            screen1Display.setText("");
        });



        //GUMB  (=)
        findViewById(R.id.equal).setOnClickListener(v -> calculateResult());






    }





    private void setOperator(char op, String symbolText) {


        // kalkulator vzame rezultat kot prvo številko
        if (lastWasEqual) {

            firstValue = Double.parseDouble(screen2Display.getText().toString().replace(".", "").replace(",", "."));
            lastWasEqual = false;

            String temp = formatWithScientificNotation(firstValue);
            screen1Display.setText(temp + " " + symbolText);
            currentOp = op;//nastavi operacijo
            screen2Display.setText("");
            return;
        }




       //se izvede ko so izpolnjeni 3 pogoji(firstValue(5),currentOp(+),secondValue(3)   )
        if (!Double.isNaN(firstValue) && currentOp != '0' && screen2Display.getText().length() > 0) {
            calculate();


            String temp = formatWithScientificNotation(firstValue);

            screen1Display.setText(temp + " " + symbolText);

        }






        else if (screen2Display.getText().length() > 0) {


            double val = Double.parseDouble(screen2Display.getText().toString().replace(".", "").replace(",", "."));


            //Če firstValue še ni nastavljena → nastavi jo
            if (Double.isNaN(firstValue)) firstValue = val;


            screen1Display.setText(screen2Display.getText().toString() + " " + symbolText);
        }

        currentOp = op;
    }







    private void calculateResult() {
        if (lastWasEqual) return;    //ne računaj

        String input = screen2Display.getText().toString();

        if (currentOp == '0') {


            if (input.isEmpty()) return;

            //✔ Pretvori vnos v število
            firstValue = Double.parseDouble(
                    input.replace(".", "").replace(",", ".")
            );

            // Oblikuj rezultat za prikaz
            String result = formatWithScientificNotation(firstValue);


            // Prikaži na spodnjem zaslonu
            screen2Display.setText(result);

            screen1Display.setText(result + " =");


            // Zapomni si, da smo pritisnili "="
            lastWasEqual = true;

            return;
        }

        //Shrani izraz, ki je bil na zgornjem zaslonu
        String leftSide = screen1Display.getText().toString();

        String rightSide = input;

        // Izvedi izračun (5 + 3 = 8, firstValue postane 8)
        calculate();






        //po izračunu



        String result = formatWithScientificNotation(firstValue);


        screen2Display.setText(result);//8

        screen1Display.setText(leftSide + " " + rightSide + " =");

        lastWasEqual = true;
    }













    private void calculate() {

        if (screen2Display.getText().length() == 0) return;



        String clean = screen2Display.getText().toString()
                .replace(".", "")
                .replace(",", ".");

      //Pretvori niz v število:
        double secondValue = Double.parseDouble(clean);


        if (!Double.isNaN(firstValue)) {
            switch (currentOp) {
                case ADD: firstValue += secondValue; break;
                case SUB: firstValue -= secondValue; break;
                case MUL: firstValue *= secondValue; break;
                case DIV: firstValue /= secondValue; break;
            }
        } else {

            firstValue = secondValue;
        }
    }











    // RAČUNANJE %
    private void applyPercent() {


        String input = screen2Display.getText().toString()
                .replace(".", "")
                .replace(",", ".");


        if (input.isEmpty()) return;


        double value = Double.parseDouble(input);


        double percent = value / 100.0;

        String formatted = formatWithScientificNotation(percent);
        screen2Display.setText(formatted);
    }


    //Metoda sprejme double in vrne String. Namen je formatiranje števil
    private String formatWithScientificNotation(double value) {

        if (Math.abs(value) < 1e10) {

              //manjša od 10.000.000.000, uporabi normalno oblikovanje

            return formatter.format(value);
        }


        String sci = String.format(java.util.Locale.US, "%.9e", value);

        sci = sci.replace('.', ',');


        return sci;
    }
}