package cn.edu.neu.cloudlab.haolap.javaExtention;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LongArrayList<E> implements LongList<E> {
    private List<List<E>> parts = new ArrayList<List<E>>();

    public LongArrayList() {
        super();
    }

    @Override
    public long size() {
        long size = 0;
        for (List<E> part : parts) {
            size += part.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (List<E> part : parts) {
            if (part.isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(E element) {
        for (List<E> part : parts) {
            if (part.contains(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LongArrayListIterator<E>(this);
    }

    @Override
    public E remove(long index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("specified index: " + index
                    + ", out of range: 0~" + size());
        }
        int partNo = getPartNo(index);
        int indexForSpecifiedPart = getIndexForSpecifiedPart(index);
        List<E> part = parts.get(partNo);
        return part.remove(indexForSpecifiedPart);
    }

    @Override
    public void clear() {
        for (List<E> part : parts) {
            part.clear();
        }
        parts.clear();
    }

    @Override
    public E set(long index, E element) {
        int partNo = getPartNo(index);
        int indexForSpecifiedPart = getIndexForSpecifiedPart(index);
        List<E> specifiedPart = parts.get(partNo);
        return specifiedPart.set(indexForSpecifiedPart, element);
    }

    @Override
    public boolean add(E element) {
        // if parts is empty, add one list as a part
        if (parts.size() == 0) {
            parts.add(new ArrayList<E>());
        }
        List<E> currentPart = parts.get(parts.size() - 1);
        // if the currentPart is full
        if (currentPart.size() == Integer.MAX_VALUE) {
            parts.add(new ArrayList<E>());
            // update currentPart
            currentPart = parts.get(parts.size() - 1);
        }
        return currentPart.add(element);
    }

    @Override
    public long indexOf(E element) {
        long index = 0;
        for (List<E> part : parts) {
            int indexSpecifiedForPart = part.indexOf(element);
            if (indexSpecifiedForPart != 0) {
                index += indexSpecifiedForPart;
                return index;
            }
            index += Integer.MAX_VALUE;
        }
        return -1;
    }

    private int getIndexForSpecifiedPart(long index) {
        return (int) (index % Integer.MAX_VALUE);
    }

    private int getPartNo(long index) {
        return (int) (index / Integer.MAX_VALUE);
    }

    public class LongArrayListIterator<ElementType> implements
            Iterator<ElementType> {
        private LongArrayList<ElementType> longArrayList;
        private Iterator<List<ElementType>> currentListItr;
        private Iterator<ElementType> currentElementItr;

        public LongArrayListIterator(LongArrayList<ElementType> longArrayList) {
            super();
            this.longArrayList = longArrayList;
            this.currentListItr = this.longArrayList.parts.iterator();
            this.currentElementItr = null;
        }

        @Override
        public boolean hasNext() {
            // if not initialized, initialize it first.
            if (this.currentElementItr == null) {
                if (this.currentListItr.hasNext()) { // have lists
                    this.currentElementItr = this.currentListItr.next()
                            .iterator();
                } else {
                    return false; // no lists, return false
                }
            }
            // initialized, and has next element
            if (this.currentElementItr.hasNext()) {
                return true;
            }
            // initialized, but doesn't has next element
            if (this.currentListItr.hasNext()) {
                // get next list to iterate
                this.currentElementItr = this.currentListItr.next().iterator();
                return this.currentElementItr.hasNext();
            } else {
                return false;
            }
            // the lists are added in a queue, there's no need to check the
            // third list, because it won't be exist.
        }

        @Override
        public ElementType next() {
            // if not initialized, initialize it first
            if (this.currentElementItr == null) {
                if (this.currentListItr.hasNext()) { // have lists
                    this.currentElementItr = this.currentListItr.next()
                            .iterator();
                } else {
                    throw new NoSuchElementException();
                }
            }
            // initialized, and has next element
            if (this.currentElementItr.hasNext()) {
                return this.currentElementItr.next();
            }
            // initialized, but doesn't has next element
            if (this.currentListItr.hasNext()) {
                // get next list to iterate
                this.currentElementItr = this.currentListItr.next().iterator();
                return this.currentElementItr.next();
            } else {
                throw new NoSuchElementException(
                        "Iteration has no more elements, You should check hasNext() before iterator it");
            }
        }

        @Override
        public void remove() {
            // if the next method has not yet been called, or the remove method
            // has already been called after the last call to the next method
            // And after the next method called, currentElementItr will
            // certainly be
            // initialized.
            if (this.currentElementItr != null) {
                this.currentElementItr.remove();
            }
            throw new IllegalStateException(
                    "the next method has not yet been called");
        }
    }

}
