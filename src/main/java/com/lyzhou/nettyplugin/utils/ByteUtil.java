package com.lyzhou.nettyplugin.utils;

public class ByteUtil 
{

	public static String bytes2HexStr(byte[] bytes)
	{
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%02X ", b));
	    }
	    return sb.toString();
	}

}
