# LRUCache

LRUCache is a Java implementation of a cache using the Least Recently Used (LRU) eviction policy. It provides an easy-to-use API for developers to store and retrieve key-value pairs from the cache.

## Installation

You can download the source code from the [GitHub repository](https://github.com/duttabhishek0/ThreadSafe-LRUCache). To use the library in your project, you can build it using Maven or add it as a dependency in your build file.

## Usage

To use the LRUCache, you need to create an instance of the `LRUCache` class and specify the maximum number of items it can hold. Here is an example:
```java
import core.LRUCache;

LRUCache<String, Integer> cache = new LRUCache<>(10);
```

To add an item to the cache, use the `put` method:
```java
cache.put("key1", 1);
```

To retrieve an item from the cache, use the `get` method:
```java
Integer value = cache.get("key1");
```

If the key is not found in the cache, the `get` method returns `null`.
You can also remove an item from the cache using the `remove` method:
```java
cache.remove("key1");
```

To clear the cache, use the `clear` method:
```java
cache.clear();
```

The `size` method returns the number of items currently in the cache:
```java
int size = cache.size();
```

## Implementation
Under the `domain` package, can be seen three files, namely
1. AccessStatistics
2. CacheEntry
3. CacheNode.

<b>The `CacheNode` Data structure</b>

This is a CacheNode class that is used in a doubly linked list for the Least Recently Used (LRU) eviction strategy in a cache. It represents an entry in the cache and has the following attributes:

1. `key`: The key of the entry in the cache.
2. `value`: The value of the entry in the cache.
3. `prev`: The previous node in the doubly linked list.
4. `next`: The next node in the doubly linked list.
5. `ttl`: The time-to-live duration of the entry in the cache.
6. `expirationTime`: The expiration time of the entry in the cache.

To accomplish the required behaviour, we can use a combination of a HashMap and a doubly linked list. The HashMap will allow us to retrieve values quickly using keys, while the doubly linked list will keep track of the least recently used entries and make it easy to evict them when the cache is full.

```
       +------------------+
       |     Cache        |
       +------------------+
       | - capacity       |
       | - size           |
       | - head           |
       | - tail           |  
       | - map            |
       +------------------+
                 |
                 |
                 |
        +-----------------+
        |    CacheNode    |
        +-----------------+
        | - key           |
        | - value         |
        | - prev          |
        | - next          |
        | - ttl           |
        | - expirationTime|
        +-----------------+
```


## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue if you find a bug or have a suggestion for improvement. Currently it only supports LRU Replacement Policy, other policies can be implmented by implementing the [EvictionPolicy](https://github.com/duttabhishek0/ThreadSafe-LRUCache/blob/master/src/Interface/EvictionPolicy.java) Interface. 

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/duttabhishek0/ThreadSafe-LRUCache/blob/master/LICENSE) file for details.

## Test:
![Screenshot from 2023-04-14 16-49-49](https://user-images.githubusercontent.com/56694152/232032912-e0f3e9f3-829e-47b9-8b33-89b142eba849.png)
