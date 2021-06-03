import interfaces.INode;
import interfaces.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

    private INode<T, V> root;
    private final INode<T, V> nil = new Node<>(INode.BLACK);

    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public V search(T key) {
        return null;
    }

    @Override
    public boolean contains(T key) {
        return false;
    }

    @Override
    public void insert(T key, V value) {
        if (key == null) return;
        if (root == null) { /*first node*/
            root = createNode(null, INode.BLACK);
            root.setKey(key);
            root.setValue(value);
            return;
        }

        INode<T, V> currentNode, nextNode = root;
        int res;

        do {
            currentNode = nextNode;
            res = key.compareTo(currentNode.getKey());
            if (res > 0) nextNode = currentNode.getRightChild();
            else nextNode = currentNode.getLeftChild();
        } while (!nextNode.equals(nil));

        INode<T, V> node = createNode(currentNode, INode.RED);
        if (res > 0 ) currentNode.setRightChild(node);
        else  currentNode.setLeftChild(node);

        insertionFixup(node);
    }

    @Override
    public boolean delete(T key) {
        return false;
    }

    private INode<T, V> createNode(INode<T, V> parent, boolean color) {
        INode<T, V> node = new Node<>(color);
        node.setParent(parent);
        node.setLeftChild(nil);
        node.setRightChild(nil);
        return node;
    }

    private void insertionFixup(INode<T, V> node) {
        if (node.equals(root)) {
            node.setColor(INode.BLACK);
            return;
        }

        INode<T, V> parent = node.getParent();
        if (isBlack(parent)) return; /*also handles the case where my parent is the root*/

        INode<T, V> uncle = getUncle(node);

        /*case uncle is red*/
        if (isRed(uncle)) {
            uncle.setColor(INode.BLACK);
            parent.setColor(INode.BLACK);
            INode<T, V> grandParent = parent.getParent();
            grandParent.setColor(INode.RED);
            insertionFixup(grandParent);
        }
        else {
            if(isRightChild(node)) {
                //todo: left rotate
            }

            //todo: right rotate and recolor
        }
    }

    private INode<T, V> getUncle(INode<T, V> node) {
        INode<T, V> parent = node.getParent(), grandParent = parent.getParent();
        INode<T, V> leftChild = grandParent.getLeftChild();

        if (parent.equals(leftChild)) return grandParent.getRightChild();
        return leftChild;
    }

    private boolean isRightChild(INode<T, V> node) {
        INode<T, V> parent = node.getParent();
        INode<T, V> rightChild = parent.getRightChild();
        return node.equals(rightChild);
    }

    private boolean isRed(INode<T, V> node) {
        return node.getColor();
    }

    private boolean isBlack(INode<T, V> node) {
        return !node.getColor();
    }
}
