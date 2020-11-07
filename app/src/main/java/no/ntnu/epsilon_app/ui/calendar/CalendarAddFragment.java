package no.ntnu.epsilon_app.ui.calendar;

import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.List;

import no.ntnu.epsilon_app.R;

public class CalendarAddFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(CalendarAddFragment.STYLE_NORMAL, R.style.SomeStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_add, container, false);
        final TimePicker startTimePicker = view.findViewById(R.id.setStartTime);
        startTimePicker.setIs24HourView(true);
        final TimePicker endTimePicker = view.findViewById(R.id.setEndTime);
        endTimePicker.setIs24HourView(true);
        final EditText editAddressText = view.findViewById(R.id.editAdress);
        final Button editAddressButton = view.findViewById(R.id.addressConfirmButton);
        editAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressToLatLong = editAddressText.getText().toString().trim();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder(getContext());


                try {
                    addressList = geocoder.getFromLocationName(addressToLatLong, 1);
                } catch (IOException e) {
                }
                if(addressList != null && addressList.size() != 0) {
                    Address address = addressList.get(0);
                    mMap.clear();
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                }
                else{
                    Toast.makeText(getActivity(),"Addresse ikke funnet",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ImageView nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                setPage(count);
            }
        });
        final ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count -= 1;
                setPage(count);
            }
        });

        final ImageView nextButtonTwo = view.findViewById(R.id.nextButtonTwo);
        nextButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                setPage(count);
            }
        });

        final ImageView backButtonTwo = view.findViewById(R.id.backButtonTwo);
        backButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count -=1;
                setPage(count);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }




    private void setPage(int page) {
        ConstraintLayout addCalendarPageOne = getView().findViewById(R.id.addCalendarPageOne);
        ConstraintLayout addCalendarPageTwo = getView().findViewById(R.id.addCalendarPageTwo);
        ConstraintLayout addCalendarPageThree = getView().findViewById(R.id.addCalendarPageThree);

        switch (page) {
            case 0:
                addCalendarPageOne.setVisibility(View.VISIBLE);
                addCalendarPageTwo.setVisibility(View.GONE);
                addCalendarPageThree.setVisibility(View.GONE);
                break;
            case 1:
                addCalendarPageOne.setVisibility(View.GONE);
                addCalendarPageTwo.setVisibility(View.VISIBLE);
                addCalendarPageThree.setVisibility(View.GONE);
                break;
            case 2:
                addCalendarPageOne.setVisibility(View.GONE);
                addCalendarPageTwo.setVisibility(View.GONE);
                addCalendarPageThree.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ntnuAalesund = new LatLng(62.472117, 6.235394);
        mMap.addMarker(new MarkerOptions().position(ntnuAalesund));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ntnuAalesund, 16f));

    }

}