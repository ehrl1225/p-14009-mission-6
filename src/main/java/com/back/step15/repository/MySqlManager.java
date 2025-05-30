package com.back.step15.repository;

import com.back.step15.WiseSaying;

import java.sql.*;

public class MySqlManager {
    private final String CREATE_DB = "create table WiseSaying( id  integer not null AUTO_INCREMENT, content varchar(100), author  varchar(50), primary key (id));";
    private final String RESET_ID = "alter table WiseSaying AUTO_INCREMENT = 0;";
    private final String CLEAR_DB = "delete from WiseSaying;";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/WiseSaying";
    private final String db_user = "root";
    private final String db_pass = "root";
    private final int PAGE_SIZE = 5;
    private Connection conn;

    private String getSelectByIdSql(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from WiseSaying ");
        sql.append("where id=");
        sql.append(id);
        sql.append(";");
        return sql.toString();
    }

    private String getDeleteSql(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from WiseSaying where id = ");
        sql.append(id);
        sql.append(";");
        return sql.toString();
    }

    private String getUpdateSql(int id, String content, String author) {
        StringBuilder sql = new StringBuilder();
        sql.append("update WiseSaying ");
        sql.append("set content = \'");
        sql.append(content);
        sql.append("\', author = \'");
        sql.append(author);
        sql.append("\' where id = ");
        sql.append(id);
        sql.append(";");
        return sql.toString();
    }

    public void connect(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, db_user, db_pass);

        } catch(SQLException | ClassNotFoundException e){
//            System.out.println("Db Connection failed");
        }
    }

    public void disconnect(){
        try{
            conn.close();

        }catch(SQLException e){
//            System.out.println("Db Connection failed");
        }
    }

    public void createTable(){
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.execute(CREATE_DB);
            stmt.close();

        }catch(SQLException e){
//            System.out.println("sql failed");
        }
    }

    public int insertWiseSaying( String content, String author){
        PreparedStatement stmt;
        int id = 0;
        try{

            stmt = conn.prepareStatement("insert into WiseSaying(content, author) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, content);
            stmt.setString(2, author);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            stmt.close();
        }catch (SQLException e){
//            System.out.println("sql failed" + e.getMessage());
        }
        return id;
    }

    private void makeResult2View(ResultSet rs, WiseSayingView view, int page) throws SQLException {
        int count = 0;
        int start_index = (page-1) * PAGE_SIZE;
        while (rs.next()) {
            if (start_index <= count && count < start_index + PAGE_SIZE) {
                WiseSaying ws = new WiseSaying(rs.getString(2), rs.getString(3));
                ws.setID(rs.getInt(1));
                view.add(ws);
            }
            count++;
        }
        view.setMaxPage((count-1)/5 + 1);
    }

    public WiseSayingView selectPagedWiseSaying(int page){
        Statement stmt;
        WiseSayingView view = WiseSayingView.getEmptyView();
        view.setPage(page);
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from WiseSaying order by id desc;");
            makeResult2View(rs, view, page);
            rs.close();
            stmt.close();

        } catch(SQLException e){

        }
        return view;
    }

    public WiseSayingList selectAllWiseSaying(){
        Statement stmt;
        WiseSayingList list = new WiseSayingList();
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from WiseSaying order by id desc;");
            while(rs.next()){
                WiseSaying ws = new WiseSaying(rs.getString(2), rs.getString(3));
                ws.setID(rs.getInt(1));
                list.add(ws);
            }
        }catch (SQLException e){

        }
        return list;

    }

    public WiseSaying selectWiseSayingByID(int id){
        PreparedStatement stmt;
        WiseSaying ws = null;
        try{
            stmt = conn.prepareStatement("select * from WiseSaying where id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                 ws = new WiseSaying(rs.getString(2), rs.getString(3));
                ws.setID(rs.getInt(1));

            }

        }catch (SQLException e){

        }
        return ws;
    }

    public WiseSayingView selectWiseSayingByContent(int page, String content){
        WiseSayingView view = WiseSayingView.getEmptyView();
        PreparedStatement stmt;
        view.setPage(page);

        try{
            stmt = conn.prepareStatement("select * from WiseSaying where content like ? order by id desc;");
            stmt.setString(1,  "%"+content+ "%");
            ResultSet rs = stmt.executeQuery();
            makeResult2View(rs, view, page);
            rs.close();
            stmt.close();
        }catch (SQLException e){

        }
        return view;
    }

    public WiseSayingView selectWiseSayingByAuthor(int page, String author){
        WiseSayingView view = WiseSayingView.getEmptyView();
        PreparedStatement stmt;
        view.setPage(page);
        try{
            stmt = conn.prepareStatement("select * from WiseSaying where author like ? order by id desc;");
            stmt.setString(1,  "%"+author+ "%");
            ResultSet rs = stmt.executeQuery();
            makeResult2View(rs, view, page);
            rs.close();
            stmt.close();
        }catch (SQLException e){

        }
        return view;
    }

    public void deleteWiseSaying(int id){
        PreparedStatement stmt;
        try{
            stmt = conn.prepareStatement("delete from WiseSaying where id = ?;");
            stmt.setInt(1, id);
            stmt.execute(getDeleteSql(id));
            stmt.close();
        }catch(SQLException e){

        }
    }

    public void updateWiseSaying(int id, String content, String author){
        PreparedStatement stmt;
        try{
            stmt = conn.prepareStatement("update WiseSaying set content = ?, author = ? where id = ?;");
            stmt.setString(1, content);
            stmt.setString(2, author);
            stmt.setInt(3, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e){

        }
    }

    public void resetID(){
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.execute(RESET_ID);
            stmt.close();

        }catch (SQLException e){

        }
    }

    public void clearWiseSaying(){
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.execute(CLEAR_DB);
            stmt.close();
        }catch (SQLException e){

        }
    }

}
