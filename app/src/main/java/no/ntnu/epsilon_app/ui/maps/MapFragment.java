package no.ntnu.epsilon_app.ui.maps;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Map;

import no.ntnu.epsilon_app.R;

public class MapFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public MapFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(MapFragment.STYLE_NORMAL,R.style.SomeStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        setStyle(MapFragment.STYLE_NORMAL,R.style.SomeStyle);
        return view;
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
        LatLng ntnuAalesund = new LatLng(62.472117, 6.235394);
        mMap.addMarker(new MarkerOptions()).setPosition(ntnuAalesund);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ntnuAalesund));
        System.out.println("FAEN DA KART");
    }
}
