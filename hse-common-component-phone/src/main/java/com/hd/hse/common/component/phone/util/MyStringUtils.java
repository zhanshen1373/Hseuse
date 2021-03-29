/**
 * Project Name:EditTextTest
 * File Name:MyUtils.java
 * Package Name:com.example.edittexttest
 * Date:2014年9月28日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:MyUtils ().<br/>
 * Date:     2014年9月28日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class MyStringUtils {
	
	/**
	 * 在  s 中找到所有 des 将其位置存储到 note 中。
	 * 
	 * findAll:(). <br/>
	 * date: 2014年10月9日 <br/>
	 *
	 * @author xuxinwen
	 * @param s 源字符串
	 * @param des  目标
	 * @param note  存储位置用的集合。
	 */
	public static void findAll(String s, String des, List<Integer> note){
		// 清空 位置集合。
		note.clear();
		
		// 记录本次查找开始位置，随着查找的进行而向前推进。
		int startOff = 0;
		
		// 该次查找的结果。
		int index =-1;
		
		while((index = s.indexOf(des, startOff))!= -1){
			// index 位置不等于 -1 说明找到了字符。
			
			//更新下次查找的开始位置。
			startOff = index+1;
			// 把刚发现的 位置 记录
			note.add(index);
			// index 重置
			index = -1;
		}
	}
}

