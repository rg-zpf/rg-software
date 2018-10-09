package com.itrg.code;

import java.util.Random;
import java.util.Scanner;

public class NumCode {
	
	public static void main(String[] args) {
		String[] su = new String[4];
		su[0]="+";
		su[1]="-";
		su[2]="*";
		su[3]="/";
		
		String str="";
		
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入题目数:");
		int n = sc.nextInt();
		int count=0;
		
		
		float[] cu = new float[3];//存储3个数
		String[] ku = new String[3];//存储两个符号
		
		
		for(int j = 0; j < n; j++){
			for (int i = 0; i < 3; i++) {
				Random rd = new Random();
				int num = rd.nextInt(4);
				cu[i] = rd.nextInt(100);
				ku[i] = su[num];
				
			
				if(i!=2){
					str = str + cu[i] + ku[i];
				}else{
					str = str + cu[i] + "=";
				}
				
			}
			float sum=0;
			//优先级判断
			//情况1
			if(ku[0]=="*" || ku[0]=="/"){
				if(ku[0]=="*"){
					sum = mul(cu[0],cu[1]);
				}else{
					sum = dis(cu[0],cu[1]);
				}
				
				if(ku[1]=="+"){
					sum = add(sum,cu[2]);
				}else if(ku[1]=="-"){
					sum = sub(sum,cu[2]);
				}else if(ku[1]=="*"){
					sum = mul(sum,cu[2]);
				}else{
					sum = dis(sum,cu[2]);
				}
			}else{//情况2
				if(ku[1]=="*" || ku[1]=="/"){
					if(ku[1]=="*"){
						sum = mul(cu[1],cu[2]);
						if(ku[0]=="+"){
							sum = add(cu[0],sum);
						}else{
							sum = sub(cu[0],sum);
						}
					}
					else{
						sum = dis(cu[1],cu[2]);
						if(ku[0]=="+"){
							sum = add(cu[0],sum);
						}else{
							sum = sub(cu[0],sum);
						}
					}
				}
				
				else if(ku[0]=="+"){
					sum = add(cu[0],cu[1]);
					if(ku[1]=="+"){
						sum = add(sum,cu[2]);
					}else{
						sum = sub(sum,cu[2]);
					}
				}else{
					sum = sub(cu[0],cu[1]);
					if(ku[1]=="+"){
						sum = add(sum,cu[2]);
					}else{
						sum = sub(sum,cu[2]);
					}
				}
			}
			//结果不能为负数
			if(sum<0){
				j=j-1;
				str="";
				continue;
			}
			System.out.print(str);
			Scanner input = new Scanner(System.in);
			float result = input.nextFloat();
			if(Math.abs(result-sum)<0.1){
				System.out.println("恭喜你，计算正确，答案是:"+sum);
				count=count+1;
			}else{
				System.out.println("很遗憾，计算错误，答案是:"+sum);
			}
			str="";
		}
		System.out.print("你的最后分数是:"+(float)count/n*100);
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
