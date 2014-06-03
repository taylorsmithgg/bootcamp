package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.taylorsmith.main.BLogic;

public class BLogicTest {

	@Test
	public void raiseSalary(){
		BLogic blogic = new BLogic();
		assertEquals(blogic.raiseSalary(1000, .10),100);
	}

	public void givePromotion(){
	}
}
