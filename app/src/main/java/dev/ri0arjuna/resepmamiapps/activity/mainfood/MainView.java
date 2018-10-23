package dev.ri0arjuna.resepmamiapps.activity.mainfood;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.model.ModelFood;

public interface MainView {
    void showLoading();
    void hideLoading();
    void showFood(List<ModelFood> modelFoodList);
    void showSearchFood(List<ModelFood> modelFoodList);
}
