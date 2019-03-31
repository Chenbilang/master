package aa_web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.objectweb.asm.tree.IntInsnNode;

public class TestString {

	@Test
	public void test(){
		String string="[[10099,\"上海\"],[10208,\"重庆\"]";
		String[] split = string.split(",");
		for (int i=0; i<split.length;i++) {
//			for (String string3 : split2) {
//				System.out.println(string3);
//			}
			if(i%2==0){
				
				System.out.println(split[i].substring(2));
			}else {

				System.out.println(split[i].replace("\"]","").substring(1));
			}
			   



		}
	}
}
