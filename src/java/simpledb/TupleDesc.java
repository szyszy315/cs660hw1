package simpledb;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here

        return Fieldsintuple.iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
        Fieldsintuple = new ArrayList<TDItem>();
        for (int i = 0; i < typeAr.length; i++) {
            Fieldsintuple.add(new TDItem(typeAr[i],fieldAr[i]));
            Sizeoftuple += typeAr[i].getLen();
        }
    }

    private ArrayList<TDItem> Fieldsintuple;
    private int Sizeoftuple;
    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
        this(typeAr,new String[typeAr.length]);
    }


    public ArrayList getall(){
        return Fieldsintuple;
    }


    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return Fieldsintuple.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
        return Fieldsintuple.get(i).fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
        return Fieldsintuple.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
        for(int i = 0; i < Fieldsintuple.size(); i ++ ) {
            if (this.getFieldName(i) == null) {
                if (name == null) {
                    return i;
                }
            }
            else if (this.getFieldName(i).equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException("no fields are named, so you can't find it");
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
//        int size = 0;
//        for (TDItem i:Fieldsintuple) {
//            size += i.fieldType.getLen();
//        }
        return Sizeoftuple;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here
        int n = td1.getall().size() + td2.getall().size();
        Type[] newtype = new Type[n];
        String[] newcontent = new String[n];
        for(int i = 0; i < td1.getall().size(); i++) {
            newtype[i] = td1.getFieldType(i);
            newcontent[i] = td1.getFieldName(i);
        }
        for(int i = 0; i < td2.getall().size(); i++) {
            newcontent[i+ td1.getall().size()] = td2.getFieldName(i);
            newtype[i+td1.getall().size()] = td2.getFieldType(i);
        }
        return new TupleDesc(newtype,newcontent);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        if (!(o instanceof TupleDesc)) {
            return false;
        }
        TupleDesc newo = (TupleDesc) o;
        if (((TupleDesc) o).getSize() != getSize()) {
            return false;
        }
        for(int i = 0; i < Fieldsintuple.size(); i ++) {
            if (newo.getFieldType(i) == null) {
                if (getFieldType(i) != null) {
                    return false;
                }
            }
            else if (!this.getFieldType(i).equals(newo.getFieldType(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Fieldsintuple.size(); i ++) {
            sb.append(Fieldsintuple.get(i).toString()).append(", ");
        }
        return sb.toString();
    }
}
