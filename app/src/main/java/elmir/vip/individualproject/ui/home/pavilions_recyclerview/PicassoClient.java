package elmir.vip.individualproject.ui.home.pavilions_recyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import elmir.vip.individualproject.R;

public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).fit().centerInside().placeholder(R.drawable.expo_logo).into(img);

        }
        else {
            Picasso.with(c).load(R.mipmap.ic_launcher).noPlaceholder().into(img);
        }
    }
}
