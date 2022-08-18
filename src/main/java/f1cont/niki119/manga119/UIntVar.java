package f1cont.niki119.manga119;

import f1cont.niki119.manga119.helpers.LogHelper;

public class UIntVar implements LogHelper {

    public int size;
    public byte[] data;
    public int number;

    public UIntVar(int number){
        this.number = number;
        size = determineSize(number);
        data = new byte[size];
        int n = number;
        for(int i = data.length-1;i>=0;i--){
            if(i== data.length-1)
                data[i] = (byte) (n & 0x7F);
            else if(i== data.length-2)
                data[i] = (byte) (((n >> 7) & 0x7F) | 0x80);
            else if(i== data.length-3)
                data[i] = (byte) (((n >> 14) & 0x7F) | 0x80);
            else if(i== data.length-4)
                data[i] = (byte) (((n >> 21) & 0x7F) | 0x80);
        }
    }

    @Override
    public String toString() {
        String number = "";
        for(int i = 0; i< data.length;i++){
            for(int j = 7; j >= 0;j--){
                int f = data[i] >> j;
                if((f & 0x01) == 0x01) number += "1";
                else number += "0";
            }
            number += " ";
        }
        return number;
    }

    public static int determineSize(int number){
        if(number>2097151) return 4;
        if(number>16383) return 3;
        if(number>127) return 2;
        return 1;
    }
}