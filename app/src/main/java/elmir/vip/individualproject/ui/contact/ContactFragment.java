package elmir.vip.individualproject.ui.contact;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import elmir.vip.individualproject.R;

public class ContactFragment extends Fragment {

    AlertDialog dialog;

    public ContactFragment(){}

    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflater.inflate(R.layout.loading_dialog_alert, null));
        dialog = builder.create();
        dialog.show();

        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        WebView webView = root.findViewById(R.id.webView_contactPage);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.expo2020dubai.com/en/support/contact");

        if (webView.isActivated()){
            dialog.dismiss();
        }
        else{
            dialog.setMessage("Something wrong");
            dialog.dismiss();
        }
        dialog.dismiss();
        return root;
    }
}