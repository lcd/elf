import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * laichendong.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */

/**
 * 一个人上山，有N个台阶，这个人可以一次跨一个台阶或者两个台阶，求这个人有几种方法上山
 * 
 * @author laichendong
 */
public class PathComputer {

    private static final int STEP_ONE = 1;
    private static final int STEP_TWO = 2;

    /**
     * 
     * @param args
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void main(String[] args) {
        try {
            int totalStep = readTotalStep();
            forward(totalStep, STEP_ONE, STEP_TWO);
        } catch (NumberFormatException e) {
            System.out.print("输入不合法，程序退出。");
        } catch (IOException e) {
            System.out.print("参数读取失败，程序退出。");
        }

    }

    private static void forward(int remainingStep, int... steps) {
        int numOfSteps = steps.length;// 有几种步幅
        for (int i = 0; i < remainingStep; i++) {// i为步数
            if (remainingStep > 0) {
                System.out.print(Math.min(STEP_ONE, STEP_TWO) + "\t");
                System.out.println("");
                forward(remainingStep - Math.min(STEP_ONE, STEP_TWO), STEP_ONE, STEP_TWO);
            }
        }
    }

    /**
     * 读取输入的台阶数
     * 
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    private static int readTotalStep() throws NumberFormatException, IOException {
        System.out.print("请输入台阶数:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(br.readLine());
    }

}
