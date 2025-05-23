/***********/
/* PACKAGE */
/***********/
package TEMP;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class TEMP_FACTORY
{
	public int counter=0;
	
	public TEMP getFreshTEMP()
	{
		return new TEMP(counter++);
	}

	public TEMP getSpecialTEMP(int i)
	{
		return new TEMP(i);
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TEMP_FACTORY instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TEMP_FACTORY() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TEMP_FACTORY getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new TEMP_FACTORY();
		}
		return instance;
	}
}
