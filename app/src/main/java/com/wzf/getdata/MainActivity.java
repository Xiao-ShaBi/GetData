package com.wzf.getdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wzf.getdatalib.ReflashData;
import com.wzf.getdatalib.data.StoreData;
import com.wzf.getdatalib.getfromnew.NetUtils;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.tv_test);
//        getSupportFragmentManager().beginTransaction().add()
//        Glide.with(new Fragment());
    }

    ReflashData reflashData = new ReflashData() {
        @Override
        public boolean refalshOrNot() {
            /**
             * 这里true可以接收到数据的更新，
             * false 只接收一次数据，不再接受之后的更新
             */
            return true;
        }

        @Override
        public void refalsh(Class clazz) {
            DataBean datas = StoreData.getStore().getDatas(DataBean.class, null);
            if (datas != null)
                text.setText(datas.toString());

        }
    };

    /**
     * 取本地数据
     *
     * @param v
     */
    public void getdata(View v) {

        DataBean datas = StoreData.getStore().getDatas(this, DataBean.class, reflashData);

        if (datas != null)
            text.setText(datas.toString());
        else
            text.setText("数据是空的");
    }

    public static final String url = "http://wangyi.butterfly.mopaasapp.com/detail/api?simpleId=";

    public static int count = 8;

    /**
     * 网络请求数据
     *
     * @param v
     */
    public void request(View v) {
        NetUtils.get(url + count, null, DataBean.class, new NetUtils.SimpleJson());

        //每次点击换一个url
        count++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        StoreData.getStore().remove(DataBean.class, reflashData);
        StoreData.getStore().remove(this);
    }
}

