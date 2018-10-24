import com.sun.javafx.css.Size;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static com.sun.javafx.font.LogicalFont.STYLE_BOLD;

public class NumCode extends Application {
    int num;//题目数量
    float answer;//回答结果
    int low;//阶层数
    int count=0;//正确标记
    int code=0;//错误标记
    long time = System.currentTimeMillis();//第一个计时器：计算答题总用时间
    Timer timer;//第二个计时器：计算每一题的规定时间


    float[] cu = new float[3];//存储3个数
    String[] ku = new String[2];//存储两个符号

    String string;
    String StrTime;
    int times=300;
    String StrOut;
    Timer tis;

    int n=-1;//当前标记

    boolean flasn;//不同式子和阶层判断

    long endTime;

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("四则运算");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        //自动调整，适应多余空间
        //ColumnConstraints column1 = new ColumnConstraints(500);
        //ColumnConstraints column2 = new ColumnConstraints(50, 100, 500);
        //column1.setHgrow(Priority.ALWAYS);//水平影响优先
        //gridPane.getColumnConstraints().add(column1);
        //gridPane.getColumnConstraints().addAll(column1, column2);

        String Color1 = "-fx-background-color: deepskyblue";
        String Color2 = "-fx-background-color:palegreen";


        TextField outTime = new TextField();//创建倒计时文本框
        outTime.setMaxHeight(100);
        outTime.setMaxWidth(150);
        outTime.setStyle(Color2);
        outTime.setFont(Font.font("宋体", FontWeight.BOLD,12));
        GridPane.setHalignment(outTime, HPos.LEFT);
        gridPane.add(outTime, 0, 0);

        //题目数
        Button Number = new Button("请输入题目数");
        Number.setFont(new Font(12));
        Number.setMaxHeight(50);
        Number.setMaxWidth(100);
        Number.setStyle(Color1);
        GridPane.setHalignment(Number, HPos.RIGHT);
        gridPane.add(Number, 1, 0);

        Button clear = new Button("清除");
        clear.setFont(new Font(12));
        clear.setMaxHeight(50);
        clear.setMaxWidth(100);
        clear.setStyle(Color1);
        GridPane.setHalignment(clear, HPos.RIGHT);
        gridPane.add(clear, 2, 0);

        TextField NumText = new TextField();//创建输入题目数文本框
        NumText.setMaxHeight(100);
        NumText.setMaxWidth(100);
        NumText.setStyle(Color2);
        GridPane.setHalignment(NumText, HPos.RIGHT);
        gridPane.add(NumText, 0, 0);


        //输入答案
        Button Answer = new Button("确定");
        Answer.setFont(new Font(12));
        Answer.setMaxHeight(50);
        Answer.setMaxWidth(100);
        Answer.setStyle(Color1);
        //设置确定按钮
        GridPane.setHalignment(Answer, HPos.RIGHT);
        gridPane.add(Answer, 1, 2);


        TextField AnswerText = new TextField();//创建输入答案文本框
        AnswerText.setMaxHeight(100);
        AnswerText.setMaxWidth(100);
        AnswerText.setStyle(Color2);
        //设置答案文本框
        GridPane.setHalignment(AnswerText, HPos.RIGHT);
        gridPane.add(AnswerText, 0, 2);


        TextArea Content = new TextArea();//创建输出内容文本区
        Content.setWrapText(true);//自动换行;
        Content.setStyle(Color1);
        GridPane.setHalignment(Content, HPos.LEFT);
        gridPane.add(Content, 0, 1);

        Button newCode = new Button("生成题目");
        newCode.setFont(new Font(12));
        newCode.setMaxHeight(20);
        newCode.setMaxWidth(100);
        newCode.setStyle(Color1);
        //设置确定按钮
        GridPane.setHalignment(newCode, HPos.RIGHT);
        gridPane.add(newCode, 1, 1);


        string = "请输入题目数目：";
        Content.setText(string);

        tis = new Timer();
        tis.schedule(new TimerTask() {
            public void run() {

                if (times==0){
                    code = num - count;
                    string = string + "\n" + "正确:" +count +"\t"+"错误:" + code +"\n"+ "你的最后分数是:"+(float)count/num*100;
                    Content.setText(string);
                    tis.cancel();
                    timer.cancel();
                    n=-1;
                }else {

                    times--;

                    long hh = times / 60 / 60 % 60;
                    long mm = times / 60 % 60;
                    long ss = times % 60;
                    StrOut = "还剩" + hh + "小时" + mm + "分钟" + ss + "秒";
                    outTime.setText(StrOut);
                }
            }
        }, 0, 1000);


