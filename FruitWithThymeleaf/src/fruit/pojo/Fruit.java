package fruit.pojo;

public class Fruit {
    // 与数据库属性名一致
    private Integer id ;
    private String name ;
    private Integer price ;
    private Integer amount ;
    private String commet;

    public Fruit(){}

    public Fruit(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        this.id = fid;
        this.name = fname;
        this.price = price;
        this.amount = fcount;
        this.commet = remark;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCommet() {
        return commet;
    }

    @Override
    public String toString() {
        return id + "\t\t" + name + "\t\t" + price +"\t\t" + amount +"\t\t" + commet ;
    }
}
