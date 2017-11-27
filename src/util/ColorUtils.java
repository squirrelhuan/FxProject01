package util;

import java.awt.Color;

/**
 * 
 * @author CGQ
 *
 */
public class ColorUtils {

	
	public static Color getColorForAWT(String str){
		str.replace("#", "");
		String hongstr = str.substring(1, 3);
		int hong = Integer.parseInt(hongstr,16);
		String lvstr = str.substring(3, 5);
		int lv = Integer.parseInt(lvstr,16);
		String lanstr = str.substring(5, 7);
		int lan = Integer.parseInt(lanstr,16);
		Color color = new Color(hong,lv,lan);
		return color;
		
	}
	
	public static void main(String args[]){
		getColorForAWT("#4DBCFF");
	}
}
