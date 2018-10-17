package dev.ri0arjuna.resepmamiapps.activity.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dev.ri0arjuna.resepmamiapps.model.ModelFood;

public class MainPresenterImp implements MainPresenter {

    private MainView view;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Makanan");
    private String nameFood, imageFood, recipeFood;
    private int idFood, favoriteFood;
    private List<ModelFood> modelFoodList;

    MainPresenterImp(MainView view) {
        this.view = view;
    }

    @Override
    public void loadRecipe() {
        view.showLoading();
        final Query asc = mRef.orderByChild("nameFood");
        asc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelFoodList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelFood value = dataSnapshot1.getValue(ModelFood.class);
                    ModelFood fire = new ModelFood();

                    nameFood = value != null ? value.getNameFood() : null;
                    imageFood = value != null ? value.getImageFood() : null;
                    recipeFood = value != null ? value.getRecipeFood() : null;
                    idFood = value != null ? value.getIdFood() : 0;
                    favoriteFood = value != null ? value.getFavoritedFood() : 0;

                    fire.setNameFood(nameFood);
                    fire.setImageFood(imageFood);
                    fire.setRecipeFood(recipeFood);
                    fire.setIdFood(idFood);
                    fire.setFavoritedFood(favoriteFood);

                    modelFoodList.add(fire);
                    view.showFood(modelFoodList);
                }

                view.hideLoading();
                mRef.keepSynced(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase: ", "failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void searchRecipe(String query) {
        final Query firebaseSearchQuery = mRef.orderByChild("nameFood")
                .startAt(query)
                .endAt(query + "\uf8ff");

        firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelFoodList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelFood value = dataSnapshot1.getValue(ModelFood.class);
                    ModelFood fire = new ModelFood();

                    nameFood = value != null ? value.getNameFood() : null;
                    imageFood= value != null ? value.getImageFood() : null;
                    recipeFood= value != null ? value.getRecipeFood() : null;
                    idFood = value != null ? value.getIdFood() : 0;
                    favoriteFood= value != null ? value.getFavoritedFood() : 0;

                    fire.setNameFood(nameFood);
                    fire.setImageFood(imageFood);
                    fire.setRecipeFood(recipeFood);
                    fire.setIdFood(idFood);
                    fire.setFavoritedFood(favoriteFood);

                    modelFoodList.add(fire);
                    view.showSearchFood(modelFoodList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase: ", "failed to read value.", databaseError.toException());
            }
        });
    }
}
