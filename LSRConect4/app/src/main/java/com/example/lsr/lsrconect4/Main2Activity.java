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

public class Main2Activity extends AppCompatActivity {

    // 当前下棋的玩家
    private int turn = 1;
    // 游戏状态
    private int result = 0;
    // 棋盘数据model
    private int chessState[][] = null;
    // 棋子堆栈
    private ArrayList<Point> STATCK = null;
    // 记录四个方向是否有4个以上相同棋子
    boolean[] winDir = null;

    // 获取UI引用
    private Button button_restart;
    private Button button_retract;
    private ImageView red;
    private ImageView green;
    private TextView text;

    // 棋子id
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

        // activity初始化
        init();
        // 新游戏初始化
        newGameInit();
    }

    /**
     * activity初始化
     */
    void init() {

        // 为棋子按钮注册点击事件
        for (int i=1 ; i<=42 ; i++) {
            ImageButton btn = (ImageButton)findViewById(idList[i-1]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 如果游戏结束不响应
                    if (result == 0)
                        return;
                    // 计算点击所在列数
                    int id = view.getId();
                    int col = getCol(id);
                    // 下一步棋
                    GoStep(col);
                }
            });
        }

        // UI初始化
        button_restart = (Button) findViewById(R.id.restart);
        button_retract = (Button) findViewById(R.id.retarct);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);
        text = (TextView)findViewById(R.id.tip);

        // 注册重新开始游戏事件
        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGameInit();
            }
        });

        // 注册退棋事件
        button_retract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result == 0)
                    return;
                goback();
            }
        });

    }

    /**
     * 重新开始游戏初始化
     */
    void newGameInit() {

        /** 清空UI **/
        text.setText("");
        red.setImageResource(R.drawable.red_wint);
        green.setImageResource(R.drawable.green_t);
        for(int i=0 ; i<42 ; i++) {
            ImageButton btn = (ImageButton) findViewById(idList[i]);
            btn.setImageResource(R.drawable.empty_t);
        }

        /** 清空数据  **/
        result = 1;
        turn = 1;
        // 清空棋盘
        chessState = null;
        chessState = new int[6][7];
        // 清空栈
        STATCK = null;
        STATCK = new ArrayList<Point>();
    }

    /**
     * 根据按钮id计算列数
     */
    int getCol(int id) {
        for (int i=0 ; i<42 ; i++) {
            if (idList[i] == id)
                return i%7;
        }
        System.out.print("getCol() error!");
        return 10000;
    }

    /**
     * 下棋
     */
    void GoStep(int col) {

        // 从下往上寻找空闲位置，从而确定下棋具体位置
        for (int i=5 ; i>=0 ; i--) {
            if(chessState[i][col] == 0) {
                // 添加到棋盘数据模型
                chessState[i][col] = turn;
                // 入栈
                STATCK.add(new Point(i,col));

                // 平局
                if (STATCK.size() == 42){
                    text.setText("Draw!");
                    turn = 0;
                    result = 0;
                    return;
                }

                // 判断有人赢
                if (judge(i,col)){
                    if (turn == 1) {
                        text.setText("red!");
                        result = 0;
                    }
                    else {
                        text.setText("green!");
                        result = 0;
                    }
                    // 显示获胜棋子
                    showRes(i,col);
                    return;
                }

                // 无人赢切换玩家
                ImageButton btn = (ImageButton)findViewById(idList[i*7+col]);
                if (turn == 1){
                    btn.setImageResource(R.drawable.red_t);
                    green.setImageResource(R.drawable.green_wint);
                    red.setImageResource(R.drawable.red_t);
                    turn = 2;
                }else {
                    btn.setImageResource(R.drawable.green_t);
                    red.setImageResource(R.drawable.red_wint);
                    green.setImageResource(R.drawable.green_t);
                    turn = 1;
                }

                return;
            }
        }
    }

    /**
     * 判断是否赢
     */
    boolean judge(int y, int x) {
        int count = 0;
        boolean res = false;
        winDir = null;
        winDir = new boolean[4];
        // horizontal
        for (int i =0;i<7;i++){
            if (chessState[y][i] == turn) {
                ++count;
                if (count >= 4) {
                    res = true;
                    winDir[0] = true;
                }
            }
            else
                count = 0;
        }

        // vertical
        count = 0;
        for (int i =0;i<6;i++){
            if (chessState[i][x] == turn) {
                ++count;
                if (count >= 4) {
                    res = true;
                    winDir[1] = true;
                }
            }
            else
                count = 0;
        }

        // mdiagnal
        count = 0;
        int min = x>y ? y : x;
        int ox = x - min;
        int oy = y - min;
        for (int i=0; ; i++) {
            if(ox+i>6 || oy+i>5)
                break;
            if (chessState[oy+i][ox+i] == turn) {
                ++count;
                if (count >= 4) {
                    res = true;
                    winDir[2] = true;
                }
            }
            else
                count = 0;
        }

        // adiagnal
        count = 0;
        min = x>(5-y) ? 5-y : x;
        ox = x - min;
        oy = y + min;
        for (int i=0;  ;i++) {
            if(ox+i>6 || oy-i<0)
                break;
            if (chessState[oy-i][ox+i] == turn) {
                ++count;
                if (count >= 4) {
                    res = true;
                    winDir[3] = true;
                }
            }
            else
                count = 0;
        }

        return res;
    }

    /**
     * 退棋
     */
    void goback() {
        if (STATCK==null || STATCK.size() == 0)
            return;
        Point p = STATCK.remove(STATCK.size()-1);
        chessState[p.x][p.y] = 0;
        // 切换玩家
        ImageButton btn = (ImageButton)findViewById(idList[p.x*7+p.y]);
        if (turn == 1){
            btn.setImageResource(R.drawable.empty_t);
            green.setImageResource(R.drawable.green_wint);
            red.setImageResource(R.drawable.red_t);
            turn = 2;
        }else {
            btn.setImageResource(R.drawable.empty_t);
            red.setImageResource(R.drawable.red_wint);
            green.setImageResource(R.drawable.green_t);
            turn = 1;
        }

    }

    /**
     * 特殊显示获胜的棋子
     */
    void showRes(int y, int x) {
        // 遍历搜索，以当前坐标为中心连续的全部显示为特殊棋子

        // 当前棋子
        showPos(y*7+x);

        int step = 1;
        if(winDir[0]) {
            // 右
            while (x+step<=6 && chessState[y][x+step] == turn) {showPos(y*7+(x+step));step++;}
            // 左
            step = 1;
            while (x-step>=0 && chessState[y][x-step] == turn) {showPos(y*7+(x-step));step++;}
        }

        if (winDir[1]) {
            // 上
            step = 1;
            while (y-step>=0 && chessState[y-step][x] == turn) {showPos((y-step)*7+x);step++;}
            // 下
            step = 1;
            while (y+step<=5 && chessState[y+step][x] == turn) {showPos((y+step)*7+x);step++;}
        }

        if (winDir[2]) {
            // 左上
            step = 1;
            while (x-step>=0 && y-step>=0 && chessState[y-step][x-step] == turn) {showPos((y-step)*7+(x-step));step++;}
            // 右下
            step = 1;
            while (x+step<=6 && y+step<=5 && chessState[y+step][x+step] == turn) {showPos((y+step)*7+(x+step));step++;}
        }

        if (winDir[3]) {
            // 左下
            step = 1;
            while (x-step>=0 && y+step<=5 && chessState[y+step][x-step] == turn) {showPos((y+step)*7+(x-step));step++;}
            // 右上
            step = 1;
            while (x+step<=6 && y-step>=0 && chessState[y-step][x+step] == turn) {showPos((y-step)*7+(x+step));step++;}
        }

    }

    void showPos(int id) {

        ImageButton btn = (ImageButton) findViewById(idList[id]);
        // 更换图片
        if (turn == 1)
            btn.setImageResource(R.drawable.red_wint);
        else
            btn.setImageResource(R.drawable.green_wint);
    }

}
