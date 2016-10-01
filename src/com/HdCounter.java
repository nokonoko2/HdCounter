package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HdCounter {
	public static long totalOne = 0;
	public static long totalZero = 0;
	public static List<Integer> oneList = new ArrayList<Integer>(256);

	static {
		for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
			int oneNum = bitCount(i);
			oneList.add(oneNum);
		}
	}

	public static void main(String[] args) throws IOException {
		File portDir = new File("/Volumes/DATAPOT/JavaScript");
		File[] portFileArr = portDir.listFiles();
		for (File portFile : portFileArr) {
			listFile(portFile);
		}
	}

	public static void listFile(File portFile) {
		if (portFile == null) {
			return;
		} else if (portFile.isDirectory() && portFile.listFiles() != null) {
			for (File temp : portFile.listFiles()) {
				listFile(temp);
			}
		} else {
			FileInputStream fis = null;
			int zero = 0;
			int one = 0;
			byte[] tempArr = null;
			try {
				int length = (int) portFile.length();
				if (length > 0) {
					tempArr = new byte[length];
					fis = new FileInputStream(portFile);
					fis.read(tempArr);
					for (byte temp : tempArr) {
						int oneCount = oneList.get(temp + 128);
						one += oneCount;
						zero += (8 - oneCount);
					}
				}
			} catch (Exception e) {

			} finally {
				try {
					if (fis != null)
						fis.close();
					fis = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (tempArr != null)
					tempArr = null;
			}
			totalZero += zero;
			totalOne += one;
			System.out.println("totalOne : " + totalOne + " totalZero : " + totalZero + " Current File : "
					+ portFile.getAbsolutePath());
		}
	}

	public static int bitCount(int byteNum) {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			if ((byteNum & 1) % 2 != 0) {
				count++;
			}
			byteNum = byteNum >> 1;
		}
		return count;
	}
}