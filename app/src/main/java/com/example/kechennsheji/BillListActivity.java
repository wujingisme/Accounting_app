        package com.example.kechennsheji;

        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.ContentValues;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.InputType;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.inputmethod.EditorInfo;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.CursorAdapter;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.SimpleCursorAdapter;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import com.example.kechennsheji.SQLite.DatabaseHelper;
        import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
        import com.example.kechennsheji.User.UserActivity;

        import java.sql.Connection;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import static com.example.kechennsheji.R.*;

public class BillListActivity extends AppCompatActivity {
    private DatabaseHelperPayin helper;
    private DatabaseHelper helper_out;
    private Button jizhang,chakan,fenxibaogao,user;
    private Button zhichu_shouru,msearch;
    private TextView date1, date2;
    private ListView ls;
    private Boolean flag=true;
    private Boolean bolean=true;
    private TextView expense,income,left;
    private TextView tv_sort,tv_money,tv_date;
    int sum_income,sum_expense,sum_left=0;
    private static  final String TAG="test";
    private List<Map<String,Object>>datalist=new ArrayList<Map<String,Object>>();
    private int[] itemIdArr=new int[]{R.id.tv_shouzhi,id.tv_sort,id.tv_jiner,id.tv_riqi};
    private String[]   dataKeyArr=new String[]{"money","datetime","sort","introduce"};
    SQLiteDatabase db;
    SimpleAdapter simpleAdapter;
    /*SimpleAdapter的参数说明
     * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
     * 第二个参数表示生成一个Map(String ,Object)列表选项
     * 第三个参数表示界面布局的id 表示该文件作为列表项的组件
     * 第四个参数表示该Map对象的哪些key对应value来生成列表项
     * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
     * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充 因为 找不到key也会返回null 其实就相当于给了一个null资源
     * 下面的程序中如果 new String[] { "name", "head", "desc","name" } new int[] {R.id.name,R.id.head,R.id.desc,R.id.head}
     * 这个head的组件会被name资源覆盖
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_billlist);
        initview();
        setclicklinster();
        helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
        db=helper.getReadableDatabase();
        db.delete("table_payin","sort=?",new String[]{""});
        db.delete("table_payin","money=?",new String[]{""});
        ls=findViewById(R.id.List_1);

        simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
        ls.setAdapter(simpleAdapter);



        zhichu_shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==true)
                {
                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    db.delete("table_payin","money=?",new String[]{""});
                    ls=findViewById(R.id.List_1);
                    final Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, null);
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                     simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                       ls.setAdapter(simpleAdapter);
                        final Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);

                    }
                    //点击修改收入数据内容
                  ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                              final int location=position;
                              final String[] items = {"金额","日期(格式为YY-MM-DD)","分类","备注"};
                              final AlertDialog.Builder builder = new AlertDialog.Builder(BillListActivity.this);
                              builder .setTitle("你想修改这条数据什么内容？");
                              builder.setItems(items, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                      dialogInterface.dismiss();
                                      simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                                      ls.setAdapter(simpleAdapter);
                                      helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                                       final SQLiteDatabase db=helper.getReadableDatabase();
                                      final Cursor cursor;
                                      cursor = db.query("table_payin", null, null, null, null,null, null);
                                      final EditText editText = new EditText(BillListActivity.this);
                                      final AlertDialog.Builder builder = new AlertDialog.Builder(BillListActivity.this);
                                      switch (i){
                                          case 0:
                                              builder.setTitle("将金额修改为：");
                                              builder.setIcon(R.mipmap.ic_launcher);
                                              builder.setView(editText);
                                              builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                              { @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                   String delete_sort=datalist.get(location).get("sort").toString();
                                                  String delete_date=datalist.get(location).get("datetime").toString();
                                                  datalist.clear();
                                                  editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                                                  ContentValues values=new ContentValues();
                                                  values.put("money",editText.getText().toString());
                                                  db.update("table_payin",values, "datetime=? and sort=?",new String[]{delete_sort,delete_date});
                                                  while (cursor.moveToNext()) {
                                                      int money = cursor.getInt(0);
                                                      String datetime = cursor.getString(1);
                                                      String sort = cursor.getString(2);
                                                      String introduce= cursor.getString(3);
                                                      final Map<String,Object> map;
                                                      map=new HashMap<String,Object>();
                                                      map.put("money",money);
                                                      map.put("datetime",datetime);
                                                      map.put("sort",sort);
                                                      map.put("introduce",introduce);
                                                      datalist.add(map);
                                                      simpleAdapter.notifyDataSetChanged();
                                                      QTotal_expense();
                                                      QTotal_income();
                                                      QTotal_left();
                                                      income.setText(":"+String.valueOf(sum_income)+" 元");
                                                      expense.setText(":"+String.valueOf(sum_expense)+" 元");
                                                  }


                                              } });
                                              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                  @Override public void onClick(DialogInterface dialog, int which)
                                                  { dialog.cancel(); } });
                                              builder.create().show();
                                              break;
                                          case 1:
                                              builder.setTitle("将日期修改为：");
                                              builder.setIcon(R.mipmap.ic_launcher);
                                              builder.setView(editText);
                                              builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                              { @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  //找出当前位置的日期
                                                  String delete_money=datalist.get(location).get("money").toString();
                                                  String delete_sort=datalist.get(location).get("sort").toString();
                                                  String delete_date=datalist.get(location).get("datetime").toString();
                                                  datalist.clear();
                                                  ContentValues values=new ContentValues();
                                                  editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                                                  values.put("datetime",editText.getText().toString());
                                                  db.update("table_payin",values,"datetime=? and money=? and sort=?",new String[]{delete_sort,delete_money,delete_date});
                                                  while (cursor.moveToNext()) {
                                                      int money = cursor.getInt(0);
                                                      String datetime = cursor.getString(1);
                                                      String sort = cursor.getString(2);
                                                      String introduce= cursor.getString(3);
                                                      final Map<String,Object> map;
                                                      map=new HashMap<String,Object>();
                                                      map.put("money",money);
                                                      map.put("datetime",datetime);
                                                      map.put("sort",sort);
                                                      map.put("introduce",introduce);
                                                      datalist.add(map);
                                                      simpleAdapter.notifyDataSetChanged();
                                                  }

                                              } });
                                              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                  @Override public void onClick(DialogInterface dialog, int which)
                                                  { dialog.cancel(); } });
                                              builder.create().show();
                                              break;
                                          case 2:
                                              final String[] items = {"兼职","转账","奖金","投资","其他"};
                                             // final AlertDialog.Builder builder = new AlertDialog.Builder(BillListActivity.this);
                                              builder.setTitle("将收入类别修改为：");
                                              builder.setItems(items, new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialogInterface, int i) {
                                                      dialogInterface.dismiss();
                                                      ContentValues values=new ContentValues();
                                                      String delete_time;
                                                      String delete_money;
                                                      String delete_sort;
                                                      switch (i)
                                                      {
                                                          case 0:
                                                              delete_money=datalist.get(location).get("money").toString();
                                                              delete_sort=datalist.get(location).get("sort").toString();
                                                              delete_time=datalist.get(location).get("datetime").toString();
                                                              datalist.clear();
                                                              //ContentValues values=new ContentValues();
                                                              values.put("sort",items[0]);
                                                              db.update("table_payin",values,"sort=? and money=? and datetime=?",new String[]{delete_time,delete_money,delete_sort});
                                                              while (cursor.moveToNext()) {
                                                                  int money = cursor.getInt(0);
                                                                  String datetime = cursor.getString(1);
                                                                  String sort = cursor.getString(2);
                                                                  String introduce= cursor.getString(3);
                                                                  final Map<String,Object> map;
                                                                  map=new HashMap<String,Object>();
                                                                  map.put("money",money);
                                                                  map.put("datetime",datetime);
                                                                  map.put("sort",sort);
                                                                  map.put("introduce",introduce);
                                                                  datalist.add(map);
                                                                  simpleAdapter.notifyDataSetChanged();
                                                              }
                                                              break;
                                                          case 1:
                                                               delete_money=datalist.get(location).get("money").toString();
                                                               delete_sort=datalist.get(location).get("sort").toString();
                                                               delete_time=datalist.get(location).get("datetime").toString();
                                                              datalist.clear();
                                                              //ContentValues values=new ContentValues();
                                                              values.put("sort",items[1]);
                                                              db.update("table_payin",values,"sort=? and money=? and datetime=?",new String[]{delete_time,delete_money,delete_sort});
                                                              while (cursor.moveToNext()) {
                                                                  int money = cursor.getInt(0);
                                                                  String datetime = cursor.getString(1);
                                                                  String sort = cursor.getString(2);
                                                                  String introduce= cursor.getString(3);
                                                                  final Map<String,Object> map;
                                                                  map=new HashMap<String,Object>();
                                                                  map.put("money",money);
                                                                  map.put("datetime",datetime);
                                                                  map.put("sort",sort);
                                                                  map.put("introduce",introduce);
                                                                  datalist.add(map);
                                                                  simpleAdapter.notifyDataSetChanged();
                                                              }
                                                              break;
                                                          case 2:
                                                              delete_money=datalist.get(location).get("money").toString();
                                                              delete_sort=datalist.get(location).get("sort").toString();
                                                              delete_time=datalist.get(location).get("datetime").toString();
                                                              datalist.clear();
                                                              //ContentValues values=new ContentValues();
                                                              values.put("sort",items[2]);
                                                              db.update("table_payin",values,"sort=? and money=? and datetime=?",new String[]{delete_time,delete_money,delete_sort});
                                                              while (cursor.moveToNext()) {
                                                                  int money = cursor.getInt(0);
                                                                  String datetime = cursor.getString(1);
                                                                  String sort = cursor.getString(2);
                                                                  String introduce= cursor.getString(3);
                                                                  final Map<String,Object> map;
                                                                  map=new HashMap<String,Object>();
                                                                  map.put("money",money);
                                                                  map.put("datetime",datetime);
                                                                  map.put("sort",sort);
                                                                  map.put("introduce",introduce);
                                                                  datalist.add(map);
                                                                  simpleAdapter.notifyDataSetChanged();
                                                              }
                                                              break;
                                                          case 3:
                                                              delete_money=datalist.get(location).get("money").toString();
                                                              delete_sort=datalist.get(location).get("sort").toString();
                                                              delete_time=datalist.get(location).get("datetime").toString();
                                                              datalist.clear();
                                                              //ContentValues values=new ContentValues();
                                                              values.put("sort",items[3]);
                                                              db.update("table_payin",values,"sort=? and money=? and datetime=?",new String[]{delete_time,delete_money,delete_sort});
                                                              while (cursor.moveToNext()) {
                                                                  int money = cursor.getInt(0);
                                                                  String datetime = cursor.getString(1);
                                                                  String sort = cursor.getString(2);
                                                                  String introduce= cursor.getString(3);
                                                                  final Map<String,Object> map;
                                                                  map=new HashMap<String,Object>();
                                                                  map.put("money",money);
                                                                  map.put("datetime",datetime);
                                                                  map.put("sort",sort);
                                                                  map.put("introduce",introduce);
                                                                  datalist.add(map);
                                                                  simpleAdapter.notifyDataSetChanged();
                                                              }
                                                              break;
                                                          case 4:
                                                              delete_money=datalist.get(location).get("money").toString();
                                                              delete_sort=datalist.get(location).get("sort").toString();
                                                              delete_time=datalist.get(location).get("datetime").toString();
                                                              datalist.clear();
                                                              //ContentValues values=new ContentValues();
                                                              values.put("sort",items[4]);
                                                              db.update("table_payin",values,"sort=? and money=? and datetime=?",new String[]{delete_time,delete_money,delete_sort});
                                                              while (cursor.moveToNext()) {
                                                                  int money = cursor.getInt(0);
                                                                  String datetime = cursor.getString(1);
                                                                  String sort = cursor.getString(2);
                                                                  String introduce= cursor.getString(3);
                                                                  final Map<String,Object> map;
                                                                  map=new HashMap<String,Object>();
                                                                  map.put("money",money);
                                                                  map.put("datetime",datetime);
                                                                  map.put("sort",sort);
                                                                  map.put("introduce",introduce);
                                                                  datalist.add(map);
                                                                  simpleAdapter.notifyDataSetChanged();
                                                              }
                                                              break;
                                                      }

                                                  }
                                              });
                                              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                  @Override public void onClick(DialogInterface dialog, int which)
                                                  { dialog.cancel(); } });
                                              builder.create().show();
                                              break;
                                          case 3:
                                              builder.setTitle("将备注修改为：");
                                              builder.setIcon(R.mipmap.ic_launcher);
                                              builder.setView(editText);
                                              builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                              { @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  //找出当前位置的备注
                                                  String delete_introduce=datalist.get(location).get("introduce").toString();
                                                   String delete_money=datalist.get(location).get("money").toString();
                                                  String delete_sort=datalist.get(location).get("sort").toString();
                                                  datalist.clear();
                                                  ContentValues values=new ContentValues();
                                                  values.put("introduce",editText.getText().toString());
                                                  db.update("table_payin",values,"introduce=? and money=? and datetime=?",new String[]{delete_introduce,delete_money,delete_sort});
                                                  while (cursor.moveToNext()) {
                                                      int money = cursor.getInt(0);
                                                      String datetime = cursor.getString(1);
                                                      String sort = cursor.getString(2);
                                                      String introduce= cursor.getString(3);
                                                      final Map<String,Object> map;
                                                      map=new HashMap<String,Object>();
                                                      map.put("money",money);
                                                      map.put("datetime",datetime);
                                                      map.put("sort",sort);
                                                      map.put("introduce",introduce);
                                                      datalist.add(map);
                                                      simpleAdapter.notifyDataSetChanged();
                                                  }

                                              } });
                                              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                  @Override public void onClick(DialogInterface dialog, int which)
                                                  { dialog.cancel(); } });
                                              builder.create().show();
                                              break;
                                      }
                                  }
                              });

                              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                  @Override public void onClick(DialogInterface dialog, int which)
                                  { dialog.cancel(); } });
                              builder.create().show();

                      }
                  });


                 ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            final int location=position;
                            simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                            ls.setAdapter(simpleAdapter);
                            new AlertDialog.Builder(BillListActivity.this)
                                    .setTitle("警告")
                                    .setMessage("你想删除这条数据吗")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                          helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                                          SQLiteDatabase db=helper.getReadableDatabase();
                                          Cursor cursor;
                                          cursor = db.query("table_payin", null, null, null, null, null,null);
                                          String delete_money=datalist.get(location).get("money").toString();
                                          String delete_date=datalist.get(location).get("datetime").toString();
                                          String delete_sort=datalist.get(location).get("sort").toString();
                                          while (cursor.moveToNext()) {
                                           int money = cursor.getInt(0);
                                           String datetime=cursor.getString(1);
                                           String sort=cursor.getString(2);
                                           if( String.valueOf(money).equals(delete_money)&&datetime.equals(delete_date)&&sort.equals(delete_sort))
                                           {   db.delete("table_payin","datetime=? and money=? and sort=?",new String[]{delete_sort,delete_money,delete_date});
                                               db.close();
                                               datalist.remove(location);
                                               simpleAdapter.notifyDataSetChanged();
                                                    QTotal_expense();
                                                    QTotal_income();
                                                    QTotal_left();
                                                    income.setText(":"+String.valueOf(sum_income)+" 元");
                                                    expense.setText(":"+String.valueOf(sum_expense)+" 元");

                                                }

                                            }


                                        }
                                    })
                                    .setNegativeButton("否",null)
                                    .show();
                            return true;
                        }
                    });
                    QTotal_expense();
                    QTotal_income();
                    QTotal_left();
                    income.setText(":"+String.valueOf(sum_income)+" 元");
                    expense.setText(":"+String.valueOf(sum_expense)+" 元");

                    tv_money_income_Onclick();
                    tv_date_income_Onclick();
                    tv_sort_income_Onclick();
                    income_search_onclick();
                    zhichu_shouru.setText("当前为收入");
                    flag=false;
                    //income.setText(":"+String.valueOf(sum_income)+" 元");

                }
                else{

                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    db.delete("table_payout","money=?",new String[]{""});
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null, null, "money desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);


                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            final int location=position;
                            simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                            ls.setAdapter(simpleAdapter);
                            new AlertDialog.Builder(BillListActivity.this)
                                    .setTitle("警告")
                                    .setMessage("你想删除这条数据吗")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                                            SQLiteDatabase db=helper_out.getReadableDatabase();
                                            Cursor cursor;
                                            cursor = db.query("table_payout", null, null, null, null, null,null);
                                            String delete_money=datalist.get(location).get("money").toString();
                                            String delete_date=datalist.get(location).get("datetime").toString();
                                            String delete_sort=datalist.get(location).get("sort").toString();
                                            while (cursor.moveToNext()) {

                                                int money = cursor.getInt(0);
                                                String datetime=cursor.getString(1);
                                                String sort=cursor.getString(2);
                                                if( String.valueOf(money).equals(delete_money)&&datetime.equals(delete_date)&&sort.equals(delete_sort))
                                                {
                                                    db.delete("table_payout","datetime=? and money=? and sort=?",new String[]{delete_sort,delete_money,delete_date});
                                                    db.close();
                                                    Log.d(TAG,String.valueOf(delete_money));
                                                    datalist.remove(location);
                                                    simpleAdapter.notifyDataSetChanged();
                                                    QTotal_expense();
                                                    QTotal_income();
                                                    QTotal_left();
                                                    income.setText(":"+String.valueOf(sum_income)+" 元");
                                                    expense.setText(":"+String.valueOf(sum_expense)+" 元");

                                                }

                                            }


                                        }
                                    })
                                    .setNegativeButton("否",null)
                                    .show();
                            return true;
                        }
                    });
                    //点击修改支出数据内容
                    ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int location=position;
                            final String[] items = {"金额","日期(格式为YY-MM-DD)","分类","备注"};
                            final AlertDialog.Builder builder = new AlertDialog.Builder(BillListActivity.this);
                            builder .setTitle("你想修改这条数据什么内容？");
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                                    ls.setAdapter(simpleAdapter);
                                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                                    final  SQLiteDatabase db=helper_out.getReadableDatabase();
                                    final Cursor cursor;
                                    cursor = db.query("table_payout", null, null, null, null,null, null);
                                    final EditText editText = new EditText(BillListActivity.this);

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(BillListActivity.this);
                                    switch (i){
                                        case 0:
                                            builder.setTitle("将金额修改为：");
                                            builder.setIcon(R.mipmap.ic_launcher);
                                            builder.setView(editText);
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                            { @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //找出当前位置的金额
                                                 String delete_money=datalist.get(location).get("money").toString();
                                                 String delete_sort=datalist.get(location).get("sort").toString();
                                                String delete_time=datalist.get(location).get("datetime").toString();
                                                 editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                                                datalist.clear();
                                                ContentValues values=new ContentValues();
                                                values.put("money",editText.getText().toString());
                                                db.update("table_payout",values,"datetime=? and sort=?",new String[]{delete_sort,delete_time});
                                                while (cursor.moveToNext()) {
                                                    int money = cursor.getInt(0);
                                                    String datetime = cursor.getString(1);
                                                    String sort = cursor.getString(2);
                                                    String introduce= cursor.getString(3);
                                                    final Map<String,Object> map;
                                                    map=new HashMap<String,Object>();
                                                    map.put("money",money);
                                                    map.put("datetime",datetime);
                                                    map.put("sort",sort);
                                                    map.put("introduce",introduce);
                                                    datalist.add(map);
                                                    simpleAdapter.notifyDataSetChanged();
                                                    QTotal_expense();
                                                    QTotal_income();
                                                    QTotal_left();
                                                    income.setText(":"+String.valueOf(sum_income)+" 元");
                                                    expense.setText(":"+String.valueOf(sum_expense)+" 元");

                                                }


                                            } });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which)
                                                { dialog.cancel(); } });
                                            builder.create().show();
                                            break;
                                        case 1:
                                            builder.setTitle("将日期修改为：");
                                            builder.setIcon(R.mipmap.ic_launcher);
                                            builder.setView(editText);
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                            { @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //找出当前位置的日期
                                                String delete_money=datalist.get(location).get("money").toString();
                                                String delete_sort=datalist.get(location).get("sort").toString();
                                                String delete_time=datalist.get(location).get("datetime").toString();
                                                datalist.clear();
                                                ContentValues values=new ContentValues();
                                                values.put("datetime",editText.getText().toString());
                                                db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                while (cursor.moveToNext()) {
                                                    int money = cursor.getInt(0);
                                                    String datetime = cursor.getString(1);
                                                    String sort = cursor.getString(2);
                                                    String introduce= cursor.getString(3);
                                                    final Map<String,Object> map;
                                                    map=new HashMap<String,Object>();
                                                    map.put("money",money);
                                                    map.put("datetime",datetime);
                                                    map.put("sort",sort);
                                                    map.put("introduce",introduce);
                                                    datalist.add(map);
                                                    simpleAdapter.notifyDataSetChanged();
                                                }

                                            } });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which)
                                                { dialog.cancel(); } });
                                            builder.create().show();
                                            break;
                                        case 2:
                                            final String[] items = {"交通","吃饭","医疗","娱乐","教育","生活用品","其他"};
                                            builder.setTitle("将支出类别修改为：");
                                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    ContentValues values=new ContentValues();
                                                    String delete_time;
                                                    String delete_money;
                                                    String delete_sort;
                                                    switch (i)
                                                    {
                                                        case 0:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            values.put("sort",items[0]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                        case 1:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[1]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[1]);
                                                            db.update("table_payout",values,"sort=?",new String[]{delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                        case 2:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[2]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                        case 3:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[3]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                        case 4:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[4]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                        case 5:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[5]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                        while (cursor.moveToNext()) {
                                                            int money = cursor.getInt(0);
                                                            String datetime = cursor.getString(1);
                                                            String sort = cursor.getString(2);
                                                            String introduce= cursor.getString(3);
                                                            final Map<String,Object> map;
                                                            map=new HashMap<String,Object>();
                                                            map.put("money",money);
                                                            map.put("datetime",datetime);
                                                            map.put("sort",sort);
                                                            map.put("introduce",introduce);
                                                            datalist.add(map);
                                                            simpleAdapter.notifyDataSetChanged();
                                                        }
                                                        break;
                                                        case 6:
                                                            delete_money=datalist.get(location).get("money").toString();
                                                            delete_sort=datalist.get(location).get("sort").toString();
                                                            delete_time=datalist.get(location).get("datetime").toString();
                                                            datalist.clear();
                                                            //ContentValues values=new ContentValues();
                                                            values.put("sort",items[6]);
                                                            db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});
                                                            while (cursor.moveToNext()) {
                                                                int money = cursor.getInt(0);
                                                                String datetime = cursor.getString(1);
                                                                String sort = cursor.getString(2);
                                                                String introduce= cursor.getString(3);
                                                                final Map<String,Object> map;
                                                                map=new HashMap<String,Object>();
                                                                map.put("money",money);
                                                                map.put("datetime",datetime);
                                                                map.put("sort",sort);
                                                                map.put("introduce",introduce);
                                                                datalist.add(map);
                                                                simpleAdapter.notifyDataSetChanged();
                                                            }
                                                            break;
                                                    }

                                                }
                                            });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which)
                                                { dialog.cancel(); } });
                                            builder.create().show();
                                            break;
                                        case 3:
                                            builder.setTitle("将备注修改为：");
                                            builder.setIcon(R.mipmap.ic_launcher);
                                            builder.setView(editText);
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                            { @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //找出当前位置的备注
                                                String delete_introduce=datalist.get(location).get("introduce").toString();
                                                String delete_money=datalist.get(location).get("money").toString();
                                                String delete_sort=datalist.get(location).get("sort").toString();
                                                String delete_time=datalist.get(location).get("datetime").toString();
                                                datalist.clear();
                                                ContentValues values=new ContentValues();
                                                values.put("introduce",editText.getText().toString());
                                                db.update("table_payout",values,"money=? and datetime=? and sort=?",new String[]{delete_money,delete_sort,delete_time});;
                                                while (cursor.moveToNext()) {
                                                    int money = cursor.getInt(0);
                                                    String datetime = cursor.getString(1);
                                                    String sort = cursor.getString(2);
                                                    String introduce= cursor.getString(3);
                                                    final Map<String,Object> map;
                                                    map=new HashMap<String,Object>();
                                                    map.put("money",money);
                                                    map.put("datetime",datetime);
                                                    map.put("sort",sort);
                                                    map.put("introduce",introduce);
                                                    datalist.add(map);
                                                    simpleAdapter.notifyDataSetChanged();
                                                }

                                            } });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which)
                                                { dialog.cancel(); } });
                                            builder.create().show();
                                            break;
                                    }
                                }
                            });

                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which)
                                { dialog.cancel(); } });
                            builder.create().show();

                        }
                    });



                    tv_money_expense_Onclick();
                    tv_date_expense_Onclick();
                    tv_sort_expense_Onclick();
                    expense_search_onclick();
                    zhichu_shouru.setText("当前为支出");
                    flag=true;
                    QTotal_expense();
                    QTotal_income();
                    QTotal_left();
                    expense.setText(":"+String.valueOf(sum_expense)+" 元");
                }

            }
        });

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg1();
            }
        });


    }


    public void setclicklinster() {
        onClick onclick = new onClick();
        chakan.setOnClickListener(onclick);
        fenxibaogao.setOnClickListener(onclick);
        user.setOnClickListener(onclick);
        jizhang.setOnClickListener(onclick);

    }
    private class onClick implements View.OnClickListener{
        public void onClick(View v)
        {
            Intent intent=null;
            switch (v.getId())
            {
                case R.id.btn_yonghuzhongxin:
                    intent=new Intent(BillListActivity.this,UserActivity.class);
                    break;
                case R.id.btn_chakanzhangdan:
                    intent=new Intent(BillListActivity.this,BillListActivity.class);
                    break;
                case R.id.btn_fenxibaogao:
                    intent=new Intent(BillListActivity.this,AnalysisChartActivity.class);
                    break;
                case R.id.btn_jizhang:
                    intent=new Intent(BillListActivity.this,MainPayoutActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    public  void   initview() {
        user = findViewById(R.id.btn_yonghuzhongxin);
        fenxibaogao = findViewById(R.id.btn_fenxibaogao);
        jizhang = findViewById(R.id.btn_jizhang);
        chakan=findViewById(R.id.btn_chakanzhangdan);
        date1 = findViewById(R.id.Et_date1);
        date2 = findViewById(R.id.Et_date2);
        zhichu_shouru=findViewById(id.zhichuorsouru);
        expense=findViewById(R.id.total_expense2);
        income=findViewById(id.total_income2);
        left=findViewById(id.the_left2);
        tv_money=findViewById(R.id._shouzhi);
        tv_sort=findViewById(id._sort);
        tv_date=findViewById(id._jiner);

    }

    public void QTotal_expense()
    {
        helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
        SQLiteDatabase db=helper_out.getReadableDatabase();
        Cursor cursor=db.rawQuery("select sum(money) from table_payout",null);
        if (cursor.moveToFirst())
        {
            do
            {
                sum_expense=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();


    }
    public  void QTotal_income()
    {
        helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select sum(money) from table_payin",null);

        if (cursor.moveToFirst())
        {
            do
            {
                sum_income=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public  void  QTotal_left(){
        sum_left=sum_income-sum_expense;
        left.setText(String.valueOf(sum_left)+" 元");
    }
    public void tv_money_expense_Onclick()
    {
        tv_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {   datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor = db.query("table_payout", null, null, null, null,null, "money desc");
                    while (cursor.moveToNext()) {
                        SimpleAdapter simpleAdapter;
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);
                        ls=findViewById(R.id.List_1);
                        simpleAdapter =new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_date.setText("日期");
                    tv_money.setText("金额(desc)");
                    tv_sort.setText("类别");
                }
                else
                {

                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "money asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_money.setText("金额(asc)");
                    tv_date.setText("日期");
                    tv_sort.setText("类别");

                }
            }
        });

    }
    public void tv_money_income_Onclick()
    {
        tv_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "money desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_money.setText("金额(desc)");
                    tv_date.setText("日期");
                    tv_sort.setText("类别");
                }
                else
                {

                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "money asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_money.setText("金额(asc)");
                    tv_date.setText("日期");
                    tv_sort.setText("类别");
                }
            }
        });

    }
    public void tv_date_expense_Onclick()
    {
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "datetime desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_date.setText("日期(desc)");
                    tv_money.setText("金额");
                    tv_sort.setText("类别");
                }
                else
                {
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "datetime asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_date.setText("日期(asc)");
                    tv_money.setText("金额");
                    tv_sort.setText("类别");
                }
            }
        });

    }
    public void tv_date_income_Onclick()
    {
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "datetime desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_date.setText("日期(desc)");
                    tv_money.setText("金额");
                    tv_sort.setText("类别");
                }
                else
                {

                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "datetime asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_date.setText("日期(asc)");
                    tv_money.setText("金额");
                    tv_sort.setText("类别");
                }
            }
        });

    }
    public void tv_sort_expense_Onclick()
    {
        tv_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "sort desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_date.setText("日期");
                    tv_money.setText("金额");
                    tv_sort.setText("类别(desc)");
                }
                else
                {
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "sort asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_date.setText("日期");
                    tv_money.setText("金额");
                    tv_sort.setText("类别(asc)");
                }
            }
        });

    }
    public void tv_sort_income_Onclick()
    {
        tv_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "sort desc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=false;
                    tv_date.setText("日期");
                    tv_money.setText("金额");
                    tv_sort.setText("类别(desc)");
                }
                else
                {

                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "sort asc");
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    flag=true;
                    tv_date.setText("日期");
                    tv_money.setText("金额");
                    tv_sort.setText("类别(asc)");
                }
            }
        });

    }

   protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(BillListActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BillListActivity.this.date1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void income_search_onclick()
    {
        msearch=findViewById(R.id.search);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                datalist.clear();
                String start_date=date1.getText().toString();
                String end_date=date2.getText().toString();
                helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                SQLiteDatabase db=helper.getReadableDatabase();
                Cursor cursor = db.query("table_payin", null, "datetime between ? and ?",new String[]{start_date,end_date}, null, null,null);
                while (cursor.moveToNext()) {
                    int money = cursor.getInt(0);
                    String datetime = cursor.getString(1);
                    String sort = cursor.getString(2);
                    String introduce= cursor.getString(3);
                    Log.d(TAG,end_date);
                    ls=findViewById(R.id.List_1);
                    SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                    ls.setAdapter(simpleAdapter);
                    Map<String,Object> map;
                    map=new HashMap<String,Object>();
                    map.put("money",money);
                    map.put("datetime",datetime);
                    map.put("sort",sort);
                    map.put("introduce",introduce);
                    datalist.add(map);
                }

            }
        });
    }
    public void expense_search_onclick()
    {

        msearch=findViewById(R.id.search);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datalist.clear();
                String start_date=date1.getText().toString();
                String end_date=date2.getText().toString();
                Log.d(TAG,start_date);
                helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                SQLiteDatabase db=helper_out.getReadableDatabase();
                Cursor cursor;
                //  cursor=db.query("table_payout",null,"money between ? and ?",new String[]{"12","23"},null,null,null);
                cursor = db.query("table_payout", null, "datetime between ? and ?",new String[]{start_date,end_date}, null, null,null);
                while (cursor.moveToNext()) {
                    int money = cursor.getInt(0);
                    String datetime = cursor.getString(1);
                    String sort = cursor.getString(2);
                    String introduce= cursor.getString(3);
                    Log.d(TAG,end_date);
                    ls=findViewById(R.id.List_1);
                    SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                    ls.setAdapter(simpleAdapter);
                    Map<String,Object> map;
                    map=new HashMap<String,Object>();
                    map.put("money",money);
                    map.put("datetime",datetime);
                    map.put("sort",sort);
                    map.put("introduce",introduce);
                    datalist.add(map);
                }

            }
        });
    }
    protected void showDatePickDlg1() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(BillListActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BillListActivity.this.date2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.show();
    }
}

