package net.pyel.utils;

/**
 * Custom Hash Map
 *
 * @author Zalán Tóth & Marcin Budzinski
 */
public class CustomHashMap<K, V> {
	private int size = 512; //size of the array, that is the number that every key hash value will be divided by, and the remainder will give the index
	//So if we have for example 64, the array size will be 64, and the maximum remainder will be 63, which is the maximum index for the array (as it starts with 0).
	private CustomEntry<K, V>[] table;


	public CustomHashMap() {
		table = new CustomEntry[size];
	}

	public void put(K key, V value) {
		int index = indexFor(key.hashCode()); //Getting the index, so we know where to put the entry.
		CustomEntry<K, V> newEntry = new CustomEntry<>(key, value, null); //The new entry to be stored

		if (table[index] == null) { //if the table at the specified index (which is the hashCode divided by the size) is null, then it is empty >
			table[index] = newEntry; // > so we store it directly at table[index]
		} else { //if there is already something stored in table[index], then add the entry linked to the last entry
			CustomEntry<K, V> current = table[index]; //The current entry, which is the value of table at the specified index
			CustomEntry<K, V> previous = null; //usage explained later

			while (current != null) {
				if (current.key.equals(key)) { //Overwriting the value of the key, if the provided key already exists
					current.value = value;
					return;
				}
				previous = current; //saving the last not null entry, so we can add the new one later
				current = current.next; //iteration for the while loop
			}
			previous.next = newEntry; //adding the new entry value to the end
		}

	}


	public V get(K key) {
		int index = indexFor(key.hashCode()); //getting the index
		CustomEntry<K, V> entry = table[index]; //getting the entry with the calculated index from the table (array)

		while (entry != null) {
			if (entry.key.equals(key)) {  //if the provided key equals a stored key, so we find a match!
				return entry.value; //returning the value for the key
			}
			entry = entry.next; //Iteration into the next entry in that table index location
		}
		return null; //no match found for the key
	}

	public void remove(K key) {
		int index = indexFor(key.hashCode()); //getting the index
		CustomEntry<K, V> current = table[index]; //The current entry, which is the value of table at the specified index
		CustomEntry<K, V> previous = null; //the entry before current, starts with null
		while (current != null) { //looking
			if (current.key.equals(key)) { //if the entry for the key is found
				if (previous == null) { //if the key is in the first entry
					table[index] = current.next; //set that first element the next
				} else { //if the key is not the first element
					previous.next = current.next; //connect the previous entry with the next entry counted from the current one, eleminating the current entry.
				}
				return; //found the element, so no need to look for more
			}
			previous = current;
			current = current.next;
		}
	}

	private int indexFor(int hashCode) {
		if (hashCode < 0) { //If the hashcode is minus
			hashCode *= -1;
		}
		return hashCode % size;  //Calculating the index
	}
}