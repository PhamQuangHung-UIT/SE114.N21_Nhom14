package com.example.splus.my_dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.DialogFragment;

import com.example.splus.R;

public class LoadingDialog extends DialogFragment implements MotionLayout.TransitionListener {

    public static LoadingDialog getInstance() {
        return new LoadingDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MotionLayout mLayout = view.findViewById(R.id.motionLayout_Loading);
        mLayout.setTransitionListener(this);
        setCancelable(false);
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        // ignored
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        // ignored
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.endLoadingScreen)
            motionLayout.setProgress(0);
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        // ignored
    }
}
