package no.ntnu.epsilon_app.ui.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import no.ntnu.epsilon_app.R;

public class MapFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public MapFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()!=null){
            SupportMapFragment mapFragment = (SupportMapFragment) getActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if(mapFragment!=null){
                mapFragment.getMapAsync(this);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
