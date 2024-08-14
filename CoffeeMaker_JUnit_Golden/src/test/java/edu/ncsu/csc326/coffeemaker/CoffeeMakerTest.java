package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import junit.framework.TestCase;



/**
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 *
 * Unit tests for CoffeeMaker class.
 */

public class CoffeeMakerTest extends TestCase {
	
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;
	private CoffeeMaker cm;
	private RecipeBook recipeBookStub;
	private Recipe [] stubRecipies; 
	
	protected void setUp() throws Exception {
		
		cm = new CoffeeMaker();
		
		//Set up for r1
		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("3");
		r1.setAmtMilk("1");
		r1.setAmtSugar("1");
		r1.setPrice("50");
		
		//Set up for r2
		r2 = new Recipe();
		r2.setName("Mocha");
		r2.setAmtChocolate("20");
		r2.setAmtCoffee("3");
		r2.setAmtMilk("1");
		r2.setAmtSugar("1");
		r2.setPrice("75");
		
		//Set up for r3
		r3 = new Recipe();
		r3.setName("Latte");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("3");
		r3.setAmtSugar("1");
		r3.setPrice("100");
		
		//Set up for r4
		r4 = new Recipe();
		r4.setName("Hot Chocolate");
		r4.setAmtChocolate("4");
		r4.setAmtCoffee("0");
		r4.setAmtMilk("1");
		r4.setAmtSugar("1");
		r4.setPrice("65");
		
		//Set up for r5 (added by MWW)
		r5 = new Recipe();
		r5.setName("Super Hot Chocolate");
		r5.setAmtChocolate("6");
		r5.setAmtCoffee("0");
		r5.setAmtMilk("1");
		r5.setAmtSugar("1");
		r5.setPrice("100");

		stubRecipies = new Recipe [] {r1, r2, r3};
		
		super.setUp();
	}
	
	
	// put your tests here!

	@Test
    public void testAddInventory() throws InventoryException {
        cm.addInventory("0","0","0","0");
    }

	@Test(expected = InventoryException.class)
    public void testAddInventory2() throws InventoryException {
		try {
			cm.addInventory("-1","-1","-1","-1");
		} catch (InventoryException e){
		}
    }

	@Test
    public void testEditRecipe() throws RecipeException {
        cm.editRecipe(0, r1);

    }

	@Test
	public void testEditRecipe1() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		Recipe newRecipe = new Recipe();
		newRecipe.setName("Test New Recipe");
		String expectedOldRecipeName = stubRecipies[0].getName();
		String actualOldRecipeName = cm.editRecipe(0, newRecipe);
		stubRecipies[0] = newRecipe;
		Recipe[] expectedRecipeArray = stubRecipies;
		Recipe[] actualRecipeArray = cm.getRecipes();
		assertEquals(expectedOldRecipeName, actualOldRecipeName);
		assertArrayEquals(expectedRecipeArray, actualRecipeArray);
	}

	@Test(expected = InventoryException.class)
    public void testAddInventoryException() throws InventoryException {
		try{
			cm.addInventory("4", "-1", "-6", "3");
		} catch (InventoryException e) {}
    }

	@Test
	public void testAddInventory1() throws InventoryException {
		cm.addInventory("20","20","20","20");
		String newInventory = cm.checkInventory();
		String inventoryAmount = "Coffee: 35\n" +
								"Milk: 35\n" +
								"Sugar: 35\n" +
								"Chocolate: 35\n";
		assertEquals(newInventory, inventoryAmount);
	}

	@Test
	public void testSubtractIngredients() {
		cm.addRecipe(r1);
		cm.makeCoffee(0, 50);
		String inventoryAmount = "Coffee: 12\n" +
		"Milk: 14\n" +
		"Sugar: 14\n" +
		"Chocolate: 15\n";
		String afterSubtract = cm.checkInventory();
		assertEquals(afterSubtract, inventoryAmount);
	}

	@Test
	public void testMakeNoCoffee() {
		assertEquals(75, cm.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffee1() {
		cm.addRecipe(r1);
		assertEquals(25, cm.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffee3() {
		cm.addRecipe(r1);
		assertEquals(12, cm.makeCoffee(0, 12));
	}

	@Test
	public void testMakeCoffee4() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		System.out.println(cm.makeCoffee(1, 12));
		assertEquals(12, cm.makeCoffee(1, 12));
	}

	@Test
    public void testAddRecipe1()
    {
        assertEquals(true,cm.addRecipe(r1));
        assertEquals(true,cm.addRecipe(r2));
        assertEquals(true,cm.addRecipe(r3));
        assertEquals(false,cm.addRecipe(r4));
    }

	@Test
	public void testAddRecipe() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		cm.addRecipe(r4);
		assertEquals(25, cm.makeCoffee(0, 25));
	}

	@Test
	public void testRepeatRecipe() {
		cm.addRecipe(r1);
		cm.addRecipe(r1);
		assertEquals(25, cm.makeCoffee(0, 25));
	}

	@Test
    public void testMakeCoffee2() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
        cm.addRecipe(r3);
        cm.addRecipe(r4);
        assertEquals(75, cm.makeCoffee(6, 75));
    }

	@Test
	public void testDeleteRecipe() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		assertEquals("Coffee",cm.deleteRecipe(0));
        assertEquals("Mocha",cm.deleteRecipe(1));
        assertEquals("Latte",cm.deleteRecipe(2));
        assertEquals(null,cm.deleteRecipe(3));
	}

	@Test
	public void testAddManyRecipes() {
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		cm.addRecipe(r4);
		assertEquals(false, cm.addRecipe(r5));
	}
}
