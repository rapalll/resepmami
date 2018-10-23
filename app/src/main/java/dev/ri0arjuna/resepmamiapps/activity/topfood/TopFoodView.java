package dev.ri0arjuna.resepmamiapps.activity.topfood;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.model.ModelTopFood;

public interface TopFoodView {
    void showLoading();
    void hideLoading();
    void showTopFood(List<ModelTopFood> modelTopFoodList);
}
