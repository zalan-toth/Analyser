import net.pyel.models.ImageProcess;
import net.pyel.models.Labeler;
import net.pyel.models.PillType;
import net.pyel.models.SingularPill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@Testable
class MainTests {

	public int[] data;
	public int[] data2;
	public int[] data3;
	Labeler labeler;
	PillType pillType;
	SingularPill singularPill;

	@BeforeEach
	void setUp() {
		data = new int[]{-1, 1, 1, 2, -1, 1, -1, -1, -1, -1, -1, 11, 11, 12, 13, 14, 15, 16, -1, 17, 19, -1, -1, -1};
		data2 = new int[]{0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
		data3 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

		labeler = new Labeler(24);
		pillType = new PillType("Teszt", 256, 0, data);
		singularPill = new SingularPill(data);
	}

	@Test
	void checkFind() {
		System.out.println("Checking find");
		assertEquals(-1, Labeler.find(data, 0));
		assertEquals(1, Labeler.find(data, 1));
		assertEquals(1, Labeler.find(data, 2));
		assertEquals(1, Labeler.find(data, 3));
		assertEquals(-1, Labeler.find(data, 4));
		assertEquals(11, Labeler.find(data, 11));
		assertEquals(11, Labeler.find(data, 17));
		assertEquals(11, Labeler.find(data, 19));
	}

	@Test
	void checkUnion() {
		System.out.println("Checking union");
		ImageProcess.union(data, 3, 17);

		assertEquals(-1, Labeler.find(data, 0));
		assertEquals(1, Labeler.find(data, 1));
		assertEquals(1, Labeler.find(data, 2));
		assertEquals(1, Labeler.find(data, 3));
		assertEquals(-1, Labeler.find(data, 4));
		assertEquals(1, Labeler.find(data, 11));
		assertEquals(1, Labeler.find(data, 17));
		assertEquals(1, Labeler.find(data, 19));
	}

	@Test
	void calculateJointPixelsForArray() {

		System.out.println("Checking pixel amount in array");
		assertEquals(13, (int) labeler.calculatePixelAmountForArray(data)); //NORMAL
		assertEquals(24, (int) labeler.calculatePixelAmountForArray(data2)); //FULL
		assertEquals(0, (int) labeler.calculatePixelAmountForArray(data3)); //EMPTY
	}

	@Test
	void addingPillType() {

		System.out.println("Checking adding pilltype");
		singularPill.setSetToStoreRelationA(data);
		singularPill.setHeight(4);
		singularPill.setWidth(6);
		singularPill.setSizeOfSet(24);
		labeler.setMaxPixels(24);
		labeler.setMinPixels(0);
		labeler.addPillType(pillType, singularPill);
		assertEquals(pillType, labeler.getPillType(labeler.getID(3)));
		assertEquals(pillType, labeler.getPillType(labeler.getID(2)));
		assertEquals(pillType, labeler.getPillType(labeler.getID(11)));
		assertEquals(pillType, labeler.getPillType(labeler.getID(12)));
		assertNull(labeler.getPillType(labeler.getID(0)));
		assertNull(labeler.getPillType(labeler.getID(4)));
		assertEquals(2, labeler.getPillType(labeler.getID(3)).getPills().size()); //all 2 pills should be added
	}

	@Test
	void checkMinPixelsOperations() {

		System.out.println("Checking min pixel limit");
		singularPill.setSetToStoreRelationA(data);
		singularPill.setHeight(4);
		singularPill.setWidth(6);
		singularPill.setSizeOfSet(24);
		labeler.setMaxPixels(24);
		labeler.setMinPixels(8);
		labeler.addPillType(pillType, singularPill);
		assertEquals(1, labeler.getPillType(labeler.getID(3)).getPills().size()); //only 1 pill should be added
	}

	@Test
	void checkMaxPixelsOperations() {

		System.out.println("Checking max pixel limit");
		singularPill.setSetToStoreRelationA(data);
		singularPill.setHeight(4);
		singularPill.setWidth(6);
		singularPill.setSizeOfSet(24);
		labeler.setMaxPixels(5);
		labeler.setMinPixels(1);
		labeler.addPillType(pillType, singularPill);
		assertEquals(1, labeler.getPillType(labeler.getID(3)).getPills().size()); //only 1 pill should be added
	}
}