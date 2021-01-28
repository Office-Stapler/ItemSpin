package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

import base.Category;
import base.Item;
import base.Spinner;


public class JSONUtil {
    private static final String path = "src/base/chances.json";
    private Scanner scanner;
    public JSONUtil() throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        
    }

    public Spinner readJSON() {
        Spinner spinner = new Spinner();
        JSONArray json = getJSONObject();
        for (int i = 0; i < json.length(); ++i) {
            JSONObject jsonObj = json.getJSONObject(i);
            Category category = new Category(
                jsonObj.getString("category_name"),
                jsonObj.getDouble("curr-weighting"),
                jsonObj.getDouble("original-weighting")
            );
            JSONArray array = jsonObj.getJSONArray("items");
            JSONArray usedItems = jsonObj.getJSONArray("usedItems");
            generateItems(false, array, category);
            generateItems(true, usedItems, category);
            spinner.addCategory(category);
        }
        return spinner;
    }

    public void writeJSON(Spinner spinner) throws FileNotFoundException {
        List<Category> categories = spinner.getCategories();
        scanner.close();
        JSONArray json = new JSONArray();
        for (Category category : categories) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("category_name", category.getName());
            jsonObj.put("curr-weighting", category.getWeighting());
            jsonObj.put("original-weighting", category.getOriginalWeighting());
            JSONArray items = new JSONArray();
            JSONArray usedItems = new JSONArray();
            for (Item item : category.getItems()) {
                items.put(generateItemJSON(item, false));
            }
            for (Item item : category.getUsedItems()) {
                usedItems.put(generateItemJSON(item, true));
            }
            jsonObj.put("items", items);
            jsonObj.put("usedItems", usedItems);
            json.put(jsonObj);
        }
        PrintWriter out = new PrintWriter(path);
        out.println(json.toString(4));
        out.close();
    }

    public JSONObject generateItemJSON(Item item, boolean isUsed) {
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("name", item.getName());
        jsonItem.put("original-quant", item.getOriginalQuantity());
        if (!isUsed)
            jsonItem.put("curr-quant", item.getCurrQuantity());
        return jsonItem;
    }

    private void generateItems(boolean isUsed, JSONArray array, Category category) {
        for (int j = 0; j < array.length(); ++j) {
            JSONObject item = array.getJSONObject(j);
            Item newItem = new Item(
                item.getString("name"),
                item.getInt("original-quant")
            );
            newItem.setCurrQuantity(isUsed ? 0 : item.getInt("curr-quant"));
            if (isUsed) {   
                category.addUsedItem(newItem);
            } else {
                category.addItem(newItem);
                
            }
        }
    }

    private JSONArray getJSONObject() {
        String input = "";
        while (scanner.hasNextLine())
            input += scanner.nextLine();
        return new JSONArray(input);
    }

    public void close() {
        scanner.close();
    }
}
