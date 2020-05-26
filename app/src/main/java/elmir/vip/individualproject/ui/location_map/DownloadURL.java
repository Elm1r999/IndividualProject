package elmir.vip.individualproject.ui.location_map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadURL {
    public String readURL(String placeURL) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(placeURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ( (line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            conn.disconnect();
        }
        return data;
    }
}
