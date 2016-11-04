package com.example.lsr.lsrconect4;

import android.graphics.Point;
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

    // 棋盘状态
    private int turn = 1;
    private int result = 0;
    // 棋盘状态
    private int chessState[][] = null;
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
            R.id.btn35, R.id.btn36, R.id.btn37, R.id.btn38, R.id.btn39, R.id.btn40, R.id.btn41};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();

        newGameInit();
    }

    // 只初始化一次
    void init() {

        for (int i=1 ; i<=42 ; i++) {
            ImageButton btn = (ImageButton)findViewById(idList[i-1]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result == 0)
                        return;
                    int id = view.getId();
                    int col = getCol(id);
                    GoStep(col);
                }
            });
        }

        button_restart = (Button) findViewById(R.id.restart);
        button_retract = (Button) findViewById(R.id.retarct);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);
        text = (TextView)findViewById(R.id.tip);

        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGameInit();
            }
        });

        button_retract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    void newGameInit() {
        text.setText("");
        result = 1;
        // 默认红色先手
        turn = 1;
        red.setImageResource(R.drawable.red_wint);
        // 清空棋盘
        chessState = null;
        // 重新初始化
        chessState = new int[6][7];
        for(int i=0 ; i<42 ; i++) {
            ImageButton btn = (ImageButton) findViewById(idList[i]);
            btn.setImageResource(R.drawable.empty_t);
        }
        // 清空栈
        STATCK = null;
        STATCK = new ArrayList();
    }

    int getCol(int id) {
        for (int i=0 ; i<42 ; i++) {
            if (idList[i] == id)
                return i%7;
        }
        System.out.print("getCol() error!");
        return 10000;
    }

    void GoStep(int col) {

        for (int i=5 ; i>=0 ; i--) {
            if(chessState[i][col] == 0) {

                // ui
                ImageButton btn = (ImageButton)findViewById(idList[i*7+col]);
                if (turn == 1){
                    btn.setImageResource(R.drawable.red_t);
                    green.setImageResource(R.drawable.green_wint);
                    red.setImageResource(R.drawable.red_t);
                    chessState[i][col] = turn;
                    turn = 2;
                }else {
                    btn.setImageResource(R.drawable.green_t);
                    red.setImageResource(R.drawable.red_wint);
                    green.setImageResource(R.drawable.green_t);
                    chessState[i][col] = turn;
                    turn = 1;
                }

                //
                STATCK.add(new Point(i,col));
                if (STATCK.size() == 42){
                    // draw
                    text.setText("Draw!");
                    turn = 0;
                    result = 0;
                    return;
                }

                // 判断是否有人赢
                if (judge(i,col)){
                    if (turn == 1) {
                        text.setText("red!");
                        result = 0;
                    }
                    else {
                        text.setText("green!");
                        result = 0;
                    }
                }

                return;
            }
        }

    }

    // 判断是否赢
    boolean judge(int y, int x) {
        int count = 0;
        // horizontal
        for (int i =0;i<7;i++){
            if (chessState[y][i] == turn) {
                ++count;
                if (count == 4)
                    return true;
            }
            else
                count = 0;
        }
        // vertical
        count = 0;
        for (int i =0;i<6;i++){
            if (chessState[i][y] == turn) {
                ++count;
                if (count == 4)
                    return true;
            }
            else
                count = 0;
        }



        return false;
    }

    // 退棋
    void goback(int y, int x) {
        if (STATCK==null || STATCK.size() == 0)
            return;
    }

    // 特殊显示获胜的棋子
    void showRes(int y, int x) {

    }

}
