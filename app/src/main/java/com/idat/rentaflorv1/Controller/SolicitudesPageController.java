package com.idat.rentaflorv1.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.idat.rentaflorv1.Fragment.ServicioFinalFragment;
import com.idat.rentaflorv1.Fragment.facturasFragment;
import com.idat.rentaflorv1.Fragment.homeFragment;
import com.idat.rentaflorv1.Fragment.informacionFragment;
import com.idat.rentaflorv1.Fragment.serviciosFragment;
import com.idat.rentaflorv1.Fragment.solicitudesFragment;
import com.idat.rentaflorv1.FragmentDetalle.SolicitudFinalFragment;
import com.idat.rentaflorv1.FragmentDetalle.SolicitudesCanseladaFragment;

public class SolicitudesPageController extends FragmentPagerAdapter {

int numoftabs=0;

    public SolicitudesPageController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        numoftabs=behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        if(numoftabs==2){
            switch (position){
                case 0:
                    return new serviciosFragment();
                case 1:
                    return new ServicioFinalFragment();
                default:
                    return null;
            }
        }else {
            switch (position) {
                case 0:
                    return new solicitudesFragment();
                case 1:
                    return new SolicitudFinalFragment();
                case 2:
                    return new SolicitudesCanseladaFragment();
                default:
                    return null;
            }
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
