package base;

public class Item {
    private String name;
    private int currQuantity;
    private int originalQuantity;
    public Item(String name, int originalQuantity) {
        this.name = name;
        this.originalQuantity = originalQuantity;
        this.currQuantity = originalQuantity;
    }

    public void decrementItem() {
        currQuantity--;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrQuantity() {
        return currQuantity;
    }

    public void setCurrQuantity(int currQuantity) {
        this.currQuantity = currQuantity;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    
}