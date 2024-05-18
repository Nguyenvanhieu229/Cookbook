package com.example.btl.dal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl.model.Message;
import com.example.btl.model.MonAn;
import com.example.btl.model.User;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Cookbook.db";
    private static int DATABASE_VERSION = 9;

    private static final String TABLE_USER = "user";
    private static final String TABLE_MON_AN = "mon_an";
    private static final String TABLE_MESSAGE = "message";
    private static final String TABLE_LUU = "luu";

    // Các cột trong bảng User
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_HINH_DAI_DIEN = "hinh_dai_dien";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_COOKBOOK_ID = "cookbook_id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_CHIA_SE_CHUNG = "chia_se_chung";

    // Các cột trong bảng Món Ăn
    private static final String COLUMN_MON_AN_ID = "id";
    private static final String COLUMN_MON_AN_HINH_DAI_DIEN = "hinh_dai_dien";
    private static final String COLUMN_MON_AN_TEN = "ten";
    private static final String COLUMN_MON_AN_MO_TA = "mo_ta";
    private static final String COLUMN_MON_AN_KHAU_PHAN = "khau_phan";
    private static final String COLUMN_MON_AN_THOI_GIAN_NAU = "thoi_gian_nau";
    private static final String COLUMN_MON_AN_NGUYEN_LIEU = "nguyen_lieu";
    private static final String COLUMN_MON_AN_CACH_LAM = "cach_lam";
    private static final String COLUMN_MON_AN_NGAY_TAO = "ngay_tao";
    private static final String COLUMN_MON_AN_HUONG_DAN = "huong_dan";
    private static final String COLUMN_MON_AN_ID_NGUOI_VIET = "id_nguoi_viet";

    // Các cột trong bảng Message
    private static final String COLUMN_MESSAGE_ID = "id";
    private static final String COLUMN_MESSAGE_ID_MON_AN = "id_mon_an";
    private static final String COLUMN_MESSAGE_ID_NGUOI_NHAN_XET = "id_nguoi_nhan_xet";
    private static final String COLUMN_MESSAGE_TIME = "time";
    private static final String COLUMN_MESSAGE_TITLE = "title";


    // Các cột trong bảng Luu
    private static final String COLUMN_LUU_ID = "id";
    private static final String COLUMN_LUU_ID_MON_AN = "id_mon_an";
    private static final String COLUMN_LUU_ID_NGUOI_LUU = "id_nguoi_luu";

    // Câu truy vấn tạo bảng User
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_HINH_DAI_DIEN + " BLOB,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_COOKBOOK_ID + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_CHIA_SE_CHUNG + " TEXT"
            + ")";

    // Câu truy vấn tạo bảng Món Ăn
    private static final String CREATE_TABLE_MON_AN = "CREATE TABLE " + TABLE_MON_AN + "("
            + COLUMN_MON_AN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MON_AN_HINH_DAI_DIEN + " BLOB,"
            + COLUMN_MON_AN_TEN + " TEXT,"
            + COLUMN_MON_AN_MO_TA + " TEXT,"
            + COLUMN_MON_AN_KHAU_PHAN + " INTEGER,"
            + COLUMN_MON_AN_THOI_GIAN_NAU + " INTEGER,"
            + COLUMN_MON_AN_NGUYEN_LIEU + " TEXT,"
            + COLUMN_MON_AN_CACH_LAM + " TEXT,"
            + COLUMN_MON_AN_NGAY_TAO + " DATE,"
            + COLUMN_MON_AN_HUONG_DAN + " TEXT,"
            + COLUMN_MON_AN_ID_NGUOI_VIET + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_MON_AN_ID_NGUOI_VIET + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")"
            + ")";

    // Câu truy vấn tạo bảng Message
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + "("
            + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MESSAGE_ID_MON_AN + " INTEGER,"
            + COLUMN_MESSAGE_ID_NGUOI_NHAN_XET + " INTEGER,"
            + COLUMN_MESSAGE_TIME + " DATE,"
            + COLUMN_MESSAGE_TITLE + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_MESSAGE_ID_MON_AN + ") REFERENCES " + TABLE_MON_AN + "(" + COLUMN_MON_AN_ID + "),"
            + "FOREIGN KEY(" + COLUMN_MESSAGE_ID_NGUOI_NHAN_XET + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")"
            + ")";

    // Câu truy vấn tạo bảng Luu
    private static final String CREATE_TABLE_LUU = "CREATE TABLE " + TABLE_LUU + "("
            + COLUMN_LUU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_LUU_ID_MON_AN + " INTEGER,"
            + COLUMN_LUU_ID_NGUOI_LUU + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_LUU_ID_MON_AN + ") REFERENCES " + TABLE_MON_AN + "(" + COLUMN_MON_AN_ID + "),"
            + "FOREIGN KEY(" + COLUMN_LUU_ID_NGUOI_LUU + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")"
            + ")";
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MON_AN);
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_LUU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS user");


        db.execSQL("DROP TABLE IF EXISTS mon_an");


        db.execSQL("DROP TABLE IF EXISTS message");


        db.execSQL("DROP TABLE IF EXISTS luu");

        // Tạo lại bảng
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // get tất cả món ăn có sắp xếp theo thời gian viết từ mới đến cũ.
    @SuppressLint("Range")
    public ArrayList<MonAn> getAll() {
        ArrayList<MonAn> monAnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Câu truy vấn lấy tất cả các món ăn và sắp xếp theo ngày tạo từ mới đến cũ
        String selectQuery = "SELECT * FROM " + TABLE_MON_AN + " ORDER BY " + COLUMN_MON_AN_NGAY_TAO + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Lặp qua các hàng kết quả và thêm vào danh sách món ăn
        if (cursor.moveToFirst()) {
            do {
                MonAn monAn = new MonAn();
                monAn.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID)));
                monAn.setHinhDaiDien(cursor.getBlob(cursor.getColumnIndex(COLUMN_MON_AN_HINH_DAI_DIEN)));
                monAn.setTen(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_TEN)));
                monAn.setMoTa(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_MO_TA)));
                monAn.setKhauPhan(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_KHAU_PHAN)));
                monAn.setThoiGianNau(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_THOI_GIAN_NAU)));
                monAn.setNguyenLieu(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGUYEN_LIEU)));
                monAn.setCachLam(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_CACH_LAM)));
                monAn.setNgayTao(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGAY_TAO)));
                monAn.setHuongDan(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_HUONG_DAN)));
                monAn.setNguoiViet(getUserById(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID_NGUOI_VIET))));

                monAnList.add(monAn);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và trả về danh sách món ăn
        cursor.close();
        Log.d("lấy", "Lấy thành công món ăn:" + monAnList.size());
        return monAnList;
    }

    @SuppressLint("Range")
    public User getUserById(int userId) {
        Log.d("user id", userId + "");
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;


        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {

            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setHinhDaiDien(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_HINH_DAI_DIEN)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user.setCookbookId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_COOKBOOK_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setChiaSeChung(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CHIA_SE_CHUNG)));
        }


        cursor.close();

        Log.d("lấy", "lấy thành công user" + user.getId());
        return user;
    }

    public long addMonAn(MonAn monAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Đặt các giá trị của món ăn vào ContentValues
        values.put(COLUMN_MON_AN_HINH_DAI_DIEN, monAn.getHinhDaiDien());
        values.put(COLUMN_MON_AN_TEN, monAn.getTen());
        values.put(COLUMN_MON_AN_MO_TA, monAn.getMoTa());
        values.put(COLUMN_MON_AN_KHAU_PHAN, monAn.getKhauPhan());
        values.put(COLUMN_MON_AN_THOI_GIAN_NAU, monAn.getThoiGianNau());
        values.put(COLUMN_MON_AN_NGUYEN_LIEU, monAn.getNguyenLieu());
        values.put(COLUMN_MON_AN_CACH_LAM, monAn.getCachLam());
        values.put(COLUMN_MON_AN_NGAY_TAO, monAn.getNgayTao());
        values.put(COLUMN_MON_AN_HUONG_DAN, monAn.getHuongDan());
        values.put(COLUMN_MON_AN_ID_NGUOI_VIET, monAn.getNguoiViet().getId());

        // Thực hiện việc chèn dữ liệu vào bảng
         Long res =  db.insert(TABLE_MON_AN, null, values);
         Log.d("thêm món ăn thành công", "id = " + res);
         return res;
    }

    public User addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Đặt các giá trị của người dùng vào ContentValues
        values.put(COLUMN_USER_HINH_DAI_DIEN, user.getHinhDaiDien());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_COOKBOOK_ID, user.getCookbookId());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CHIA_SE_CHUNG, user.getChiaSeChung());

        // Thực hiện việc chèn dữ liệu vào bảng
        user.setId((int) db.insert(TABLE_USER, null, values));

        Log.d("add user", "thêm thành công" + user.getId());

        return user;


    }

    public void deleteMonAn(long monAnId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa món ăn có id được chỉ định
        db.delete(TABLE_MON_AN, COLUMN_MON_AN_ID + " > 0", new String[]{String.valueOf(monAnId)});

        db.close(); // Đóng kết nối đến cơ sở dữ liệu
    }

    public void deleteAllMonAn() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa tất cả các món ăn từ bảng mon_an
        db.delete(TABLE_MON_AN, null, null);

        db.close(); // Đóng kết nối đến cơ sở dữ liệu
    }
    @SuppressLint("Range")
    public User findUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {
                "id",
                "hinh_dai_dien",
                "username",
                "password",
                "cookbook_id",
                "email",
                "chia_se_chung"
        };

        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("user", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            byte[] hinhDaiDien = cursor.getBlob(cursor.getColumnIndex("hinh_dai_dien"));
            String cookbookId = cursor.getString(cursor.getColumnIndex("cookbook_id"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String chiaSeChung = cursor.getString(cursor.getColumnIndex("chia_se_chung"));

            // Tạo một đối tượng User từ dữ liệu từ cơ sở dữ liệu
            user = new User(id, username, password, cookbookId, email, chiaSeChung, hinhDaiDien);
        }

        cursor.close();
        db.close();

        return user;
    }

    public boolean luuMonAn(User user, MonAn monAn) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_LUU_ID_MON_AN + " = ? AND " + COLUMN_LUU_ID_NGUOI_LUU + " = ?";
        String[] selectionArgs = {String.valueOf(monAn.getId()), String.valueOf(user.getId())};
        Cursor cursor = db.query(TABLE_LUU, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            Log.d("Lưu bản ghi", "Món ăn đã được lưu trước đó.");
            cursor.close();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_LUU_ID_MON_AN, monAn.getId());
        values.put(COLUMN_LUU_ID_NGUOI_LUU, user.getId());

        long result = db.insert(TABLE_LUU, null, values);
        cursor.close();
        db.close();

        Log.d("Lưu bản ghi", "Bản ghi đã được lưu với ID = " + result);
        return true;
    }

    public long saveMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MESSAGE_ID_MON_AN, message.getMonAn().getId());
        values.put(COLUMN_MESSAGE_ID_NGUOI_NHAN_XET, message.getUser().getId());
        values.put(COLUMN_MESSAGE_TIME, message.getTime());
        values.put(COLUMN_MESSAGE_TITLE, message.getTitle());


        long result = db.insert(TABLE_MESSAGE, null, values);

        Log.d("Lưu bản ghi", "Bản ghi Message đã được lưu với ID = " + result);

        return result;
    }

    @SuppressLint("Range")
    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MESSAGE + " ORDER BY " + COLUMN_MESSAGE_TIME + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_ID)));
                message.setMonAn(getMonAnById(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_ID_MON_AN))));
                message.setUser(this.getUserById(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_ID_NGUOI_NHAN_XET))));
                message.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_TIME)));
                message.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_TITLE)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }


        cursor.close();
        Log.d("Lấy", "Lấy thành công các message:" + messageList.size());
        return messageList;
    }

    @SuppressLint("Range")
    public MonAn getMonAnById(int monAnId) {
        SQLiteDatabase db = this.getReadableDatabase();
        MonAn monAn = null;


        String query = "SELECT * FROM " + TABLE_MON_AN + " WHERE " + COLUMN_MON_AN_ID + " = " + monAnId;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            monAn = new MonAn();
            monAn.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID)));
            monAn.setHinhDaiDien(cursor.getBlob(cursor.getColumnIndex(COLUMN_MON_AN_HINH_DAI_DIEN)));
            monAn.setTen(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_TEN)));
            monAn.setMoTa(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_MO_TA)));
            monAn.setKhauPhan(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_KHAU_PHAN)));
            monAn.setThoiGianNau(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_THOI_GIAN_NAU)));
            monAn.setNguyenLieu(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGUYEN_LIEU)));
            monAn.setCachLam(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_CACH_LAM)));
            monAn.setNgayTao(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGAY_TAO)));
            monAn.setHuongDan(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_HUONG_DAN)));
            monAn.setNguoiViet(getUserById(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID_NGUOI_VIET))));
        }

        cursor.close();
        return monAn;
    }

    public void deleteMonAnById(int monAnId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MON_AN, COLUMN_MON_AN_ID + " = ?", new String[]{String.valueOf(monAnId)});

        Log.d("Xoa thanh cong", "Xoa thanh cong mon an " + monAnId);
        db.close();
    }

    public void updateMonAn(MonAn monAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Đặt các giá trị mới của món ăn vào ContentValues
        values.put(COLUMN_MON_AN_HINH_DAI_DIEN, monAn.getHinhDaiDien());
        values.put(COLUMN_MON_AN_TEN, monAn.getTen());
        values.put(COLUMN_MON_AN_MO_TA, monAn.getMoTa());
        values.put(COLUMN_MON_AN_KHAU_PHAN, monAn.getKhauPhan());
        values.put(COLUMN_MON_AN_THOI_GIAN_NAU, monAn.getThoiGianNau());
        values.put(COLUMN_MON_AN_NGUYEN_LIEU, monAn.getNguyenLieu());
        values.put(COLUMN_MON_AN_CACH_LAM, monAn.getCachLam());
        values.put(COLUMN_MON_AN_NGAY_TAO, monAn.getNgayTao());
        values.put(COLUMN_MON_AN_HUONG_DAN, monAn.getHuongDan());
        values.put(COLUMN_MON_AN_ID_NGUOI_VIET, monAn.getNguoiViet().getId());

        // Cập nhật thông tin của món ăn trong bảng
        db.update(TABLE_MON_AN, values, COLUMN_MON_AN_ID + " = ?",
                new String[]{String.valueOf(monAn.getId())});

        db.close(); // Đóng kết nối đến cơ sở dữ liệu
    }

    public void deleteAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa tất cả các hàng từ bảng message
        db.delete(TABLE_MESSAGE, null, null);

        db.close(); // Đóng kết nối đến cơ sở dữ liệu
    }

    public int countMonAnByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT COUNT(*) FROM " + TABLE_MON_AN + " WHERE " + COLUMN_MON_AN_ID_NGUOI_VIET + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(countQuery, selectionArgs);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public int sumSavedDishesByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sumQuery = "SELECT SUM(1) FROM " + TABLE_LUU + " luu " +
                "INNER JOIN " + TABLE_MON_AN + " mon_an ON luu." + COLUMN_LUU_ID_MON_AN + " = mon_an." + COLUMN_MON_AN_ID +
                " WHERE mon_an." + COLUMN_MON_AN_ID_NGUOI_VIET + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(sumQuery, selectionArgs);
        int sum = 0;

        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }

        cursor.close();
        return sum;
    }

    public int countSavedDishesByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn để đếm số lượng món ăn mà một người dùng đã lưu
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_LUU +
                " WHERE " + COLUMN_LUU_ID_NGUOI_LUU + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(countQuery, selectionArgs);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    @SuppressLint("Range")
    public ArrayList<MonAn> getSavedDishesByUserId(int userId) {
        ArrayList<MonAn> savedDishesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn để lấy tất cả các món ăn đã được lưu bởi một người dùng
        String selectQuery = "SELECT ma.* FROM " + TABLE_LUU + " luu " +
                "INNER JOIN " + TABLE_MON_AN + " ma ON luu." + COLUMN_LUU_ID_MON_AN + " = ma." + COLUMN_MON_AN_ID +
                " WHERE luu." + COLUMN_LUU_ID_NGUOI_LUU + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                MonAn savedDish = new MonAn();
                savedDish.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID)));
                savedDish.setHinhDaiDien(cursor.getBlob(cursor.getColumnIndex(COLUMN_MON_AN_HINH_DAI_DIEN)));
                savedDish.setTen(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_TEN)));
                savedDish.setMoTa(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_MO_TA)));
                savedDish.setKhauPhan(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_KHAU_PHAN)));
                savedDish.setThoiGianNau(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_THOI_GIAN_NAU)));
                savedDish.setNguyenLieu(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGUYEN_LIEU)));
                savedDish.setCachLam(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_CACH_LAM)));
                savedDish.setNgayTao(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_NGAY_TAO)));
                savedDish.setHuongDan(cursor.getString(cursor.getColumnIndex(COLUMN_MON_AN_HUONG_DAN)));
                savedDish.setNguoiViet(getUserById(cursor.getInt(cursor.getColumnIndex(COLUMN_MON_AN_ID_NGUOI_VIET))));

                savedDishesList.add(savedDish);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return savedDishesList;
    }

    public void deleteSavedDishByUserIdAndDishId(int userId, int dishId) {
        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(TABLE_LUU, COLUMN_LUU_ID_NGUOI_LUU + " = ? AND " + COLUMN_LUU_ID_MON_AN + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(dishId)});

        db.close();
    }

    public ArrayList<Integer> getAllSavedMonAnIdsByUser(User user) {
        ArrayList<Integer> savedMonAnIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_LUU_ID_MON_AN +
                " FROM " + TABLE_LUU +
                " WHERE " + COLUMN_LUU_ID_NGUOI_LUU + " = ?";


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user.getId())});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int monAnId = cursor.getInt(cursor.getColumnIndex(COLUMN_LUU_ID_MON_AN));
                savedMonAnIds.add(monAnId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return savedMonAnIds;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_HINH_DAI_DIEN, user.getHinhDaiDien());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_COOKBOOK_ID, user.getCookbookId());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CHIA_SE_CHUNG, user.getChiaSeChung());

        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});

        db.close();
    }

}
