package gwt.client.collect;

/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.HashMap;



public final class HashBiMap<K, V>  {

  /**
   * Returns a new, empty {@code HashBiMap} with the default initial capacity
   * (16).
   */
  public static <K, V> HashBiMap<K, V> create() {
    return new HashBiMap<K, V>();
  }

  HashMap<K, V> a;
  HashBiMap<V, K> aI;
  

  private HashBiMap() {
    a=new HashMap<K, V>();
    aI=new HashBiMap<V, K>(this);
  }

 


	  // Query Operations (optimizations)

	   public HashBiMap(HashBiMap hashBiMap) {
		a =   new HashMap<K, V>();
		aI = hashBiMap;
	   }
	// TODO Auto-generated constructor stub





	public boolean containsValue(V value) {
	    return aI.containsKey(value);
	  }

	  // Modification Operations

	  

	   public V forcePut(K key, V value) {
		    return putInBothMaps(key, value, true);
		  }
	   
	   public V put(K key, V value) {
		    return putInBothMaps(key, value, true);
		  }

	  private V putInBothMaps( K key,  V value, boolean force) {
	    boolean containedKey = containsKey(key);
	    if (containedKey && value != null&&value.equals(get(key))) {
	      return value;
	    }
	    if (force) {
	      aI.a.remove(value);
	    } 
	    V oldValue = a.put(key, value);
	    updateInverseMap(key, containedKey, oldValue, value);
	    return oldValue;
	  }

	  public V get(K key) {

		return a.get(key);
	}




	public boolean containsKey(K key) {

		return a.containsKey(key);
	}




	private void updateInverseMap(
	      K key, boolean containedKey, V oldValue, V newValue) {
	    if (containedKey) {
	      aI.a.remove(oldValue);
	    }
	    aI.a.put(newValue, key);
	  }






	



	public void clear() {
		aI.a.clear();
		a.clear();
		
	}




	public HashBiMap<V,K> inverse() {
		
		return aI;
	}



}
