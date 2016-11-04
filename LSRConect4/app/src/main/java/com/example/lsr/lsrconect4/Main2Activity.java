package com.example.lsr.lsrconect4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.lsr.lsrconect4.R.id.table;

public class Main2Activity extends AppCompatActivity {

    // 游戏状态
    private int turn = 1;
    // 棋盘状态
    private int chessState[][] = null;
    // 棋盘棋子引用
    private ImageButton chessButton[][] = null;
    // 棋子堆栈
    private ArrayList STATCK = null;

    // UI
    private Button button_restart;
    private Button button_retract;
    private ImageView red;
    private ImageView green;
    private TextView text;

    int[] idList = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13,
            R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20,
            R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25, R.id.btn26, R.id.btn27,
            R.id.btn28, R.id.btn29, R.id.btn30, R.id.btn31, R.id.btn32, R.id.btn33, R.id.btn34,
            R.id.btn35, R.id.btn36, R.id.btn37, R.id.btn38, R.id.btn39, R.id.btn40, R.id.btn41,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    // 只初始化一次
    void init() {

        ImageButton btn;
        for (int i=0 ; i<=42 ; ++i) {
            int id = idList[i-1];
            btn = (ImageButton)findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int col = getCol(view.getId());
                    GoStep(col);
                }
            });
            chessButton[i/7][i%7] = btn;
        }

        button_restart = (Button) findViewById(R.id.restart);
        button_retract = (Button) findViewById(R.id.retarct);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);

        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button_retract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    int getCol(int id) {
        for (int i=0 ; i<42 ; i++) {
            if (idList[i] == id)
                return (i+1)%7;
        }
        System.out.print("getCol() error!");
        return 10000;
    }

    void GoStep(int col) {


        for (int i=6 ; i>=0 ; i--) {
            if(chessState[i][col] == 0) {
                // draw
                if (turn == 1){
                    chessButton[i][col].setImageResource(R.drawable.red_t);
                    green.setImageResource(R.drawable.green_wint);
                }else {
                    chessButton[i][col].setImageResource(R.drawable.green_t);
                    red.setImageResource(R.drawable.red_wint);
                }

                // 判断是否有人赢
            }
        }

    }

}
