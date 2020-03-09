package com.utilities.test;

public class TestString {
	public static String getClassName(String blockName) {
		String res = "";
		if ((blockName != null) && (blockName != "")) {
			blockName = blockName.toLowerCase();
			String[] resAr = blockName.split("_", -1);
			for (int i = 0; i < resAr.length; i++) {
				res = res + resAr[i].replaceFirst(new StringBuilder().append(resAr[i].charAt(0)).append("").toString(),
						new StringBuilder().append(resAr[i].charAt(0)).append("").toString().toUpperCase());
			}
		}
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String master = "BLK_FACILITY";
		master = master.substring(4, master.length());
		System.out.println(master);
		System.out.println(getClassName(master));
	}
}
