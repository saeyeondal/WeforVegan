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
import java.util.concurrent.ExecutionException;

public class ScannerFrag extends Fragment {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    String response="";
    String pt_name, pt_rwmtr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.scanner_frag, container, false);

        /*
        GetRequest httpTask = new GetRequest(getActivity().getApplicationContext());
        try {
            response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/text1.php?").get();
            JsonParser json_result= new JsonParser();
            System.out.println(response);
            String[] barcode_inform = new String[2]; //pt_name, pt_rwmtr
            barcode_inform = json_result.inform_parse(response);
            pt_name = barcode_inform[0];
            pt_rwmtr = barcode_inform[1];

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
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
