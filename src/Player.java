public class Player {
    private String name,country,club,position;
    private int age,number,WeeklySalary;
    private double height;

    public Player()
    {
        name = country = club = position = null;
        age = number = WeeklySalary = 0;
        height = 0;
    }

    public Player(String name,String country,int age,double height,String club,String position,int number,int WeeklySalary)
    {
        this.name = name;
        this.country = country;
        this.club = club;
        this.position = position;
        this.age = age;
        this.number = number;
        this.WeeklySalary = WeeklySalary;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public int getNumber() {
        return number;
    }

    public int getWeeklySalary() {
        return WeeklySalary;
    }

    public double getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setWeeklySalary(int WeeklySalary) {
        this.WeeklySalary = WeeklySalary;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    @Override
    public String toString()
    {

        if(number != 0)
            return "Name: "+name+"\nCountry: "+country+"\nAge: "+age+"\nHeight: "+height+"\nClub: "+club+"\nPosition: "+position+"\nJersey Number: "+number+"\nWeekly Salary: "+WeeklySalary+"\n";
        else
            return "Name: "+name+"\nCountry: "+country+"\nAge: "+age+"\nHeight: "+height+"\nClub: "+club+"\nPosition: "+position+"\nWeekly Salary: "+WeeklySalary+"\n";

    }


}
