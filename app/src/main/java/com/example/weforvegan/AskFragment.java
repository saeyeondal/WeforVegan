package com.example.weforvegan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;

public class AskFragment extends Fragment {
    Button submitBtn;
    EditText userEmail;
    EditText askMsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ask_frag, container, false);
        userEmail = (EditText)rootView.findViewById(R.id.user_email);
        askMsg = (EditText)rootView.findViewById(R.id.ask_msg);

        submitBtn = (Button)rootView.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(askMsg.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "문의할 내용을 적어주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(userEmail.getText().toString().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "이메일을 적어주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    PostRequest httpTask = new PostRequest(getActivity().getApplicationContext());
                    try {
                        httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/qna", "content", askMsg.getText().toString(), "mail", userEmail.getText().toString()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "문의 접수가 완료되었습니다. 답변은 이메일 확인해주시길 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
