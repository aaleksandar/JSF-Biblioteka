package db;
// Generated Sep 2, 2012 12:30:19 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * SystemActivated generated by hbm2java
 */
public class SystemActivated  implements java.io.Serializable {


     private Integer id;
     private Date timestamp;
     private int year;
     private Boolean activated;

    public SystemActivated() {
    }

	
    public SystemActivated(int year) {
        this.year = year;
    }
    public SystemActivated(int year, Boolean activated) {
       this.year = year;
       this.activated = activated;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    public Boolean getActivated() {
        return this.activated;
    }
    
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }




}


