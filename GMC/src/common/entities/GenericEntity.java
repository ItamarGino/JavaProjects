package common.entities;

import java.util.ArrayList;

public class GenericEntity<E>
{
	private ArrayList<E> elementsArray;
	
	public boolean add(E element) 
	{
		return this.elementsArray.add(element);
	}
	
	public boolean edit(E oldElement,E newElement)
	{
		// Checking if the maps are'nt equals
		// Looking for the oldMap
		// Replacing the oldMap with newMap
		if(oldElement.equals(newElement) == true)
			return false;
		if(this.elementsArray.contains(oldElement) == false)
			return false;
		else
		{
			if(this.elementsArray.remove(oldElement) == false)
				return false;
			else
			{
				this.elementsArray.add(newElement);
				return true;
			}
		}
			
	}
	
	public boolean delete(E element)
	{
		return this.elementsArray.remove(element);
	}

	public ArrayList<E> getElementsArray() {
		return elementsArray;
	}

	public void setElementsArray(ArrayList<E> elementsArray) {
		this.elementsArray = elementsArray;
	}
}
