package com.company;
import sun.reflect.generics.tree.Tree;

import java.security.Key;
import java.util.TreeMap;


public class HashMap<K,V> {

    private static final int upperTol = 10;
    private static final int lowTol = 2;
    private static final int init = 7;

    private TreeMap<K,V>[] hashTable;
    private int M;
    private int size;

    HashMap(int M){
        this.M = M;
        this.size = 0;
        this.hashTable = new TreeMap[M];
        for(int i = 0; i<M ; i++)
        {
            this.hashTable[i] =  new TreeMap<>();
        }
    }


    HashMap()
    {
        this(init);
    }


    public int getSize(){
        return  size;
    }

    public int hash(K key)
    {
        return ( key.hashCode() & 0x7fffffff ) % M;
    }


    // 对应相应的四种 增删改查 操作
    public void add(K key ,V value){

        TreeMap<K,V> map = hashTable[hash(key)]; // 对每一个保存键值的treemap 进行一定的保存
        if(map.containsKey(key))
        {
            map.put(key, value);
        }
        else{
            map.put(key, value); // 没有这个键值 == 最新添加
            size ++;

            if(size >= upperTol * M)
            {
                // 如果每个表里面的均值大于某个数值 那么进行容量的扩充
                resize(2*M);
            }

        }
    }

    public V remove(K key){

        //首先查找哈希表里面是否存在这个键值，否则不删除
        TreeMap<K,V> map = hashTable[hash(key)];
        V ret = null;
        if(map.containsKey(key))
        {
            ret= map.remove(key);
            size --;  // 需要维护数量

            if( size < lowTol * M && M / 2 >= init)
            {
                // 相反 进行容量的缩减
                resize(M / 2);
            }
        }
        return ret;
    }



    private void resize(int newM){

        // 首先保存之前的 M
        int oldM = M;
        this.M = newM;

        TreeMap<K,V>[] newHashTable = new TreeMap[M];

        for(int i = 0 ; i< M ; i++)
        {
            // 对新的容器进行初始化
            newHashTable[i] = new TreeMap<>();
        }
        for(int i = 0 ; i< oldM ; i++)
        {
            // 然后依次进行数据的拷贝
            TreeMap<K,V> temp = hashTable[i]; // 首先提取每棵树
            for(K key : temp.keySet()){
                // 对每个键值进行处理
                newHashTable[hash(key)].put(key,temp.get(key));
            }
        }

        this.hashTable = newHashTable; // 替换
    }


    public void set(K key,V value){
        TreeMap<K,V> map = hashTable[hash(key)];
        if( map.containsKey(key)){
            //存在这个键值
            map.put(key,value);
        }else{
            throw new IllegalArgumentException(" the key don't exist");
        }
    }


    // 是否存在某个键值
    public boolean contains(K key){
        return hashTable[hash(key)].containsKey(key);
    }

    public V get(K key)
    {
        return  hashTable[hash(key)].get(key);
    }

}
