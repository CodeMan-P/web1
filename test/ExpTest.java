import java.awt.Point;
import java.util.LinkedList;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.PoiForExcel;
import com.util.Points;

/**
 * 
 * @author lenovo
 *
 */
public class ExpTest {
	
	@Test
	public void testm2(){
		LinkedList<Points> list = PoiForExcel.getXcol(0, 1, 0);
		ObjectMapper m = new ObjectMapper();
		try {
			String js = m.writeValueAsString(list);
			System.out.println(js);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testmethod(){
		 
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
