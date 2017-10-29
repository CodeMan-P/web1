package com.testPack;

import java.awt.Point;
import java.util.LinkedList;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestExp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Test
	public void testmethon(){
		ObjectMapper mapper = new ObjectMapper();
		LinkedList<Point> list = new LinkedList<Point>();
		Point p = new Point(10, 20);
		Point p2 = new Point(11, 21);
		list.add(p);
		list.add(p2);
		
		try {
			String json = mapper.writeValueAsString(list);
			System.out.println(json);
			@SuppressWarnings("unchecked")
			LinkedList<Point> list2 = mapper.readValue(json, LinkedList.class);
			json = mapper.writeValueAsString(list2);
			System.out.println(json);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
