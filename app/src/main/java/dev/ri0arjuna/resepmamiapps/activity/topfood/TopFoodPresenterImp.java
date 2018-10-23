package dev.ri0arjuna.resepmamiapps.activity.topfood;

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

import dev.ri0arjuna.resepmamiapps.model.ModelTopFood;

public class TopFoodPresenterImp implements TopFoodPresenter {

    private TopFoodView view;
    private String nameFood, imageFood, recipeFood;
    private int idFood, favoritedFood;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Makanan");
    private List<ModelTopFood> makananList;

    TopFoodPresenterImp(TopFoodView view) {
        this.view = view;
    }

    @Override
    public void loadTopView() {
        view.showLoading();
        final Query asc = mRef.orderByChild("favoritedFood");

        asc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelTopFood value = dataSnapshot1.getValue(ModelTopFood.class);
                    ModelTopFood fire = new ModelTopFood();

                    nameFood = value != null ? value.getNameFood() : null;
                    imageFood = value != null ? value.getImageFood() : null;
                    recipeFood = value != null ? value.getRecipeFood() : null;
                    idFood = value != null ? value.getIdFood() : 0;
                    favoritedFood = value != null ? value.getFavoritedFood() : 0;

                    fire.setNameFood(nameFood);
                    fire.setImageFood(imageFood);
                    fire.setRecipeFood(recipeFood);
                    fire.setIdFood(idFood);
                    fire.setFavoritedFood(favoritedFood);

                    makananList.add(fire);
                    view.showTopFood(makananList);
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
}
