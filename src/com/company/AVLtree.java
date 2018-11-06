package com.company;

import java.util.ArrayList;

public class AVLtree<E extends Comparable<E>> {


    private class Node {
        public E e;
        public Node left, right;
        // 这里两者之间的主要区别就是
        /*
          avl 里面需要对每一个结点进行高度的标记
             -- 方便每一个结点对平衡因子进行求解
          bst 不需要对每一个结点进行标记
         */
        public int height;


        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
            // 每个结点的初始值就是 1
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLtree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 获取当前结点的高度值
    private int getHeight(Node node) {
        // 返回当前结点的node的高度值
        if(node == null){
            return 1;
        }
        return node.height; //返回当前结点的高度
    }


    //获取当前结点的平衡因子
    private int getBalanceFactor(Node node)
    {
        if(node == null)
        {
            return 0;
        }
        // 返回平衡因子 --- 这里是正整数

        //如果这里返回的是绝对值 不利于后面左右rotate的 判断
        return getHeight(node.left) - getBalanceFactor(node.right);
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e) {
        root = add(root, e);
    }

    // 向以node为根的二分搜索树中插入元素e，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);  // 在这个里面已经对height 进行了初始化 所以不用变
        }

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if (e.compareTo(node.e) > 0)
            node.right = add(node.right, e);

        // 这里高度的维护可以进行优化
        int tempHeight = node.height;
        if(tempHeight == Math.max(getHeight(node.left),getHeight(node.right) + 1))
        {
            return node;
        }

        // 每次添加完一个结点 都需要对当前的结点进行高度的维护
        node.height = Math.max(getHeight(node.left),getHeight(node.right)) + 1;

        // 在这里可以进行一种判断 -- 添加元素以后当前的树是否还是avl
        int  theBalanceFactor  = getBalanceFactor(node);
        if(theBalanceFactor > 1 || theBalanceFactor < -1 )
        {
            System.out.println("the balanceFactor is : " + theBalanceFactor );
        }

        // 维护平衡二叉树
        if( theBalanceFactor > 1 && getBalanceFactor(node.left) >= 0)
        {
            // 如果当前树 左倾斜
            rightRotate(node);
        }
        if( theBalanceFactor <  -1 && getBalanceFactor(node.right) <0 )
        {
            //  当前的树右倾斜
            leftRotate(node);
        }
        // LR RL
        if( theBalanceFactor > 1 && getBalanceFactor(node.left) < 0)
        {
            leftRigthRotate(node);
        }
        if( theBalanceFactor < -1 && getBalanceFactor(node.right) >0)
        {
            rightLeftRotate(node);
        }

