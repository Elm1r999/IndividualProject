package elmir.vip.individualproject.ui.home.pavilions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.ActivityPavilionBinding

class PavilionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityPavilionBinding>(this, R.layout.activity_pavilion)
    }
}