package top.systemsec.survey.bean;

public class UserBean {

    /**
     * "id":1,"phone":"13111111111",
     * "station":"宝民派出所","street":"新安街道","name":"测试用户","password":"abc"
     */
    private int id;

    private String phone;

    private String station;//站台信息

    private String street;//街道

    private String name;//名字

    private String password;//密码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
