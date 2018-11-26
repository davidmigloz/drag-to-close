package com.davidmiguel.sample;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Example of closing activity both dragging or clicking.
 */
public class Card3Fragment extends Fragment {

    public Card3Fragment() {
        // Required empty constructor
    }
    
    static Card3Fragment newInstance() {
        return new Card3Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_card3, container, false);
    }
}
