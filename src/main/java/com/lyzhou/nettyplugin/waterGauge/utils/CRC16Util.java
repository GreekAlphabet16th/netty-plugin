package com.lyzhou.nettyplugin.waterGauge.utils;


/**
 * CRC-16校验码
 * @author zhouliyu
 * */
public class CRC16Util {


    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }

    public static void main(String[] args) {
        byte[] reqMessage = {0x01,0x03,0x00,0x00,0x00,0x01};
        System.out.println(CRC16Util.getCRC(reqMessage));
    }


}
