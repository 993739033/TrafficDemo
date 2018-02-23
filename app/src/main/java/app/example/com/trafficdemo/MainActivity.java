  package app.example.com.trafficdemo;

  import android.os.Bundle;
  import android.support.v7.app.AppCompatActivity;
  import android.support.v7.widget.LinearLayoutManager;
  import android.support.v7.widget.RecyclerView;

  import java.util.List;

  import app.example.com.trafficdemo.Bean.AppInfoBean;
  import app.example.com.trafficdemo.Recycler.TrafficAdapter;
  import app.example.com.trafficdemo.Utils.AppsName;

  public class MainActivity extends AppCompatActivity {
      RecyclerView recy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      List<AppInfoBean> beans= AppsName.getAllAppNames(this);
        recy = findViewById(R.id.recy);
        TrafficAdapter adapter = new TrafficAdapter(beans);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(adapter);
    }
}
