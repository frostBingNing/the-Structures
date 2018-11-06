package com.company;

public class BlackRedTree<E extends Comparable<E>> {


    // 增加两个静态常量
    private static final boolean RED = true;
    private static final boolean BLACK = false;


    private class Node {
        public E e;
        public Node left, right;
        public boolean color ;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public BlackRedTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 当前结点是否是红色
    public boolean isRed(Node node){
        if(node == null)
        {
            return BLACK;
        }
        return node.color;
    }


    // 增加一个左旋转操作  -- 根据定义 红色的结点是左倾斜
    private Node leftRotate(Node node)
    {
        Node x = node.right;

        node.right = x.left;
        x.left = node;

        // 更新颜色
        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 有了上面的左旋转肯定有下面的右旋转
    // 右旋转的产生条件 ---
    private Node rightRotate(Node node)
    {
        Node x = node.left;
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;
        return x;
    }
    // flipcolor
    private void filpcolor(Node node)
    {
        node.color = BLACK;
        node.right.color = RED;
        node.left.color = RED;
    }



    // 向红黑树中添加新的元素e
    public void add(E e) {
        root = add(root, e);
        root.color = BLACK;   // 维护根结点的颜色
    }

    // 向以node为根的红黑树中插入元素e，递归算法
    // 返回插入新节点后红黑树的根
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if (e.compareTo(node.e) > 0)
            node.right = add(node.right, e);

        //  当数值相等的情况下 不需要进行动作
        /*
           都是互斥的操作
         */
        // 下面就是对红黑树 颜色方面的调整
        if( isRed(node.right) && !isRed(node.left))
        {
            // 符合左旋转的要求
            node =   leftRotate(node);
        }
        //  右旋转
        if( isRed(node.left) && isRed(node.left.left))
        {
            node = rightRotate(node);
        }

        // 颜色翻转
        if( isRed(node.right) && isRed(node.left))
        {
            filpcolor(node);
        }

        return node;
    }

    public Node findNode(Node node, E e) {
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
        // 删除红黑树里面值对应的元素
        // 返回删除结点之后的根结点
        root = deleteRandomNode(root , e);
    }

    // 删除结点可能有些复杂 --- 在这里暂时不实现删除功能
    // import java.util.TreeMap TreeSet 里面有delete

    private Node deleteRandomNode(Node root,E e) {
        // 还是像上面一样进行递归删除操作
        if (root == null)
            return null; // 等价于没有找到数值

        if (e.compareTo(root.e) < 0) {
            root.left = deleteRandomNode(root.left, e);
            return root;
        } else if (e.compareTo(root.e) > 0) {
            root.right = deleteRandomNode(root.right, e);
            return root;
        } else {
            // e == node.e
            // 比较复杂 complex
            if (root.left == null) {
                // 直接替换掉当前接结点的右节点
                Node temp = root.right;
                root.right = null;
                size--;
                return temp;
            } else if (root.right == null) {
                // 和上面的正好相反
                Node temp = root.left;
                root.left = null;
                size--;
                return temp;
            } else {
                // root.left and root.right not equale null
                Node rightMix = getTheMin(root.right);
                rightMix.right = removeMin(root.right);
                rightMix.left = root.left;
                root.left = root.right = null;
                return rightMix;
            }

        }
    }
}


