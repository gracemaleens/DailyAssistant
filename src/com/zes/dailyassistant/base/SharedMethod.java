package com.zes.dailyassistant.base;

import java.util.EnumSet;

public class SharedMethod {
	public static <T extends Enum<T>> T getEnumByIndex(Class<T> c, int index){
		EnumSet<T> remindTimesSet = EnumSet.allOf(c);
		if(index < 0 || index >= remindTimesSet.size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		
		for(T item : remindTimesSet){
			if(item.ordinal() == index){
				return item;
			}
		}
		return null;
	}
}
