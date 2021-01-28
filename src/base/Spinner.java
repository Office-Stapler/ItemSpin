package base;

import java.util.ArrayList;
import java.util.HashMap;

import utils.RandomUtil;

public class Spinner {
    private ArrayList<Category> categories = new ArrayList<>();
    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addItem(String categoryTarget, Item item) {
        boolean foundCategory = false;
        for (Category category : categories) {
            if (category.getName().equals(categoryTarget)) {
                category.addItem(item);
                foundCategory = true;
                break;
            }
        }
        if (!foundCategory) {
            System.out.println("No such category found.");
        }
    }

    // Spins and returns a random item depending on the give probability.
    public Item spin() {
        if (categories.size() == 0)
            return null;
        double rand = RandomUtil.getDouble();
        double cumulativeWeighting = 0;
        Category lostCategory = null;
        boolean emptyCategory = false;
        for (Category category : categories) {
            if (category.isEmpty()) {
                lostCategory = category;
                emptyCategory = true;
                break;
            }
            cumulativeWeighting += category.getWeighting() / 100;
            if (rand < cumulativeWeighting) {
                Item returnItem = category.chooseRandom();
                double diff = category.adjustProbability();
                adjustProbability(category, diff);
                return returnItem;
            } 
        }
        if (emptyCategory) {
            categories.remove(lostCategory);
            return spin();
        }
        return null;
    }
    
    public void adjustProbability(Category target, double diff) {
        int count = (categories.size() - (target.isEmpty() ? 0 : 1) - countEmpty());
        if (count == 0) {
            target.setWeighting(100.0);
        }
        double spread = diff / count;
        
        for (Category category : categories) {
            if (target == category || category.isEmpty())
                continue;
            category.setWeighting(category.getWeighting() + spread);
        }
    }

    public void testSpinAlgo() {
        HashMap<String, Integer> categoryChosen = new HashMap<>();
        HashMap<String, Integer> averages = new HashMap<>();
        for (int k = 0; k < 100; k++) {
            for (int i = 0; i < 100; ++i) {
                double rand = RandomUtil.getDouble();
                double cumulativeWeighting = 0;
                for (Category category : categories) {
                    cumulativeWeighting += category.getWeighting() / 100;
                    if (rand < cumulativeWeighting) {
                        String name = category.getName();
                        if (!categoryChosen.containsKey(name)) {
                            categoryChosen.put(name, 0);
                        }
                        int count = categoryChosen.get(name);
                        categoryChosen.put(name, count + 1);
                        break;
                    }
                }
            }
            for (String key : categoryChosen.keySet()) {
                if (!averages.containsKey(key)) {
                    averages.put(key, categoryChosen.get(key));
                } else {
                    int countOcuurances = averages.get(key);
                    averages.put(key, countOcuurances + categoryChosen.get(key));
                }
            }
            categoryChosen.clear();
        }
        for (String average : averages.keySet()) {
            System.out.println(String.format("%s averaged: %d times", average, averages.get(average) / 100));
        }
    }

    

    private int countEmpty() {
        int empty = 0;
        for (Category category : categories) {
            if (category.isEmpty())
                empty++;
        }
        return empty;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

}
