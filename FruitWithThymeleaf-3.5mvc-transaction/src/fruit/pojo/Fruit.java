package fruit.pojo;

public class Fruit {
    // 与数据库属性名一致
    private Integer id ;
    private String name ;
    private Integer price ;
    private Integer amount ;
    private String comment;

    public Fruit(){}

    public Fruit(Integer id, String name, Integer price, Integer amount, String comment) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return id + "\t\t" + name + "\t\t" + price +"\t\t" + amount +"\t\t" + comment ;
    }
}
