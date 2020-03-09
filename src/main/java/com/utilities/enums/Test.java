package com.utilities.enums;

public class Test {
	private OSEnum osEnum;

	public static void main(String[] args) {
		System.out.println(OSEnum.contains("wind"));
		Test test = new Test();
		test.setEnum(OSEnum.valueOf("J"));
		System.out.println(test.getEnum());
	}

	public void setEnum(OSEnum osEnum) {
		this.osEnum = osEnum;
	}

	public OSEnum getEnum() {
		return this.osEnum;
	}
}
