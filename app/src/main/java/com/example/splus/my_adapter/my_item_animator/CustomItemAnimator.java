package com.example.splus.my_adapter.my_item_animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        if (oldHolder == newHolder) {
            dispatchChangeFinished(oldHolder, true);
            return true;
        }
        ObjectAnimator animatorOld = ObjectAnimator.ofFloat(oldHolder.itemView, "alpha", 1f, 0f);
        ObjectAnimator animatorNew = ObjectAnimator.ofFloat(newHolder.itemView, "alpha", 0f, 1f);
        animatorOld.setDuration(300);
        animatorNew.setDuration(300);
        animatorOld.start();
        animatorNew.start();
        animatorOld.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Notify to the RecyclerView that the change has finished
                dispatchChangeFinished(oldHolder, true);
            }
        });
        animatorNew.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchChangeFinished(newHolder, false);
            }
        });
        return true;
    }
}
