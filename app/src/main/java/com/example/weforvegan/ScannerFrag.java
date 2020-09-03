package com.example.weforvegan;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class ScannerFrag extends Fragment {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.scanner_frag, container, false);

        barcodeScannerView = (DecoratedBarcodeView)rootView.findViewById(R.id.barcode);

        capture = new CaptureManager(getActivity(), barcodeScannerView);
        capture.initializeFromIntent(getActivity().getIntent(), savedInstanceState);
        capture.decode();
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                readBarcode(result.toString());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    public void readBarcode(String barcode){
        ResultFrag.barcodeNumber = barcode;
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.onFragmentChanged(1);
    }
}
