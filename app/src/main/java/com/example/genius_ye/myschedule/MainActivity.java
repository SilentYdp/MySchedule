package com.example.genius_ye.myschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.genius_ye.myschedule.db.ScheduleDao;
import com.example.genius_ye.myschedule.domain.ScheduleInfo;

import java.util.ArrayList;
import java.util.Map;

/*
1.找到控件
2.模拟操作，先添加假数据进行显示效果展示
3.然后手动增加真实有效的数据
4.将真实的数据写入数据库
5.将数据库上传至服务器
 */
public class MainActivity extends AppCompatActivity {
    private EditText edt_day;
    private EditText edt_name;
    private EditText edt_plan;
    private ListView lv;
    //private ArrayList<ScheduleInfo> list;
    private ScheduleDao dao;
    //private EditText edt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_all:
                dao.deleteAll();
                Toast.makeText(this, "删除全部信息成功", Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
                break;
            case R.id.item_save:
                Toast.makeText(this, "上传至服务器成功", Toast.LENGTH_SHORT).show();
                //上传操作实现逻辑

                break;
             default:
                 break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        setContentView(R.layout.main_activity);
        edt_day=(EditText)findViewById(R.id.et_day);
        edt_name=(EditText)findViewById(R.id.et_name);
        edt_plan=(EditText) findViewById(R.id.et_plan);
        lv=(ListView)findViewById(R.id.lv);
        //list=new ArrayList<>();
        dao=new ScheduleDao(this);
        lv.setAdapter(new MyAdapter());



//        for(int i=0;i<10;i++){
//            ScheduleInfo info=new ScheduleInfo();
//            info.setDay("1月"+i+"日");
//            info.setName("领导"+i);
//            info.setPlan("计划"+i);
//            list.add(info);
//        }

    }

    /*
    实现提交按钮的响应逻辑：手动添加真实数据
     */
    public void add(View view){
        String day=edt_day.getText().toString().trim();
        String name=edt_name.getText().toString().trim();
        String plan=edt_plan.getText().toString().trim();
        if(TextUtils.isEmpty(day)||TextUtils.isEmpty(name)||TextUtils.isEmpty(plan)){
            Toast.makeText(this, "请按格式完善信息", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //在界面显示出来并且同步保存到数据库
            ScheduleInfo info=new ScheduleInfo();
            info.setDay(day);
            info.setName(name);
            info.setPlan(plan);
            boolean result=dao.add(info);
            if(result){
                Toast.makeText(this, "提交至数据库成功", Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
            }
        }

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
           // return list.size();
            return dao.getTotalcount();
        }
        @Override
        public ScheduleInfo getItem(int position) {
            //return list.get(position);
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.show_xml, null);
            TextView tv_day=(TextView)view.findViewById(R.id.tv_day);
            TextView tv_name=(TextView)view.findViewById(R.id.tv_name);
            TextView tv_plan=(TextView)view.findViewById(R.id.tv_plan);
            ImageView iv_dele=(ImageView)view.findViewById(R.id.iv_dele);
            final Map<String,String>map=dao.getinfo(position);
            iv_dele.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // list.remove(position);
                            boolean result=dao.delete(map.get("name"));
                            if(result){
                                Toast.makeText(MainActivity.this, "删除信息成功", Toast.LENGTH_SHORT).show();
                                lv.setAdapter(new MyAdapter());
                            }

                        }
                    });
            /*
            getItem(position)返回的是一个ScheduleInfo对象实例
             */
            tv_day.setText(map.get("date"));
            tv_name.setText(map.get("name"));
            tv_plan.setText(map.get("plan"));
            return view;
        }
    }
}
