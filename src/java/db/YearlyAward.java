package db;
// Generated Sep 2, 2012 12:30:19 PM by Hibernate Tools 3.2.1.GA



/**
 * YearlyAward generated by hbm2java
 */
public class YearlyAward  implements java.io.Serializable {


     private Integer id;
     private User user;
     private int year;

    public YearlyAward() {
    }

    public YearlyAward(User user, int year) {
       this.user = user;
       this.year = year;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }




}


