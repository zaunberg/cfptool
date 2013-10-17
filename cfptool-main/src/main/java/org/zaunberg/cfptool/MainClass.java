package org.zaunberg.cfptool;

import org.zaunberg.cfptool.CfpTool;


public class MainClass {

	public static void main(String[] args) {
		new CfpTool().loadProperties().run();
	}
}
