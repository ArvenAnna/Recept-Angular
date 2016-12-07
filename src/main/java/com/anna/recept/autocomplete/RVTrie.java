package com.anna.recept.autocomplete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Class represents implementation of Trie interface
 *
 */
public class RVTrie implements Trie {

    private final TrieNode root;

    public RVTrie() {
        root = new TrieNode(' ', 0, null);
    }

    @Override
    public void add(Tuple tuple) {
        if (tuple != null) {
            char arr[] = tuple.getTerm().toCharArray();
            TrieNode node = root;
            int i = 0;
            while (i < arr.length) {
                TrieNode child = node.getChild(arr[i]);
                if (child != null) {
                    node = child;
                } else {
                    TrieNode newChild = new TrieNode(arr[i], 0, node);
                    node.setChild(newChild);
                    node = newChild;
                }
                i++;
            }
            node.setValue(tuple.getWeigh()); //last node is a leaf
        }
    }

    @Override
    public boolean contains(String word) {
        if (word == null) {
            return false;
        }
        char arr[] = word.toCharArray();
        TrieNode node = root;
        int i = 0;
        while (i < arr.length) {
            TrieNode child = node.getChild(arr[i]);
            if (child == null) {
                return false;
            }
            node = child;
            i++;
        }
        if (node.getValue().equals(0)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean delete(String word) {
        if (word == null) {
            return false;
        }
        TrieNode currentNode = root;
        char arr[] = word.toCharArray();
        int i = 0;
        while (i < arr.length) {
            TrieNode child = currentNode.getChild(arr[i]);
            if (child != null) {
                currentNode = child;
            } else {
                return false; //if node for some letter of world is missing
            }
            i++;
        }
        if (currentNode.getValue() > 0) { //if last node is a leaf
            currentNode.setValue(0);
        } else {
            return false;
        }

        while (!currentNode.equals(root)) {
            if (currentNode.getChildenSize() > 0 || currentNode.getValue() > 0) {
                return true;
            } else {
                char key = currentNode.getKey();
                currentNode = currentNode.getParent();
                currentNode.deleteChild(key);
            }
        }
        return true;
    }

    @Override
    public Iterable<String> words() {
        return subTrieWords(root);
    }

    private Iterable<String> subTrieWords(final TrieNode subTrieRoot) {

        return () -> new Iterator<String>() {

            private TrieNode currentLeaf;
            private Queue<TrieNode> currentLevelNodes = new ArrayDeque<TrieNode>();

            {
                currentLevelNodes.add(subTrieRoot);
            }

            @Override
            public boolean hasNext() {
                while (!currentLevelNodes.isEmpty()) {
                    if (currentLevelNodes.peek().getValue() == 0) {
                        currentLevelNodes.addAll(currentLevelNodes.peek().getChildren());
                        currentLevelNodes.poll();
                    } else {
                        currentLeaf = currentLevelNodes.peek();
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String next() {
                if (hasNext() == false) {
                    return null;
                }
                currentLevelNodes.addAll(currentLevelNodes.peek().getChildren());
                currentLevelNodes.poll();
                return getWordFromCurentLeaf();
            }

            private String getWordFromCurentLeaf() {
                List<TrieNode> wordNodes = new ArrayList<TrieNode>();
                wordNodes.add(currentLeaf);
                TrieNode parent = currentLeaf.getParent();

                while (!parent.equals(root)) {
                    wordNodes.add(parent);
                    parent = parent.getParent();
                }
                Collections.reverse(wordNodes);
                String word = "";
                for (TrieNode node : wordNodes) {
                    word = word + String.valueOf(node.getKey());
                }
                if (word.equals("")) {
                    word = null;
                }
                return word;
            }

            @Override
            public void remove() {
                delete(getWordFromCurentLeaf());
            }
        };
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        Iterable<String> empty = () -> new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public String next() {
                return null;
            }

            @Override
            public void remove() {
            }
        };
        if (pref == null) {
            return empty;
        }
        char arr[] = pref.toCharArray();
        TrieNode node = root;
        int i = 0;
        while (i < arr.length) {
            TrieNode child = node.getChild(arr[i]);
            if (child == null) {
                return empty;
            }
            node = child;
            i++;
        }
        return subTrieWords(node);
    }

    @Override
    public int size() {
        return subTrieSize(root, 0);
    }

    /**
     * Counts size of subTrie and writes it to static class field size
     *
     * @param node root for subTrie
     */
    private int subTrieSize(TrieNode node, int initial) {
        int size = initial;
        if (node.getChildenSize() > 0) {
            for (TrieNode child : node.getChildren()) {
                if (child.getValue() > 0) {
                    size++;
                }
                size = subTrieSize(child, size);
            }
        }
        return size;
    }

    /**
     * Class represents node of RVTrie
     */
    private static class TrieNode {

        private final List<TrieNode> children = new ArrayList<TrieNode>();
        private final char key;
        private Integer value;
        private final TrieNode parent;

        public TrieNode(char key, Integer value, TrieNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public char getKey() {
            return key;
        }

        public TrieNode getParent() {
            return parent;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setChild(TrieNode node) {
            children.add(node);
        }

        public void deleteChild(char deletedKey) {
            TrieNode deleted = null;
            for (TrieNode node : children) {
                if (node.getKey() == deletedKey) {
                    deleted = node;
                }
            }
            children.remove(deleted);
        }

        public TrieNode getChild(char childKey) {

            for (TrieNode node : children) {
                if (node.getKey() == childKey) {
                    return node;
                }
            }
            return null;
        }

        public int getChildenSize() {
            return children.size();
        }

        public List<TrieNode> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "TrieNode{" + ", key=" + key + ", value=" + value + '}';
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + this.key;
            hash = 17 * hash + Objects.hashCode(this.value);
            hash = 17 * hash + Objects.hashCode(this.parent);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TrieNode other = (TrieNode) obj;
            if (this.key != other.key) {
                return false;
            }
            if (!Objects.equals(this.value, other.value)) {
                return false;
            }
            if (!Objects.equals(this.parent, other.parent)) {
                return false;
            }
            return true;
        }
    }
}