        //产生题目数点击事件
        Number.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                num = Integer.parseInt(NumText.getText());
                if (num>5){
                    string = string + "\n" + "题目数不能超过5题，请重新输入!"+ "\n" + "请输入题目数目：";
                    Content.setText(string );
                }else {
                    string = string + NumText.getText();
                    Content.setText(string);
                }


                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        StrTime = String.format("%1$tM:%1$tS",System.currentTimeMillis()-time);
                    }
                };
                new Timer().schedule(task1, 1, 1000);
            }
        });


        //显示算式与最后得分
        newCode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                n++;
                if(n<num){
                    string = string+ "\n"+Code();
                    Content.setText(string);

                    //每一道题目的规定答题时间
                    timer = new Timer();//第二个计时器：计算每一题的规定时间
                    long nowTime = System.currentTimeMillis();
                    TimerTask task2 = new TimerTask() {
                        @Override
                        public void run() {
                            endTime = (System.currentTimeMillis()-nowTime)/1000;
                            //System.out.println(endTime);
                            if (endTime>120){
                                string = string + "\n" + "此题超时，答案错误!";
                                Content.setText(string);
                                code++;
                                timer.cancel();
                            }
                        }
                    };
                    timer.schedule(task2,1,1000);
                //所有题目答题完毕
                }else {
                    tis.cancel();//总时间
                    new Timer().cancel();//答题所用时间(所有题目)
                    string = string + "\n" + "正确:" +count +"\t"+"错误:" + code + "\n"+ "你的最后分数是:"+(float)count/num*100;
                    Content.setText(string + "\n" + "你的用时:" + StrTime);
                    n=-1;

                }
            }
        });



        //提交答案并判断对错
        Answer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timer.cancel();//每一道题的结束
                answer = Integer.parseInt(AnswerText.getText());
                if(Math.abs(answer-result())<0.1){
                    string = string + AnswerText.getText() + "\n" +"恭喜你，计算正确，答案是:"+result();
                    Content.setText(string);
                    count=count+1;
                    }else{
                        string = string + AnswerText.getText() + "\n" +"很遗憾，计算错误，答案是:"+result();
                        Content.setText(string);
                        code++;
                    }
            }
        });

        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                NumText.setText(null);
                AnswerText.setText(null);
                Content.setText(null);
                string = "请输入题目数目：";
                Content.setText(string);
                time=System.currentTimeMillis();
                count=0;
                code=0;
                //复位总时间
                times=300;
                tis = new Timer();
                tis.schedule(new TimerTask() {
                    public void run() {

                        if (times == 0) {
                            code = num - count;
                            string = string + "\n" + "正确:" + count + "\t" + "错误:" + code + "\n" + "你的最后分数是:" + (float) count / num * 100;
                            Content.setText(string);
                            tis.cancel();
                            timer.cancel();
                            n = -1;
                        } else {

                            times--;

                            long hh = times / 60 / 60 % 60;
                            long mm = times / 60 % 60;
                            long ss = times % 60;
                            StrOut = "还剩" + hh + "小时" + mm + "分钟" + ss + "秒";
                            outTime.setText(StrOut);
                        }
                    }
                }, 0, 1000);
            }
        });

        borderPane.setTop(gridPane);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String Code(){
        String[] su = new String[4];
        su[0]="+";
        su[1]="-";
        su[2]="*";
        su[3]="/";

        String str="";

        int data = new Random().nextInt(5);
        switch (data) {
            case 0:
            case 1:
            case 2:
            case 3:
                for (int i = 0; i < 3; i++) {
                    Random rd = new Random();
                    int num = rd.nextInt(4);
                    cu[i] = rd.nextInt(100);
                    if (i<2) {
                        ku[i] = su[num];
                    }


                    if (i != 2) {
                        str = str + cu[i] + ku[i];
                    } else {
                        str = str + cu[i] + "=";
                    }
                }
                    flasn=true;
                    break;
            case 4:
                low = new Random().nextInt(11) + 1;
                str = low + "!=";

                flasn = false;
                break;
            }
        //结果不能为负数
         while (result()<0){
             str = Code();
         }
        return str;
    }

    public float result(){
        float sum=0;
        //优先级判断
        //情况1
        if (flasn) {
            if (ku[0] == "*" || ku[0] == "/") {
                if (ku[0] == "*") {
                    sum = mul(cu[0], cu[1]);
                } else {
                    sum = dis(cu[0], cu[1]);
                }

                if (ku[1] == "+") {
                    sum = add(sum, cu[2]);
                } else if (ku[1] == "-") {
                    sum = sub(sum, cu[2]);
                } else if (ku[1] == "*") {
                    sum = mul(sum, cu[2]);
                } else {
                    sum = dis(sum, cu[2]);
                }
            } else if (ku[1] == "*" || ku[1] == "/"){//情况2
                if (ku[1] == "*") {
                    sum = mul(cu[1], cu[2]);
                    if (ku[0] == "+") {
                        sum = add(cu[0], sum);
                    }else {
                        sum = sub(cu[0], sum);
                    }
                } else {
                    sum = dis(cu[1], cu[2]);
                    if (ku[0] == "+") {
                        sum = add(cu[0], sum);
                    } else {
                        sum = sub(cu[0], sum);
                    }
                }
            } else  {
                if (ku[0] == "+"){
                    sum = add(cu[0], cu[1]);
                    if (ku[1] == "+") {
                        sum = add(sum, cu[2]);
                    } else {
                        sum = sub(sum, cu[2]);
                    }
                } else {
                    sum = sub(cu[0], cu[1]);
                    if (ku[1] == "+") {
                        sum = add(sum, cu[2]);
                    } else {
                        sum = sub(sum, cu[2]);
                    }
                }
            }
        }else {
            sum=1;
            for(int i=1;i<=low;i++){
                sum=sum*i;
            }
        }
        return sum;
    }

    public static float add(float x,float y){
        return x+y;
    }

    public static float sub(float x,float y){
        return x-y;
    }

    public static float mul(float x,float y){
        return x*y;
    }

    public static float dis(float x,float y){
        return x/y;
    }

}
