package net.pyel.utils;

/**
 * Entry for hashmap
 *
 * @author Zalán Tóth & Marcin Budzinski
 */
public class CustomEntry<K, V> {
	public K key; //Holds the key
	public V value; //Holds the value associated with the key

	CustomEntry<K, V> next; //Holds the next entry

	public CustomEntry(K key, V value, CustomEntry<K, V> next) {
		this.key = key;
		this.value = value;
		this.next = next;
	}
}
