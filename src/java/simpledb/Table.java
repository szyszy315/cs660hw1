package simpledb;

import java.util.HashMap;

public class Table {
    private DbFile file;
    private String name;
    private String primarykey;
    private HashMap<String,Integer> nametotableid;
    public Table(DbFile file,String name, String pri) {
        this.file = file;
        this.name  = name;
        this.primarykey = pri;
    }

    public DbFile getfile() {
        return file;
    }

    public String getPrimarykey() {
        return primarykey;
    }

    public String getName() {
        return name;
    }
}