        return node;
    }

    // 对这个平衡二叉树 添加几个函数 用来判断是否是 而二叉搜索树  以及  avl
    public boolean isBST()
    {

        //首先定义一个数组
        ArrayList<E> temp = new ArrayList<>();
        isBST(root,temp);
        // 这里对temp 进行数据上面的判断
        for(int i = 1 ; i < temp.size() ; i++)
        {
            // 这里的temp 获取长度的方法不是 length 而是 siz
            if( temp.get(i - 1 ).compareTo(temp.get(i)) > 0)
            {
                return false;
            }
        }
        return true;
    }

    private void isBST(Node node ,ArrayList<E> temp)
    {
        // 从这里开始进行相应的判断
        // 需要中序遍历
        if(node == null)
        {
            return;
        }
        isBST(node.left,temp);
        temp.add(node.e);
        isBST(node.right,temp);
    }

    // is avl
    public boolean isAVL()
    {
        // 是不是平衡二叉树  还是对每一个结点进行相应的判断
        // 计算当前结点的左右子树是否高度差合法
        return isAVL(root);
    }

    private boolean isAVL(Node node){

        if(node == null)
        {
            return true;
        }
        //不是空节点
        int balancedFactor = getBalanceFactor(node);
        if(balancedFactor > 2)
            return false;
        // 下面采用递归遍历的方法--- 对所有的结点进行一种遍历
        return isAVL(node.left) && isAVL(node.right);
    }

    // 平衡二叉树的左右旋转  rotate 旋转
    private Node rightRotate(Node y)
    {
        // 暂时需要标记 x y z 三个结点
        Node x = y.left;
        Node t = x.right;

        // 旋转逻辑
        x.right = y;
        y.left = t;

        // 维护高度
        y.height = 1 + Math.max(getHeight(y.left) , getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left) , getHeight(x.right));


        //返回结点 -- 旋转之后的根结点
        return x;
    }

    // leftRotate
    private Node leftRotate(Node y)
    {
        // the same as up
        Node x = y.right;
        Node t = x.left;

        x.left = y;
        y.right = t;

        y.height = 1 + Math.max(getHeight(y.left) , getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left) , getHeight(x.right));

        return x;
    }
    //  上面的两种情况分别对应最最正常的两种情况   然后 2*2 = 4
    //  下面是 LR RL 两种特殊的情况
    private Node leftRigthRotate(Node y)
    {
        // 进行两次旋转
        y.left = leftRotate(y.left);
        rightRotate(y);
        return y;
    }

    private Node rightLeftRotate(Node y)
    {
        y.right = rightRotate(y.right);
        leftRotate(y);
        return y;
    }



    private Node findNode(Node node, E e) {
        if (node == null)
            return null;

        if (e.compareTo(node.e) < 0) {
            return findNode(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            return findNode(node.right, e);
        } else {
            return node;
        }
    }


    /**
     * @param
     * @return the value
     * @// FIXME: 2018/10/21
     */

    public E theMin() {
        if (size == 0) {
            throw new IllegalArgumentException("the bst is empty");
        }
        Node min = getTheMin(root);
        return min.e; // 直接返回最小节点的数值
    }

    public Node getTheMin(Node node) {

        if (node.left == null)
            return node;   // 递归条件的终结
        return getTheMin(node.left); //递归继续进行下去的连接
    }

    /**
     * @param * @information find the max value
     */
    public E theMax() {
        if (size == 0) {
            throw new IllegalArgumentException("the bst is empty");
        }
        Node max = getTheMax(root);
        return max.e;
    }

    public Node getTheMax(Node node) {
        if (node.right == null) {
            return node;
        }
        return getTheMax(node.right);
    }


    /**
     * @param : the correct node and value
     */
    public E removeMin() {
        E ret = theMin(); // 返回最小值
        root = removeMin(root);  // return a min node
        return ret;
    }

    private Node removeMin(Node root) {
        if (root.left == null) {
            // 表示当前结点就是最小值所在的结点
            Node temp = root.right;
            root.right = null; // 设置为空
            size--;
            return temp;
        }
        // 这里逻辑出现了一定的问题 没有维护后期的树
        root.left = removeMin(root.left);
        return root; // 没有到达递归结束的最后条件继续执行相应的操作

    }

    public E removeMax() {
        E ret = theMax();
        root = removeMax(root);
        return ret;
    }

    private Node removeMax(Node root) {

        if (root.right == null) {
            //表示当前结点就是要删除结点
            Node temp = root.left;
            root.left = null;
            size--;
            return temp;
        }
        // 一样没有维护当前的树
        root.right =  removeMax(root.right);
        return root;
    }

    /**
     * @param : delete random node
     *
     */
    public void deleteRandomNode(E e){
        // 删除二分搜索树里面值对应的元素
        //返回删除结点之后的根结点
        root = deleteRandomNode(root , e);
    }

    private Node deleteRandomNode(Node root,E e) {
        // 还是像上面一样进行递归删除操作
        if (root == null)
            return null; // 等价于没有找到数值

        // 添加另一个结点元素 用来保存数据
        Node retNode ;

        if (e.compareTo(root.e) < 0) {
            root.left = deleteRandomNode(root.left, e);
            retNode = root;
        } else if (e.compareTo(root.e) > 0) {
            root.right = deleteRandomNode(root.right, e);
            retNode = root;
        } else {
            // e == node.e
            // 比较复杂 complex
            if (root.left == null) {
                // 直接替换掉当前接结点的右节点
                Node temp = root.right;
                root.right = null;
                size--;
                retNode = temp;
            } else if (root.right == null) {
                // 和上面的正好相反
                Node temp = root.left;
                root.left = null;
                size--;
                retNode = temp;
            } else {
                // root.left and root.right not equale null
                Node rightMix = getTheMin(root.right);
                rightMix.right = removeMin(root.right);
                rightMix.left = root.left;
                root.left = root.right = null;
                retNode = rightMix;
            }

        }

        // 针对retNode 进行相应的判断
        if( retNode == null)
        {
            return null;
        }
        int  theBalanceFactor  = getBalanceFactor(retNode);
        if(theBalanceFactor > 1 || theBalanceFactor < -1 )
        {
            System.out.println("the balanceFactor is : " + theBalanceFactor );
        }

        // 维护平衡二叉树
        if( theBalanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)
        {
            // 如果当前树 左倾斜
            rightRotate(retNode);
        }
        if( theBalanceFactor <  -1 && getBalanceFactor(retNode.right) <0 )
        {
            //  当前的树 右倾斜
            leftRotate(retNode);
        }
        // LR RL
        if( theBalanceFactor > 1 && getBalanceFactor(retNode.left) < 0)
        {
            leftRigthRotate(retNode);
        }
        if( theBalanceFactor < -1 && getBalanceFactor(retNode.right) >0)
        {
            rightLeftRotate(retNode);
        }

        //  返回一定的数值
        return retNode;
    }


    // 采用中序遍历里面的元素
    public void showInformation()
    {
        StringBuilder str = new StringBuilder();
        getInformation(root,str);
        System.out.println(str.toString());
    }

    private StringBuilder getInformation(Node node, StringBuilder str)
    {
        if(node == null){
            return null;
        }
        getInformation(node.left,str);
        str.append(node.e + "->");
        getInformation(node.right,str);

        return str;
    }
}


