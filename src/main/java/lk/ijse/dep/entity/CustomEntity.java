package lk.ijse.dep.entity;

public class CustomEntity extends SuperEntity{
    private String maxId;


    public CustomEntity() {
    }

    public CustomEntity(String maxId) {
        this.maxId = maxId;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }
}
