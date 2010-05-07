package edu.ucdenver.ccp.util.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CollectionsUtil {

	/**
	 * Returns a List<T> containing the input arguments.
	 * 
	 * @param <T>
	 * @param listEntries
	 * @return
	 */
	public static <T> List<T> createList(T... listEntries) {
		if (listEntries == null) {
			return null;
		}
		return Arrays.asList(listEntries);
	}

	/**
	 * Returns a Set<T> containing the input arguments.
	 * 
	 * @param <T>
	 * @param setEntries
	 * @return
	 */
	public static <T> Set<T> createSet(T... setEntries) {
		if (setEntries == null) {
			return null;
		}
		return new HashSet<T>(Arrays.asList(setEntries));
	}

	/**
	 * Converts an array<T> into a Set<T>
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> Set<T> array2Set(T[] array) {
		return new HashSet<T>(Arrays.asList(array));
	}

	/**
	 * Returns a mapping based on the input key/value pairings. Keys and Values must be the same
	 * type, and there must be an even number of input parameters, i.e. there must be a value for
	 * every key.
	 * 
	 * @param <T>
	 * @param mapKeyValuePairs
	 * @return
	 */
	public static <T> Map<T, T> createMap(T... mapKeyValuePairs) {
		Map<T, T> map = new HashMap<T, T>();
		for (int i = 0; i < mapKeyValuePairs.length; i += 2) {
			map.put(mapKeyValuePairs[i], mapKeyValuePairs[i + 1]);
		}
		return map;
	}

	/**
	 * Combines all input maps and returns the aggregate map.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param maps
	 * @return
	 */
	public static <K, V> Map<K, V> combineMaps(Map<K, V>... maps) {
		Map<K, V> combinedMap = new HashMap<K, V>();
		for (Map<K, V> map : maps) {
			combinedMap.putAll(map);
		}
		return combinedMap;
	}

	/**
	 * Converts a list of Strings to a list of Integers. Assumes the Strings are all integers.
	 * 
	 * @param intsAsStrings
	 * @return
	 */
	public static List<Integer> parseInts(List<String> intsAsStrings) {
		List<Integer> intsAsIntegers = new ArrayList<Integer>();
		for (String intAsString : intsAsStrings) {
			intsAsIntegers.add(Integer.parseInt(intAsString));
		}
		return intsAsIntegers;
	}

	/**
	 * Returns an array that goes from 0 to length-1 in value
	 * 
	 * @param length
	 * @return
	 */
	public static int[] createZeroBasedSequence(int length) {
		int[] sequence = new int[length];
		for (int i = 0; i < sequence.length; i++) {
			sequence[i] = i;
		}
		return sequence;
	}

	/**
	 * Adds a key/value pair to a one2many map, where the many are stored in a set.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param key
	 * @param value
	 * @param one2ManyMap
	 */
	public static <K, V> void addToOne2ManyMap(K key, V value, Map<K, Set<V>> one2ManyMap) {
		if (one2ManyMap.containsKey(key)) {
			one2ManyMap.get(key).add(value);
		} else {
			Set<V> newSet = new HashSet<V>();
			newSet.add(value);
			one2ManyMap.put(key, newSet);
		}
	}

}
