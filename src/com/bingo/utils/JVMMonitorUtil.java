package com.bingo.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// http://doc.java.sun.com/DocWeb/api/all/java.lang.management.MemoryPoolMXBean
public class JVMMonitorUtil {

	public static void monitorMemoryUsage() {
		
		List<MemoryPoolMXBean> mxBeanList = ManagementFactory
				.getMemoryPoolMXBeans();

		System.out.println( "Memory Usage Test" );
		System.out.println( "MemoryPoolMXBean Size:" + mxBeanList.size() );
		
		for (MemoryPoolMXBean instance : mxBeanList) {
			if( instance.isValid() )
			{
				System.out.println("\tMemory Usage Committed: "
						+ instance.getUsage().getCommitted());
				System.out.println("\tMemory Usage Init: "
						+ instance.getUsage().getInit());
				System.out.println("\tMemory Usage Max: "
						+ instance.getUsage().getMax());
				System.out.println("\tMemory Usage Used: "
						+ instance.getUsage().getUsed() + "\n");
			}
		}
	}
	
	// about System.gc http://www.iteye.com/topic/802638
	// http://www.iteye.com/topic/802573
	// java heap size default is 20M, this will cause java heap exception
	public static void virtualHeapError()
	{
		List<String> list = new ArrayList<String>();
		while( true )
		{
			list.add("this will cause memory segment fault");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
	}

}
