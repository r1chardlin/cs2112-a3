package test;

import modules.ModuleFactory;
import modules.*;
import util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Data Structures and Search class in util and modules
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 */
class Test
{

	private final ModuleFactory moduleFactory = new ModuleFactory();
	@org.junit.jupiter.api.Test
	void testHashTable()
	{
		// test constructor
		HashTable<Character, String> one = new HashTable<>(5);

		// test int hash()
		Character char1 = 'a';
		int char1HashCode = char1.hashCode();
		int hashedChar1 = one.hash(char1HashCode);
		assertEquals(char1HashCode % one.getNumBuckets(), hashedChar1);
		assertEquals(0, one.hash(0));

		// test int size(), int getNumBuckets(), put(), V put(K key, V value), void resize(), V get(Object key),
		// Set<K> keySet(), Iterator<K> iterator()
		assertEquals(0, one.size());
		one.put('a', "hi");
		assertEquals(1, one.size());
		one.put('b', "hello");
		one.put('c', "heehee");
		one.put('d', "asdf");
		one.put('q', "qwerty");
		assertEquals(5, one.size());
		assertEquals(5, one.getNumBuckets());
		assertEquals(true, one.containsKey('c'));
//		System.out.println();
//		for (char key : one.keySet())
//		{
//			System.out.println(key);
//		}
//		System.out.println();
		assertEquals(true, one.containsKey('d'));
		one.put('f', "ben");
		assertEquals(true, one.containsKey('a'));
		assertEquals(true, one.containsKey('b'));
		assertEquals(true, one.containsKey('q'));
		assertEquals(true, one.containsKey('d'));
		assertEquals(true, one.containsKey('f'));
		assertEquals(true, one.containsKey('c'));
		assertEquals(6, one.size());
		assertEquals(10, one.getNumBuckets());
		assertEquals(true, one.containsKey('f'));
		one.remove('f');
		assertEquals(false, one.containsKey('f'));
		assertEquals(5, one.size());
		assertEquals(10, one.getNumBuckets());

		// test boolean isEmpty()
		HashTable<String, Integer> two = new HashTable<>(5);
		assertEquals(false, one.isEmpty());
		assertEquals(true, two.isEmpty());

		// test boolean containsKey(Object key)
		assertEquals(false, two.containsKey(5));
		assertEquals(false, two.containsKey(null));

		// test boolean containsValue(Object value)
		assertEquals(true, one.containsValue("hi"));
		assertEquals(false, one.containsValue(""));
		assertEquals(false, one.containsValue(5));
		assertEquals(false, one.containsValue(null));
		assertEquals(false, two.containsValue(null));
		assertEquals(false, two.containsValue("hi"));

		// test V get(Object key)
		assertEquals("hi", one.get('a'));
		assertEquals(null, one.get(null));
		assertEquals(null, one.get(5));
		assertEquals(null, one.get('r'));

		// test V put(K key, V value);
		HashTable<Character, String> three = new HashTable<Character, String>(5);
		assertEquals(null, three.put('t', "0"));
		assertEquals(1, three.size());
		assertEquals(null, three.put('u', "1"));
		assertEquals(2, three.size());
		assertEquals("0", three.put('t', "2"));
		assertEquals(2, three.size());
//		Iterator<Character> iter = three.iterator();
//		while(iter.hasNext())
//		{
//			System.out.println(iter.next());
//		}

		// test void putALl(Map<? extends K, ? extend V> m)
		one.putAll(three);
		assertEquals(true, one.containsKey('t'));
		assertEquals(true, one.containsKey('u'));

		// test void clear(), Set<K> keySet()
		three.clear();
		assertEquals(true, three.isEmpty());

		// test Iterator<K> iterator()
		int count = 0;
		for (String key : two.keySet())
		{
			count++;
		}
		assertEquals(0, count);

		// test Object[] toArray()
		System.out.println();
		Object[] oneArr = one.toArray();
		int oneArrIndex = 0;
		for (char key : one.keySet())
		{
			assertEquals(true, key == (char) oneArr[oneArrIndex]);
			oneArrIndex++;
		}

		// test Set<K> keySet(); already tested

	}

	@org.junit.jupiter.api.Test
	void testTrie()
	{
		// test constructor
		Trie one = new Trie();

		// test void insert(String element), boolean contains(String elem)
		one.insert("hi");
		one.insert("hello");
		one.insert("world");
		one.insert("1a");
		assertEquals(true, one.contains("hi"));
		assertEquals(true, one.contains("hello"));
		assertEquals(true, one.contains("world"));
		assertEquals(true, one.contains("1a"));
		assertEquals(false, one.contains(""));
		assertEquals(false, one.contains("him"));
		assertEquals(false, one.contains("h"));
		assertEquals(false, one.contains(null));

		// test void delete(String elem), boolean contains(String elem)
		one.delete("hello");
		assertEquals(true, one.contains("hi"));
		assertEquals(true, one.contains("world"));
		assertEquals(true, one.contains("1a"));
		assertEquals(false, one.contains(""));
		assertEquals(false, one.contains("him"));
		assertEquals(false, one.contains("h"));
		assertEquals(false, one.contains("hello"));

		// test boolean contains(String elem) already tested

		// test void String closestWordToPrefix(String prefix)
		assertEquals("world", one.closestWordToPrefix("wo"));
		one.insert("won");
		assertEquals("won", one.closestWordToPrefix("wo"));
		assertEquals(null, one.closestWordToPrefix(null));
		assertEquals(null, one.closestWordToPrefix("cr"));
		assertEquals("hi", one.closestWordToPrefix(""));
	}

	@org.junit.jupiter.api.Test
	void testBloomFilter()
	{
		// test constructor
		BloomFilter<String> one = new BloomFilter<String>(null, 8888, 8);
		// test int hash(int k, int m)
		assertEquals(1, one.hash(15, 7));
		// test void insert (E elem)
		one.insert("hi");
		one.insert("hello");
		one.insert("ben");
		// test boolean mightContain(E elem)
		assertEquals(true, one.mightContain("hi"));
		assertEquals(true, one.mightContain("hello"));
		assertEquals(true, one.mightContain("ben"));
		assertEquals(false, one.mightContain("no"));
		assertEquals(false, one.mightContain("oogabooga"));
	}

	@org.junit.jupiter.api.Test
	void testSearch()
	{
		Search search = new Search();
		assertEquals(6, search.find("world", "hello world"));
		assertEquals(-1, search.find("heehee", "hello world"));
	}
}
