package base;

import java.util.ArrayList;

import utils.RandomUtil;

public class Category {
    private double originalWeighting;
    private double weighting;
    private String name;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> usedItems = new ArrayList<>();
    private int currQuantity = 0;
    private int originalQuantity = 0;
    public Category(String name, double weighting, double originalWeighting) {
        this.name = name;
        this.weighting = weighting;
        this.originalWeighting = originalWeighting;

    }

    public void addItem(Item item) {    
        items.add(item);
        currQuantity += item.getCurrQuantity();
        originalQuantity += item.getCurrQuantity();
    }

    public void addUsedItem(Item item) {
        usedItems.add(item);
        originalQuantity += item.getOriginalQuantity();
    }

    /**
     * Chooses a random item in the category,
     * if no items exist, return null.
     */
    public Item chooseRandom() {
        if (items.size() == 0)
            return null; 
        int rand = RandomUtil.getInt(0, items.size());
        Item item = items.get(rand);
        if (item.getCurrQuantity() == 1) {
            items.remove(item);
            usedItems.add(item);
            item.decrementItem();
            return item;
        }
        item.decrementItem();
        return item;
    }

    /**
     * Adjusts the probability assumes one item is selected once from the category
     * 
     * @return the difference in weighting
     */
    public double adjustProbability() {
        if (currQuantity == 0)
            return 0;
        double weightDiff =  weighting / currQuantity;
        currQuantity--;
        weighting -= weightDiff;
        return weightDiff;
    }

    public void displayItems() {
        System.out.println("---------------------");
        System.out.println("Items left - ");
        for (Item item : items) {
            System.out.println("Name: " + item.getName());
            System.out.println("Quantity: " + item.getCurrQuantity());
            System.out.println("Original Quantity: " + item.getOriginalQuantity());
        }
        System.out.println("Items lost - ");
        for (Item item : usedItems) {
            System.out.println("Name: " + item.getName());
            System.out.println("Quantity: " + item.getCurrQuantity());
            System.out.println("Original Quantity: " + item.getOriginalQuantity());
        }
        System.out.println("--------------------");
    }

    public boolean isEmpty() {
        return currQuantity == 0;
    }

    public double getWeighting() {
        return weighting;
    }

    public void setWeighting(double weighting) {
        this.weighting = weighting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getCurrQuantity() {
        return currQuantity;
    }

    public void setCurrQuantity(int currQuantity) {
        this.currQuantity = currQuantity;
    }

    public ArrayList<Item> getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(ArrayList<Item> usedItems) {
        this.usedItems = usedItems;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public double getOriginalWeighting() {
        return originalWeighting;
    }

    public void setOriginalWeighting(double originalWeighting) {
        this.originalWeighting = originalWeighting;
    }

    
}
