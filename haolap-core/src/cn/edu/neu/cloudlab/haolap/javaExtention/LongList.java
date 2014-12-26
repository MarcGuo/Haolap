package cn.edu.neu.cloudlab.haolap.javaExtention;

import java.util.Iterator;

public interface LongList<E> extends Iterable<E> {
    /**
     * Returns the number of elements in this list. If this list contains more
     * than Long.MAX_VALUE elements , returns Long.MAX_VALUE.
     *
     * @return
     */
    public long size();

    /**
     * Returns true if this list contains no elments.
     *
     * @return
     */
    public boolean isEmpty();

    /**
     * Returns true if this list contains the specified element. More formally,
     * returns true if and only if this list contains at least one element e
     * such that (o == null ? e == null : o.equals(e)).
     *
     * @param element
     * @return
     */
    public boolean contains(E element);

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    @Override
    public Iterator<E> iterator();

    /**
     * Remove the first occurrence of the specified element from this list, if
     * it is present (optional operation). If this list does not contain the
     * element, it is unchanged. More formally, removes the element with the
     * lowest index i such that (o == null ? get(i) == null : o.equals(get(i)))
     * (if such an element exsists). Returns true if this list contained the
     * specified element ( or equivalently, if this list changed as a result of
     * the call).
     *
     * @return true if this list contained the specified element
     */
    public E remove(long index);

    /**
     * Removes all of the elemnts from this list(optional operation). The list
     * will be empty after this call returns.
     */
    public void clear();

    /**
     * Replaces the element at the speecified position in this list with the
     * specified element(optional operation).
     *
     * @param index   index of the element to replace
     * @param element element to be the specified position
     * @return the element previously at the specified position
     */
    public E set(long index, E element);

    /**
     * Appends the specified element to the end of this list(optional
     * operation).
     *
     * @param element
     * @return
     */
    public boolean add(E element);

    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element. More
     * formally, returns the lowest index i such that (o == null ? get(i) ==
     * null : o.equals(get(i))), or -1 if there is no such index.
     *
     * @param element element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    long indexOf(E element);
}
