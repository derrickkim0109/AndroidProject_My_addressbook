package com.android.mypeople.Minwoo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mypeople.Minwoo.NetworkTask.ListNetworkTask;
import com.android.mypeople.youngjae.Activity.LoginActivity;
import com.android.mypeople.Minwoo.Adapter.MainSpinnerAdapter;
import com.android.mypeople.HyunA.Activity.MyPageActivity;
import com.android.mypeople.R;
import com.android.mypeople.Minwoo.Adapter.RecyclerAdapter;
import com.android.mypeople.jiseok.NetworkTask.SelectTagNameTask;
import com.android.mypeople.Share.Bean.Bean_friendslist;
import com.android.mypeople.Share.Bean.Bean_tag;
import com.android.mypeople.jiseok.Activity.TAGActivity;
import com.android.mypeople.Taehyun.Activity.AddFriendsActivity;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //
    private RecyclerView listView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private ArrayList<Bean_friendslist> data = null;
    private ArrayList<Bean_tag> tags = null;
    private RecyclerAdapter adapter = null;

    private ArrayList<Bean_friendslist> list;          // 데이터를 넣은 리스트변수

    String TAG = "MainActivity";
    BottomAppBar bab;
    boolean isCenter=true;
    Spinner spinner_field = null;
    LinearLayout linearLayout = null;
    AutoCompleteTextView searchText = null;
    Button addBtn = null;
    String macIP;
    String urlAddr = null;
    String urlAddr2 = null;
    CoordinatorLayout outLayout = null;
    int spinnerItmeNum = -1;
    String putExtraSpNum = null;
    ImageView searchBtn = null;
    String where = null;
    String getSearchText = null;
    int userSeqno = 0;
    String action = null;
    String putExtraText = null;
    int dataSize = 0;
    TextView tv_Count = null;
    ImageView toolbar_Hay = null;

    RadioButton radioBtn_Name = null;
    RadioButton radioBtn_New = null;
    RadioButton radioBtn_Tag = null;
    RadioGroup radioGroup = null;

    Bean_friendslist bean_friendslist = new Bean_friendslist();

    // 스피너
    int tag[] = {R.drawable.tag_small, R.drawable.tag1, R.drawable.tag2, R.drawable.tag3, R.drawable.tag4, R.drawable.tag5};
    String tagName[] = {"", "TAG1", "TAG2", "TAG3", "TAG4", "TAG5"};
    Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (AutoCompleteTextView)findViewById(R.id.main_Edit_SearchText);

        tv_Count = findViewById(R.id.main_Text_Count);

        // 값 받기
        Intent intent = getIntent();
        userSeqno = intent.getIntExtra("uSeqno", 0);
        Log.v(TAG, "userSeqno : " + userSeqno);

        // 스피너
        spinner = findViewById(R.id.main_Spinner_tag);

        MainSpinnerAdapter spinnerAdapter = new MainSpinnerAdapter(getApplicationContext(), tag, tagName);
        spinner.setAdapter(spinnerAdapter);
        spinnerItmeNum = spinner.getSelectedItemPosition();

        // 연결 (검색 내용, 정렬순에 따라 jsp 바꿔주기)
        macIP = intent.getStringExtra("macIP");
        urlAddr2 = "http://" + macIP + ":8080/mypeople/tag_select_Tagname.jsp?user_uSeqno=" + userSeqno;
        // 인텐트 받은 액션여부로 나누어 urlAddr 설정하기
        action = intent.getStringExtra("action");

        switch (action){ // intent로 받은 action값이 존재할 경우 값마다 다른 DB Action
            case "Show_List":
                where = "select"; // 리스트 불러오는 처음은 select
                urlAddr = "http://" + macIP + ":8080/mypeople/friendslist_Select_All.jsp?user_uSeqno=" + userSeqno;
                Log.v(TAG, "Show_List");
                break;
            case "Search_NoTag": // 태그없이 검색
                putExtraText = intent.getStringExtra("searchText");
                if (putExtraText == null) {
                    putExtraText = "";
                }
                where = "search";
                urlAddr = "http://" + macIP + ":8080/mypeople/friendslist_Search_SearchText.jsp?user_uSeqno=" + userSeqno + "&searchText=" + putExtraText;
                Log.v(TAG, "Search_With_NoTag");
                break;
            case "Search_With_Tag": // 태그 고르고 검색
                putExtraText = intent.getStringExtra("searchText");
                putExtraSpNum = intent.getStringExtra("spinnerPosition");

                if (putExtraText == null) {
                    putExtraText = "";
                }
                where = "search_with_tag";
                urlAddr = "http://" + macIP + ":8080/mypeople/friendslist_Search_With_Tag.jsp?user_uSeqno=" + userSeqno + "&searchText=" + putExtraText + "&fTag=fTag" + putExtraSpNum;
                Log.v(TAG, "Search_With_NoTag");
                break;
        }

        Log.v(TAG, "검색값 : " + getSearchText);
        Log.v(TAG, "spinnerItemNum : " + spinnerItmeNum);
        searchBtn = findViewById(R.id.main_ImgBtn_SearchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerItmeNum = spinner.getSelectedItemPosition();
                getSearchText = searchText.getText().toString();

                switch (spinnerItmeNum){
                    case 0: // 태그 선택없이 검색
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("uSeqno", userSeqno);
                        intent.putExtra("action", "Search_NoTag");
                        intent.putExtra("searchText", getSearchText);
                        intent.putExtra("macIP", macIP);
                        startActivity(intent);
                        break;
                    default: // 태그를 선택 후 검색
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("macIP", macIP);
                        intent.putExtra("uSeqno", userSeqno);
                        intent.putExtra("action", "Search_With_Tag");
                        intent.putExtra("searchText", getSearchText);
                        intent.putExtra("spinnerPosition", Integer.toString(spinnerItmeNum));

                        Log.v(TAG, "유저넘버 : " + userSeqno);
                        Log.v(TAG, "검색어 : " + getSearchText);
                        Log.v(TAG, "태그 : " + spinnerItmeNum);
                        startActivity(intent);
                }
            }
        });
        Log.v(TAG, "urlAddr : " + urlAddr);

        // 정렬

        //라디오 버튼 설정
        radioBtn_Name = findViewById(R.id.main_Radio_Name);
        radioBtn_New = findViewById(R.id.main_Radio_New);
        radioBtn_Tag = findViewById(R.id.main_Radio_Tag);

        radioBtn_Tag.setOnClickListener(radioButtonClickListener);
        radioBtn_New.setOnClickListener(radioButtonClickListener);
        radioBtn_Tag.setOnClickListener(radioButtonClickListener);
        //라디오 그룹 설정
        radioGroup = findViewById(R.id.main_RadioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        // 리사이클러뷰 규격 만들기
        listView = findViewById(R.id.listView_Friends);
        listView.setHasFixedSize(true);

        // Context는 Activity
        adapter = new RecyclerAdapter(MainActivity.this, R.layout.activity_main, data, MainActivity.this, macIP);
        listView.setAdapter(adapter);

        // 레이아웃 매니저 만들기
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);

        // 툴바 생성
        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar); // 상단 툴바
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // 검색창 키보드 액션
        searchText.setOnKeyListener(new View.OnKeyListener() { // 엔터 키 눌렀을 경우!(onClick 메소드 사용해서 검색버튼 누르기)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    finish();
                    return true;
                }
                return false;
            }
        });

        // 홈버튼 액션을 위한 선언
        bab=findViewById(R.id.bab);

        toolbar_Hay = findViewById(R.id.toolbar_title);
        toolbar_Hay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hay!", Toast.LENGTH_SHORT).show();

                isCenter= !isCenter;

                if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                else bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("uSeqno", userSeqno);
                intent.putExtra("action", "Show_List");
                startActivity(intent);
            }
        });

        // 배경 선택 시 키보드 내리기 위한 선언
        outLayout = findViewById(R.id.main_coordinator_outer);

        outLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(searchText.getText().toString() != null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        // 친구 추가 버튼 액션
        addBtn =  findViewById(R.id.main_Btn_AddFriend);
        addBtn.setOnClickListener(onClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bean_friendslist bean_friendslist = new Bean_friendslist();
        Log.v(TAG, "onResume urlAddr : " + urlAddr);
        Intent intent = new Intent();

        // 리스트를 생성한다.
        list = new ArrayList<Bean_friendslist>();
        list = connectGetData(); // db를 통해 받은 데이터를 담는다.

        // -----------------검색-------------------
        if(list.size() == 0){ // 만약 검색결과가 없다면

        }else{ // 검색 결과가 있다면
            String tempValue = null; // list의 값을 돌려가며 저장시키기 위한 변수
            ArrayList<String> tempValues = new ArrayList<String>(); // tempValue의 값을 배열에 저장시킴
            ArrayList<String> deleteDuplData = new ArrayList<String>(); // tempValues 배열의 중복값을 제거해서 다시 저장시킴
            ArrayList<String> values = new ArrayList<String>();

            for(int i = 0; i < list.size(); i++){ // list의 크기만큼 돌려가며 데이터를 추출한다.
                tempValue = list.get(i).getfName(); // 이름의 모든 값을 받는다.
                tempValues.add(tempValue);
                tempValue = list.get(i).getfComment(); // 코멘트의 모든 값을 받는다.
                tempValues.add(tempValue);
                tempValue = list.get(i).getfRelation(); // 관계의 모든 값을 받는다.
                tempValues.add(tempValue);
                tempValue = list.get(i).getfAddress(); // 주소의 모든 값을 받는다.
                tempValues.add(tempValue);
                tempValue = list.get(i).getfEmail(); // 이메일의 모든 값을 받는다.
                tempValues.add(tempValue);

                for(String item : tempValues){ // 배열값의 중복 제거 후 deleteDupl 배열에 저장시킨다.
                    if(!deleteDuplData.contains(item))
                        deleteDuplData.add(item);
                }

                Log.v(TAG, "배열값 확인 : " + deleteDuplData);
            }

            // 리스트에 검색될 데이터(단어)를 추가한다.
            searchText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, deleteDuplData)); // 중복이 제거된 배열값을 넣어서 리스트를 띄운다.
        }
        // ------------------------------------------

        // 리스트 클릭 이벤트
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, Main_CustomDialog.class);
                // 뭐가 필요하실지 몰라서 다 보냅니다..
                intent.putExtra("macIP", macIP);
                intent.putExtra("uSeqno", userSeqno);
                intent.putExtra("fSeqno", data.get(position).getfSeqno());
                intent.putExtra("fName", data.get(position).getfName());
                intent.putExtra("fRelation", data.get(position).getfRelation());
                intent.putExtra("fTel", data.get(position).getfTel());
                intent.putExtra("fImage", data.get(position).getfImage());
                intent.putExtra("fImageReal", data.get(position).getfImageReal());
                intent.putExtra("fTag1", data.get(position).getfTag1());
                intent.putExtra("fTag2", data.get(position).getfTag2());
                intent.putExtra("fTag3", data.get(position).getfTag3());
                intent.putExtra("fTag4", data.get(position).getfTag4());
                intent.putExtra("fTag5", data.get(position).getfTag5());
                intent.putExtra("fComment", data.get(position).getfComment());
                intent.putExtra("fAddress", data.get(position).getfAddress());
                intent.putExtra("fEmail", data.get(position).getfEmail());
                intent.putExtra("Tag1", tags.get(0).getTag1());
                intent.putExtra("Tag2", tags.get(0).getTag2());
                intent.putExtra("Tag3", tags.get(0).getTag3());
                intent.putExtra("Tag4", tags.get(0).getTag4());
                intent.putExtra("Tag5", tags.get(0).getTag5());

                Log.v(TAG,"macIP : " + macIP);
                Log.v(TAG, "클릭한 사람 seqno : " + data.get(position).getfSeqno());
                Log.v(TAG, "이름 : " + data.get(position).getfName());
                Log.v(TAG, "관계 : " + data.get(position).getfRelation());
                Log.v(TAG, "이미지 : " + data.get(position).getfImage());
                Log.v(TAG, "실제이미지 : " + data.get(position).getfImageReal());
                Log.v(TAG, "tag1 : " + data.get(position).getfTag1());
                Log.v(TAG, "tag2 : " + data.get(position).getfTag2());
                Log.v(TAG, "tag3 : " + data.get(position).getfTag3());
                Log.v(TAG, "tag4 : " + data.get(position).getfTag4());
                Log.v(TAG, "tag5 : " + data.get(position).getfTag5());
                Log.v(TAG, "코멘트 : " + data.get(position).getfComment());
                Log.v(TAG, "주소 : " + data.get(position).getfAddress());

                startActivity(intent);
            }
        });

        Log.v(TAG, "onResume()");
        Log.v(TAG, "onResume getPosition : " + spinnerItmeNum);

    }

    private ArrayList<Bean_friendslist> connectGetData(){ // friendslist 데이터를 출력해옴
        ArrayList<Bean_friendslist> beanList = new ArrayList<Bean_friendslist>();
        try {
            getSearchText = searchText.getText().toString(); // 검색한 내용
            Log.v(TAG, "connectGetData getSearchText : " + getSearchText);
            ListNetworkTask listNetworkTask= new ListNetworkTask(MainActivity.this, urlAddr, where); // 리스트를 불러오는 NetworkTask

            SelectTagNameTask selectTagNameTask = new SelectTagNameTask(MainActivity.this, urlAddr2, "select"); // 태그명을 불러오는 NetworkTask
            Object obj1 = selectTagNameTask.execute().get();
            tags = (ArrayList<Bean_tag>) obj1;
            tagName[1] = tags.get(0).getTag1();
            tagName[2] = tags.get(0).getTag2();
            tagName[3] = tags.get(0).getTag3();
            tagName[4] = tags.get(0).getTag4();
            tagName[5] = tags.get(0).getTag5();

            Object obj = listNetworkTask.execute().get();
            data = (ArrayList<Bean_friendslist>) obj;

            Log.v(TAG, "data.size() : " + data.size());

            adapter = new RecyclerAdapter(MainActivity.this, R.layout.main_recycler_items, data, MainActivity.this, macIP);
            listView.setAdapter(adapter);

            // 리스트 개수 파악
            Intent intent = getIntent();
            String get = intent.getStringExtra("searchText");
            if(action.equals("Show_List")){ // 전체 개수 보존하기 위함
                dataSize = data.size();
                Log.v(TAG, "dataSize = " + dataSize);
                tv_Count.setText("친구 : " + dataSize + "명");

            }else { // 그외의 액션 시 검색결과로 보여줌
                dataSize = data.size();
                Log.v(TAG, "dataSize = " + dataSize);
                tv_Count.setText("검색결과 : " + dataSize + "명");
            }
            beanList = data;

        }catch (Exception e){
            e.printStackTrace();
        }
        return beanList;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.main_Btn_AddFriend:
                    intent = new Intent(MainActivity.this, AddFriendsActivity.class);
                    intent.putExtra("uSeqno", userSeqno);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 툴바 메뉴

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // 메뉴 아이템 액션
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menu_MyPage: // 마이페이지
                intent = new Intent(MainActivity.this, MyPageActivity.class);
                intent.putExtra("uSeqno", userSeqno);
                intent.putExtra("macIP", macIP);
                startActivity(intent);

                break;
            case R.id.menu_ManageTag: // 태그수정
                intent = new Intent(MainActivity.this, TAGActivity.class);
                intent.putExtra("uSeqno", userSeqno);
                intent.putExtra("macIP", macIP);
                startActivity(intent);

                break;
            case R.id.menu_Logout: // 로그아웃 시 선택 가능
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("로그아웃");
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setNegativeButton("아니오", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() { // 예를 눌렀을 경우 로그인 창으로 이동
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();

                        autoLogin.clear();
                        autoLogin.commit();
                        startActivity(intent);
                    }
                });

                builder.create().show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickFab(View view) { // 하단 앱바 홈버튼 클릭시
        isCenter= !isCenter;

        if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        else bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("macIP", macIP);
        intent.putExtra("uSeqno", userSeqno);
        intent.putExtra("action", "Show_List");
        startActivity(intent);
    }

    public void hideKeyboard(View view) { // 레이아웃 클릭 시 키보드 내리기
        view = getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // --------------------------------정렬------------------------------
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.v(TAG, "버튼 클릭 : " + checkedId);

            // 1. 정렬
            // 2. 어댑터 갱신

            switch (checkedId){
                case 2131231025: // 가나다순 (ASC)
                    Comparator<Bean_friendslist> solt_Name = new Comparator<Bean_friendslist>() {
                        @Override
                        public int compare(Bean_friendslist o1, Bean_friendslist o2) {
                            return o1.getfName().compareTo(o2.getfName()) ;
                        }
                    };
                    Collections.sort(data, solt_Name);
                    adapter.notifyDataSetChanged();
                    break;

                case 2131231026: // 최신순 (DESC)
                    Comparator<Bean_friendslist> solt_new = new Comparator<Bean_friendslist>() {
                        @Override
                        public int compare(Bean_friendslist o1, Bean_friendslist o2) {
                            return (o2.getfSeqno() - o1.getfSeqno());
                        }
                    };
                    Collections.sort(data, solt_new) ;
                    adapter.notifyDataSetChanged() ;
                    break;

                case 2131231027: // 태그순 (ASC) => tag1 > tag5 순으로
                    Comparator<Bean_friendslist> solt_Tag = new Comparator<Bean_friendslist>() {
                        @Override
                        public int compare(Bean_friendslist o1, Bean_friendslist o2) {
                            int ret = 0;

                            if(o1.getfTag1() < o2.getfTag1()){ // 처음 값 비교
                                ret = 1;
                            }else if(o1.getfTag1() == o2.getfTag1()){ // 같으면 다음값 비교Log.v(TAG, "1이름 : " + o1.getfName());
                                ret = o1.getfName().compareTo(o2.getfName()); // 1번째 이름 비교
                                if(o1.getfTag2() < o2.getfTag2()){ // 두번째 값 비교
                                    ret = 1;
                                }else if(o1.getfTag2() == o2.getfTag2()){ // 같으면 다음값
                                    ret = o1.getfName().compareTo(o2.getfName()); // 2번째 이름 비교
                                    if(o1.getfTag3() < o2.getfTag3()){ // 세번째 값 비교
                                        ret = 1;
                                    }else if(o1.getfTag3() == o2.getfTag3()){ // 같으면 다음값
                                        ret = o1.getfName().compareTo(o2.getfName()); // 3번째 이름 비교
                                        if(o1.getfTag4() < o2.getfTag4()){ // 네번째 값 비교
                                            ret = 1;
                                        }else if(o1.getfTag4() == o2.getfTag4()){ // 같으면 다음값
                                            ret = o1.getfName().compareTo(o2.getfName()); // 4번째 이름 비교
                                            if(o1.getfTag5() < o2.getfTag5()){ // 마지막 값 비교
                                                ret = 1;
                                            }else if(o1.getfTag5() == o2.getfTag5()){ //  같으면 다음값
                                                ret = o1.getfName().compareTo(o2.getfName()); // 5번째 이름 비교
                                            }else{
                                                ret = -1;
                                            }
                                        }else{ // 전자가 작으면 앞으로
                                            ret = -1;
                                        }
                                    }else{ // 전자가 작으면 앞으로
                                        ret = -1;
                                    }
                                }else{ // 전자가 작으면 앞으로
                                    ret = -1;
                                }
                            }else{ // 전자가 작으면 앞으로
                                ret = -1;
                            }

                            return ret;
                        }
                    };
                    Collections.sort(data, solt_Tag) ;
                    adapter.notifyDataSetChanged() ;
            }
        }
    };


}