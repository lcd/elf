package com.elf.utils;

import java.util.Random;

import com.elf.enumlations.CharType;

public class PasswordGenerator {

    public static String generate() {
        return generate(12);
    }
    
    /**
     * 生成方法
     * @param length 生成的密码长度
     * @param charTypes 生成的密码中包含的字符类型
     * @return
     */
    public static String generate(int length, CharType... charTypes){
        StringBuffer password = new StringBuffer();
        if(charTypes.length == 0){
            //未指定类型，则默认是所有类型都有
            charTypes = CharType.values();
        }
        for(int i=0; i<length; i++){
            Random random = new Random();
            //在指定类型中随机选一种
            CharType currentCharMode = charTypes[(random.nextInt(100)+charTypes.length)%charTypes.length];
            //生成字符
            char currentChar = generateChar(currentCharMode);
            password.append(currentChar);
        }
        return password.toString();
    }


    /**
     * 生成字符
     * @param currentCharType  该字符的类型，数字？大写字母？ 小写字母？特殊符号？
     * @return
     */
    private static char generateChar(CharType currentCharType) {
        char c = 0;
        Random random = new Random();
        switch(currentCharType){
        case LETTER_ROWCASE : {
            c = (char) (random.nextInt(26) + 97);//97是a的ascii码
            break;
        }
        case LETTER_UPCASE : {
            c = (char) (random.nextInt(26) + 65);//65是A的ascii码
            break;
        }
        case NUMBER : {
            c = (char) (random.nextInt(10) + 48);//48是0的ascii码
            break;
        }
        case SYMBOL : {
            c = (char) (random.nextInt(15) + 33);//33是!的ascii码，后面连续15个符号
        }
        }
        return c;
    }
    
    public static void main(String[] a){
        System.out.println(PasswordGenerator.generate(10,CharType.LETTER_ROWCASE,CharType.NUMBER));
    }

}
