import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dev.lbell.models.Comps;
import dev.lbell.services.CompsService;


public class Project1Tests {
	CompsService cs = new CompsService();
	
	
	@Test
	public void testStatusChange() {
		Comps comp1 = cs.findCompById(3);
		System.out.println(comp1.getStatus());
		cs.addressComp(comp1, "APPROVED");
		Comps comp2 = cs.findCompById(3);
		assertTrue("these are the same", comp1.getStatus().equals(comp2.getStatus()));
		
	}
	
}
